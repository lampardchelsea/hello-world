/** */
package com.eus.digitalservices.transformer.pipeline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PipelineFactory implements ApplicationContextAware {

  private static final String SPRING_BASED_TASK_CREATION_EXCEPTION =
      "[%s] Bean of name %s could not be created! Make sure the bean has a constructor with a TaskConfig parameter along with default constructor & the bean scope is prototype!";

  private PipelineContext pipelineContext;

  private ApplicationContext springApplicationContext;

  @Autowired private TransJsonUtil jsonUtil;

  @Value("${app.classpath.config.enabled}")
  private boolean classPathConfiguration;

  @Value("${app.etl.config.dir.location}")
  private String configFolder;

  @Value("${app.job.tasks.tunnel.size}")
  private int defaultTunnelSize;

  @Autowired private ExecutorService executorService;

  @Autowired
  @Qualifier("GenericExceptionHandler")
  private IExceptionHandler genericExceptionHandler;

  @Value("${app.sftp.statistics.dir:/tmp}")
  private String pipelineStatRemoteDir;

  @Autowired private IFileTransporter fileTransporter;

  @Autowired private TransDateUtil dateUtil;

  /**
   * Gets the pipeline.
   *
   * @param pipeLineidentifier identifier of the pipeline
   * @return the pipeline
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws GenericException the generic exception
   */
  public Pipeline getPipeline(String pipeLineidentifier) throws IOException, GenericException {
    Pipeline pipeLine = getPipelineFromConfig(pipeLineidentifier);
    return initializePipeline(pipeLine);
  }

  /**
   * Gets the pipeline from config.
   *
   * @param pipeLineidentifier the pipe line identifier
   * @return the pipeline from config
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Pipeline getPipelineFromConfig(String pipeLineidentifier) throws IOException {
    File pipeLineConfigFile = null;
    if (classPathConfiguration) {
      pipeLineConfigFile =
          new ClassPathResource(
                  configFolder
                      + File.separator
                      + pipeLineidentifier
                      + TransConstants.PIPELINE_FILE_EXTN)
              .getFile();
    } else {
      pipeLineConfigFile =
          new File(configFolder, pipeLineidentifier + TransConstants.PIPELINE_FILE_EXTN);
    }
    return jsonUtil.readObjectFromFile(pipeLineConfigFile, Pipeline.class);
  }

  /**
   * Initialize pipeline.
   *
   * @param pipeLine the pipe line
   * @return the pipeline
   * @throws GenericException the generic exception
   */
  private Pipeline initializePipeline(Pipeline pipeLine) throws GenericException {
    List<ExecutionBlock> executionBlocks = pipeLine.getExecutionBlocks();
    Collections.sort(executionBlocks);

    PipelineStatistics pipelineStatistics =
        PipelineStatistics.builder()
            .pipelineName(pipeLine.getName())
            .taskStatistics(Collections.synchronizedList(new ArrayList<>()))
            .build();
    pipeLine.setPipelineStatistics(pipelineStatistics);

    ITunnel dataTunnel = getDefaultTunnel(pipeLine.getTunnelType());
    pipeLine.setPipelineDoor(dataTunnel);

    for (ExecutionBlock block : executionBlocks) {
      List<ITask> tasks = block.getTasks();
      dataTunnel = initializeTasks(tasks, dataTunnel, block, pipelineStatistics);
    }
    return pipeLine;
  }

  /**
   * Initialize tasks.
   *
   * @param tasks the block tasks
   * @param dataTunnel the data tunnel
   * @param parentBlock the parent block
   * @param pipelineStatistics the pipeline statistics
   * @return the i tunnel
   * @throws GenericException the generic exception
   */
  private ITunnel initializeTasks(
      List<ITask> tasks,
      ITunnel dataTunnel,
      ExecutionBlock parentBlock,
      PipelineStatistics pipelineStatistics)
      throws GenericException {
    List<ITask> taskList = new ArrayList<>(parentBlock.getTasks());
    List<ITask> taskToRepalce =
        taskList.stream()
            .map(
                t -> {
                  try {
                    return replaceSpringTasks(t, parentBlock);
                  } catch (Exception e) {
                    throw new NonThrowableException(e);
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    parentBlock.getTasks().removeAll(taskToRepalce);
    if (tasks.size() > 1) {
      return induceSplitterJoiner(tasks, dataTunnel, pipelineStatistics);
    } else {
      return intializeTask(tasks.get(0), dataTunnel, pipelineStatistics);
    }
  }

  /**
   * Induce splitter joiner.
   *
   * @param tasks the tasks
   * @param dataTunnel the data tunnel
   * @param pipelineStatistics the pipeline statistics
   * @return the i tunnel
   * @throws GenericException the generic exception
   */
  private ITunnel induceSplitterJoiner(
      List<ITask> tasks, ITunnel dataTunnel, PipelineStatistics pipelineStatistics)
      throws GenericException {
    SplitterTask splitterTask = SplitterTask.getInstance();
    splitterTask.setExceptionHandler(genericExceptionHandler);
    splitterTask.setExecutorService(executorService);

    List<ITunnel> splitterTunnels = new ArrayList<>();
    List<ITunnel> joinerTunnels = new ArrayList<>();
    for (ITask task : tasks) {
      ITunnel splitTunnel = getDefaultTunnel(dataTunnel.getTunnelType());
      splitterTunnels.add(splitTunnel);
      ITunnel joinTunnel = intializeTask(task, splitTunnel, pipelineStatistics);
      if (joinTunnel != null) {
        joinerTunnels.add(joinTunnel);
      }
    }
    splitterTask.initializeTask(dataTunnel, splitterTunnels);

    JoinerTask aggregator = JoinerTask.getInstance();
    aggregator.setExecutorService(executorService);
    aggregator.setExceptionHandler(genericExceptionHandler);
    ITunnel outputTunnel = getDefaultTunnel(dataTunnel.getTunnelType());

    aggregator.initializeTask(joinerTunnels, outputTunnel);

    return outputTunnel;
  }

  /**
   * Initialize task.
   *
   * @param task the task
   * @param dataTunnel the data tunnel
   * @param pipelineStatistics the pipeline statistics
   * @return the i tunnel
   * @throws GenericException the generic exception
   */
  private ITunnel intializeTask(
      ITask task, ITunnel dataTunnel, PipelineStatistics pipelineStatistics)
      throws GenericException {
    AbstractTask executorTask = (AbstractTask) task;

    executorTask.setExceptionHandler(getExceptionHandler(executorTask));

    if (executorTask instanceof CassandraConnectionAware) {
      CassandraConnectionAware cassandraAwareTask = (CassandraConnectionAware) executorTask;
      cassandraAwareTask.setIrisDao(
          (IrisDao) springApplicationContext.getBean(cassandraAwareTask.getConnectionName()));
    }
    if (executorTask instanceof OracleDataSourceAware) {
      OracleDataSourceAware oracleDataSourceTask = (OracleDataSourceAware) executorTask;
      oracleDataSourceTask.setDataSource(
          (DataSource)
              springApplicationContext.getBean(oracleDataSourceTask.getOracleDataSourceBeanName()));
    }

    ITunnel publishTunnel =
        executorTask.isLastStep() ? null : getDefaultTunnel(dataTunnel.getTunnelType());
    executorTask.initializeTask(dataTunnel, publishTunnel);

    pipelineStatistics.getTaskStatistics().add(executorTask.getTaskStatistics());

    return publishTunnel;
  }

  /**
   * Gets the exception handler.
   *
   * @param executorTask the executor task
   * @return the exception handler
   */
  private IExceptionHandler getExceptionHandler(AbstractTask executorTask) {
    String exceptionHandlerName = executorTask.getExceptionHandlerName();
    if (StringUtils.isBlank(exceptionHandlerName)) {
      return genericExceptionHandler;
    } else {
      return (IExceptionHandler) springApplicationContext.getBean(exceptionHandlerName);
    }
  }

  /**
   * Replace spring tasks.
   *
   * @param actualTask the actual task
   * @param parentBlock the parent block
   * @return the i task
   * @throws GenericException the generic exception
   */
  private ITask replaceSpringTasks(ITask actualTask, ExecutionBlock parentBlock)
      throws GenericException {
    AbstractTask abstractTask = (AbstractTask) actualTask;
    if (abstractTask.isSpringBean()) {
      try {
        ITask springTask =
            (ITask)
                springApplicationContext.getBean(
                    abstractTask.getBeanQualifier(), abstractTask.getTaskConfig());

        populateSpringBeanWithActual(abstractTask, (AbstractTask) springTask);

        parentBlock.getTasks().add(springTask);
      } catch (Exception e) {
        throw new GenericException(
            String.format(
                SPRING_BASED_TASK_CREATION_EXCEPTION,
                e.getMessage(),
                abstractTask.getBeanQualifier()),
            e);
      }
      return actualTask;
    }
    return null;
  }

  /**
   * Populate spring bean with actual.
   *
   * @param abstractTask the abstract task
   * @param springTask the spring task
   */
  private void populateSpringBeanWithActual(AbstractTask abstractTask, AbstractTask springTask) {
    springTask.setAsyncTaskProcessor(abstractTask.isAsyncTaskProcessor());
    springTask.setCacheable(abstractTask.isCacheable());
    springTask.setConditionalTask(abstractTask.isConditionalTask());
    springTask.setConditionalFormula(abstractTask.getConditionalFormula());
    springTask.setInitialWaitRequired(abstractTask.isInitialWaitRequired());
    springTask.setLastStep(abstractTask.isLastStep());
    springTask.setMaxWaitTimeSeconds(abstractTask.getMaxWaitTimeSeconds());
    springTask.setTaskId(abstractTask.getTaskId());
    springTask.setTaskName(abstractTask.getTaskName());
    springTask.setWorkerCount(abstractTask.getWorkerCount());
    springTask.setExceptionHandlerName(abstractTask.getExceptionHandlerName());
    springTask.setSpringBean(abstractTask.isSpringBean());
    springTask.setExceptionHandler(getExceptionHandler(abstractTask));
  }

  /**
   * Gets the default tunnel.
   *
   * @param tunnelType the tunnel type
   * @return the default tunnel
   * @throws GenericException the generic exception
   */
  private ITunnel getDefaultTunnel(String tunnelType) throws GenericException {
    Map<String, Integer> tunnelProperties = new HashMap<>();
    tunnelProperties.put(TransConstants.TUNNEL_SIZE_KEY, defaultTunnelSize);
    return TunnelFactory.getTunnel(tunnelType, tunnelProperties);
  }

  /**
   * Run pipeline.
   *
   * @param pipeLine the pipe line
   * @param executedBy the executed by
   * @param startRec the start rec
   * @throws GenericException the generic exception
   */
  public void runPipeline(Pipeline pipeLine, String executedBy, BaseRecord startRec)
      throws GenericException {
    PipelineStatistics pipelineStatistics = pipeLine.getPipelineStatistics();
    pipelineStatistics.setRunDate(new Date().toString());
    pipelineStatistics.setExecutedBy(executedBy);
    pipelineStatistics.setStartTime(new Date().toString());
    pipelineStatistics.setStartTimeObj(new Date());
    runPipeline(pipeLine, startRec == null ? new StartOfTask() : startRec);
  }

  /**
   * Adds the shutdown hook.
   *
   * @param pipeLine the pipe line
   * @throws GenericException the generic exception
   */
  private void addShutdownHook(Pipeline pipeLine) throws GenericException {
    Optional<Boolean> lastTask =
        getAllPipelineTasks(pipeLine).stream()
            .filter(ITask::isLastStep)
            .map(
                task -> {
                  if (task instanceof PipelineContextAware) {
                    ((PipelineContextAware) task).setPipelineContext(pipelineContext);
                    return true;
                  }
                  return false;
                })
            .findAny();
    if (!lastTask.isPresent()) {
      throw new GenericException("Last Step in the pipeline not found!");
    }
  }

  /**
   * Close pipeline.
   *
   * @param pipeLine the pipe line
   */
  public void forceClosePipeline(Pipeline pipeLine) {
    log.error("Force closing pipeline!");
    getAllPipelineTasks(pipeLine)
        .forEach(
            task -> {
              try {
                task.forceStop();
                if (task.isSpringBean()) {
                  destroySpringBeans(pipeLine);
                }
              } catch (Exception e) {
                if (genericExceptionHandler != null) {
                  genericExceptionHandler.handleException(e, null);
                } else {
                  log.error("No exception handler configured");
                }
              }
            });
    /**
     * In case of wrong configuration like no last step defined, pipeline flag does not get updated
     * on ForceClose
     */
    if (isPipelineRunning() && pipelineContext != null) {
      pipelineContext.setPipelineRunningFlag(false);
    }
  }

  /**
   * Gets the all pipeline tasks.
   *
   * @param pipeLine the pipe line
   * @return the all pipeline tasks
   */
  private static List<ITask> getAllPipelineTasks(Pipeline pipeLine) {
    if (!ObjectUtils.isEmpty(pipeLine) && !ObjectUtils.isEmpty(pipeLine.getExecutionBlocks())) {
      return pipeLine.getExecutionBlocks().stream()
          .map(ExecutionBlock::getTasks)
          .flatMap(List::stream)
          .filter(ObjectUtils::isNotEmpty)
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * This method returns all the execution Tasks present in the pipeline omitting any Splitter and
   * Joiner, if present.
   *
   * @param pipeLine the pipe line
   * @return the pipeline execution tasks
   */
  public static List<ITask> getPipelineExecutionTasks(Pipeline pipeLine) {
    List<ITask> allTasks = getAllPipelineTasks(pipeLine);
    if (!ObjectUtils.isEmpty(allTasks)) {
      return allTasks.stream()
          .filter(task -> !(task instanceof SplitterTask) && !(task instanceof JoinerTask))
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  /** @author rahul.mohan */
  public static final class PipelineContext {

    private static final AtomicBoolean pipelineRunningFlag = new AtomicBoolean(false);

    private static final String STAT_FILE_MSG =
        "Statistics file %s is uploaded in SFTP server and can be found in %s. Pipeline Executor - %s";

    private Pipeline runningPipeline;
    private File pipeLineStatFile;
    @Setter private TransJsonUtil localJsonUtil;
    @Setter private ApplicationContext applicationContextReference;
    @Setter private IFileTransporter fileTransporter;
    @Setter private String remoteStatDirectory;

    private File statMsgFile;
    /**
     * Instantiates a new pipeline context.
     *
     * @param runningPipeline the running pipeline
     * @param configDirectory the config directory
     * @param jsonUtil the json util
     * @param classPathDirEnabled the class path dir enabled
     * @throws IOException Signals that an I/O exception has occurred.
     */
    PipelineContext(
        Pipeline runningPipeline,
        String configDirectory,
        TransDateUtil localDateUtil,
        boolean classPathDirEnabled)
        throws IOException {
      this.runningPipeline = runningPipeline;

      File parentDir =
          classPathDirEnabled
              ? new ClassPathResource(configDirectory).getFile()
              : new File(configDirectory);

      this.pipeLineStatFile =
          new File(
              parentDir,
              String.format(
                  TransConstants.STAT_FILE_FORMAT,
                  runningPipeline.getName(),
                  localDateUtil.formatUtilDate(
                      new Date(), TransConstants.DATEFORMAT_FOR_FILENAME)));

      this.statMsgFile = new File(parentDir, String.format("%s.STAT", runningPipeline.getName()));

      if (!this.pipeLineStatFile.exists() || this.pipeLineStatFile.createNewFile()) {
        log.info("Pipeline stat will be written to file: {}", this.pipeLineStatFile);
      }

      if (this.statMsgFile.exists() || this.statMsgFile.createNewFile()) {
        log.debug("All stat files names will be found in {}", statMsgFile);
      }
    }

    void setPipelineRunningFlag(boolean flag) {
      pipelineRunningFlag.set(flag);
    }

    public boolean isPipelineRunning() {
      return pipelineRunningFlag.get();
    }

    public Pipeline getRunningPipeline() {
      return runningPipeline;
    }

    /**
     * Close pipeline.
     *
     * @param forceStop the force stop
     */
    public void closePipeline(boolean forceStop) {
      PipelineStatistics pipelineStatistics = runningPipeline.getPipelineStatistics();
      if (!forceStop) {
        while (getAllPipelineTasks(runningPipeline).stream().anyMatch(ITask::isRunning)) {
          getAllPipelineTasks(runningPipeline).stream()
              .filter(ITask::isRunning)
              .forEach(task -> log.info("Task {} is running!", task.getTaskName()));
          try {
            Thread.sleep(1000 * 2l);
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
          }
        }
      } else {
        pipelineStatistics.setForceStoped(true);
      }

      finalizeTasks();

      pipelineStatistics.setEndTime(new Date().toString());
      pipelineStatistics.setEndTimeObj(new Date());
      pipelineStatistics.setExecutionTimeSeconds(
          Duration.between(
                      pipelineStatistics.getStartTimeObj().toInstant(),
                      pipelineStatistics.getEndTimeObj().toInstant())
                  .toMillis()
              / 1000l);

      log.info("Closing pipeline {}", runningPipeline.getName());
      setPipelineRunningFlag(false);

      ExecutorService sThread = Executors.newSingleThreadExecutor();
      sThread.submit(this::finalizePipelineStatistics);
      sThread.shutdown();
    }

    /** */
    private void finalizeTasks() {
      log.info("Destroying Spring based Tasks in the pipeline!");
      getPipelineExecutionTasks(runningPipeline).stream()
          .filter(task -> task instanceof AbstractTask)
          .map(this::flushExceptions)
          .filter(ITask::isSpringBean)
          .forEach(
              task -> {
                applicationContextReference.getAutowireCapableBeanFactory().destroyBean(task);
                log.info("Destroyed Spring Task {}", task.getTaskName());
              });
    }

    /**
     * Finalize tasks.
     *
     * @param abstractTask the abstract task
     * @return the i task
     */
    private ITask flushExceptions(ITask abstractTask) {
      ((AbstractTask) abstractTask).getExceptionHandler().finalizeExceptionHandler();
      return abstractTask;
    }

    /** Method to save pipeline statistics. */
    private void finalizePipelineStatistics() {
      try (FileWriter writer = new FileWriter(pipeLineStatFile, false)) {
        PipelineStatistics pipelineStat = runningPipeline.getPipelineStatistics();
        StringBuilder logMessage = new StringBuilder(System.lineSeparator());
        logMessage
            .append("#############################################")
            .append(System.lineSeparator());
        logMessage
            .append(localJsonUtil.getObjectAsString(pipelineStat))
            .append(System.lineSeparator());
        logMessage
            .append("#############################################")
            .append(System.lineSeparator());
        log.info(logMessage.toString());

        String statFileContent = new String(Files.readAllBytes(pipeLineStatFile.toPath()));

        PipelineStatisticsData statData = null;

        if (StringUtils.isNotBlank(statFileContent)) {
          statData =
              localJsonUtil.convertStringToObject(statFileContent, PipelineStatisticsData.class);
        } else {
          statData = PipelineStatisticsData.builder().pipelineStats(new ArrayList<>()).build();
        }
        statData.getPipelineStats().add(pipelineStat);

        writer.write(localJsonUtil.getObjectAsString(statData));

        writer.flush();
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
      log.info("Finalizing Pipeline!");
      performFinalOperations();
    }

    /**
     * @param pipeLineStatFile
     * @param remoteStatDirectory
     */
    private void performFinalOperations() {
      try (FileWriter writer = new FileWriter(this.statMsgFile, true)) {
        log.info(
            "Uploading Stat file {} to SFTP location {}!", pipeLineStatFile, remoteStatDirectory);
        fileTransporter.sendFileToSftpLocation(pipeLineStatFile, remoteStatDirectory);

        writer.write(System.lineSeparator());
        writer.write(
            String.format(
                STAT_FILE_MSG,
                pipeLineStatFile.getName(),
                remoteStatDirectory,
                getRunningPipeline().getPipelineStatistics().getExecutedBy()));
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
  }

  /** @return */
  public String getConfigFolder() {
    return configFolder;
  }

  /** @param configFolder */
  public void setConfigFolder(String configFolder) {
    this.configFolder = configFolder;
  }

  /** @return */
  public boolean isPipelineRunning() {
    return pipelineContext != null && pipelineContext.isPipelineRunning();
  }

  /** @return */
  public Pipeline getRunningPipeline() {
    return pipelineContext.getRunningPipeline();
  }

  /**
   * Run pipeline.
   *
   * @param pipeLine the pipe line
   * @param startRec the start rec
   * @throws GenericException the generic exception
   */
  private void runPipeline(Pipeline pipeLine, BaseRecord startRec) throws GenericException {
    try {
      pipelineContext =
          new PipelineContext(pipeLine, configFolder, dateUtil, classPathConfiguration);
      pipelineContext.setApplicationContextReference(springApplicationContext);
      pipelineContext.setFileTransporter(fileTransporter);
      pipelineContext.setRemoteStatDirectory(pipelineStatRemoteDir);
      pipelineContext.setLocalJsonUtil(jsonUtil);

      if (!pipelineContext.isPipelineRunning()) {
        pipelineContext.setPipelineRunningFlag(true);
        pipeLine.getPipelineDoor().publish(startRec);
        addShutdownHook(pipeLine);
        pipeLine.getPipelineDoor().publish(new EndOfRecord());
      } else {
        throw new GenericException("One pipeline already running");
      }
    } catch (GenericException e) {
      throw e;
    } catch (Exception e) {
      throw new GenericException(e);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.springApplicationContext = applicationContext;
    log.trace(
        "Beans available: {}", Arrays.asList(springApplicationContext.getBeanDefinitionNames()));
  }

  /**
   * Destroy spring beans.
   *
   * @param runningPipeline the running pipeline
   */
  private void destroySpringBeans(Pipeline runningPipeline) {
    log.info("Force destroying Spring based Tasks in the pipeline!");
    getPipelineExecutionTasks(runningPipeline).stream()
        .filter(ITask::isSpringBean)
        .forEach(
            task -> {
              springApplicationContext.getAutowireCapableBeanFactory().destroyBean(task);
              log.info("Force destroyed Spring Bean {}", task.getTaskName());
            });
  }
}
