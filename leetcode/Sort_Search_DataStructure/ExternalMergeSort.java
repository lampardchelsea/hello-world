/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Refer to:
 * http://www.ashishsharma.me/2011/08/external-merge-sort.html
 * http://pages.cs.wisc.edu/~jignesh/cs564/notes/lec07-sorting.pdf
 * http://svn.apache.org/repos/asf/jackrabbit/oak/trunk/oak-commons/src/main/java/org/apache/jackrabbit/oak/commons/sort/ExternalSort.java
 * http://stackoverflow.com/questions/20802396/how-external-merge-sort-algorithm-works
 * http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSort.htm
 * http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX1.htm
 * http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX2.htm
 * http://stackoverflow.com/questions/683041/java-how-do-i-use-a-priorityqueue
 * http://stackoverflow.com/questions/5055909/algorithm-for-n-way-merge
 * http://stackoverflow.com/questions/19474924/merging-k-sorted-lists-using-priority-queue
 * https://courses.cs.washington.edu/courses/cse373/06sp/handouts/lecture08.pdf
 */
package org.apache.jackrabbit.oak.commons.sort;

// filename: ExternalSort.java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * Source copied from a publicly available library.
 * @see <a href="https://code.google.com/p/externalsortinginjava/">https://code.google.com/p/externalsortinginjava</a>
 *
 * <pre>
 * Goal: offer a generic external-memory sorting program in Java.
 * 
 * It must be : - hackable (easy to adapt) - scalable to large files - sensibly efficient.
 * 
 * This software is in the public domain.
 * 
 * Usage: java org/apache/oak/commons/sort//ExternalSort somefile.txt out.txt
 * 
 * You can change the default maximal number of temporary files with the -t flag: java
 * org/apache/oak/commons/sort/ExternalSort somefile.txt out.txt -t 3
 * 
 * You can change the default maximum memory available with the -m flag: java
 * org/apache/oak/commons/sort/ExternalSort somefile.txt out.txt -m 8192
 * 
 * For very large files, you might want to use an appropriate flag to allocate more memory to
 * the Java VM: java -Xms2G org/apache/oak/commons/sort/ExternalSort somefile.txt out.txt
 * 
 * By (in alphabetical order) Philippe Beaudoin, Eleftherios Chetzakis, Jon Elsas, Christan
 * Grant, Daniel Haran, Daniel Lemire, Sugumaran Harikrishnan, Jerry Yang, First published:
 * April 2010 originally posted at
 * http://lemire.me/blog/archives/2010/04/01/external-memory-sorting-in-java/
 * </pre>
 */
public class ExternalSort {

    /*
     * This sorts a file (input) to an output file (output) using default parameters
     * 
     * @param file source file
     * 
     * @param file output file
     */
    public static void sort(File input, File output) throws IOException {
        ExternalSort.mergeSortedFiles(ExternalSort.sortInBatch(input), output);
    }
    
    static final int DEFAULTMAXTEMPFILES = 1024;
    
    /**
    * Defines the default maximum memory to be used while sorting (8 MB)
    */
    static final long DEFAULT_MAX_MEM_BYTES = 8388608L;
    
    // we divide the file into small blocks. If the blocks
    // are too small, we shall create too many temporary files.
    // If they are too big, we shall be using too much memory.
    public static long estimateBestSizeOfBlocks(File filetobesorted,
            int maxtmpfiles, long maxMemory) {
        long sizeoffile = filetobesorted.length() * 2;
        /**
         * We multiply by two because later on someone insisted on counting the memory usage as 2
         * bytes per character. By this model, loading a file with 1 character will use 2 bytes.
         */
        // we don't want to open up much more than maxtmpfiles temporary
        // files, better run
        // out of memory first.
        long blocksize = sizeoffile / maxtmpfiles
                + (sizeoffile % maxtmpfiles == 0 ? 0 : 1);

        // on the other hand, we don't want to create many temporary
        // files
        // for naught. If blocksize is less than maximum allowed memory,
        // scale the blocksize to be equal to the maxMemory parameter

        if (blocksize < maxMemory) {
            blocksize = maxMemory;
        }
        return blocksize;
    }

    /**
     * This will simply load the file by blocks of lines, then sort them in-memory, and write the
     * result to temporary files that have to be merged later.
     * 
     * @param file
     *            some flat file
     * @return a list of temporary flat files
     */
    public static List<File> sortInBatch(File file)
            throws IOException {
        return sortInBatch(file, defaultcomparator, DEFAULTMAXTEMPFILES, DEFAULT_MAX_MEM_BYTES,
                Charset.defaultCharset(), null, false);
    }

    /**
     * This will simply load the file by blocks of lines, then sort them in-memory, and write the
     * result to temporary files that have to be merged later.
     * 
     * @param file
     *            some flat file
     * @param cmp
     *            string comparator
     * @return a list of temporary flat files
     */
    public static List<File> sortInBatch(File file, Comparator<String> cmp)
            throws IOException {
        return sortInBatch(file, cmp, DEFAULTMAXTEMPFILES, DEFAULT_MAX_MEM_BYTES,
                Charset.defaultCharset(), null, false);
    }

    /**
     * This will simply load the file by blocks of lines, then sort them in-memory, and write the
     * result to temporary files that have to be merged later.
     * 
     * @param file
     *            some flat file
     * @param cmp
     *            string comparator
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded.
     * @return a list of temporary flat files
     */
    public static List<File> sortInBatch(File file, Comparator<String> cmp,
            boolean distinct) throws IOException {
        return sortInBatch(file, cmp, DEFAULTMAXTEMPFILES, DEFAULT_MAX_MEM_BYTES,
                Charset.defaultCharset(), null, distinct);
    }

    /**
     * This will simply load the file by blocks of lines, then sort them in-memory, and write the
     * result to temporary files that have to be merged later. You can specify a bound on the number
     * of temporary files that will be created.
     * 
     * @param file
     *            some flat file
     * @param cmp
     *            string comparator
     * @param maxtmpfiles
     *            maximal number of temporary files
     * @param cs
     *            character set to use (can use Charset.defaultCharset())
     * @param tmpdirectory
     *            location of the temporary files (set to null for default location)
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded.
     * @param numHeader
     *            number of lines to preclude before sorting starts
     * @param usegzip use gzip compression for the temporary files
     * @return a list of temporary flat files
     */
    public static List<File> sortInBatch(File file, Comparator<String> cmp,
            int maxtmpfiles, long maxMemory, Charset cs, File tmpdirectory,
            boolean distinct, int numHeader, boolean usegzip)
            throws IOException {
        List<File> files = new ArrayList<File>();
        BufferedReader fbr = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), cs));
        // in bytes
        long blocksize = estimateBestSizeOfBlocks(file, maxtmpfiles, maxMemory);

        try {
            List<String> tmplist = new ArrayList<String>();
            String line = "";
            try {
                int counter = 0;
                while (line != null) {
                    // in bytes
                    long currentblocksize = 0;
                    while ((currentblocksize < blocksize)
                            && ((line = fbr.readLine()) != null)) {
                        // as long as you have enough memory
                        if (counter < numHeader) {
                            counter++;
                            continue;
                        }
                        tmplist.add(line);
                        // ram usage estimation, not
                        // very accurate, still more
                        // realistic that the simple 2 *
                        // String.length
                        currentblocksize += StringSizeEstimator
                                .estimatedSizeOf(line);
                    }
                    files.add(sortAndSave(tmplist, cmp, cs,
                            tmpdirectory, distinct, usegzip));
                    tmplist.clear();
                }
            } catch (EOFException oef) {
                if (tmplist.size() > 0) {
                    files.add(sortAndSave(tmplist, cmp, cs,
                            tmpdirectory, distinct, usegzip));
                    tmplist.clear();
                }
            }
        } finally {
            fbr.close();
        }
        return files;
    }

    /**
     * This will simply load the file by blocks of lines, then sort them in-memory, and write the
     * result to temporary files that have to be merged later. You can specify a bound on the number
     * of temporary files that will be created.
     * 
     * @param file
     *            some flat file
     * @param cmp
     *            string comparator
     * @param maxtmpfiles
     *            maximal number of temporary files
     * @param cs
     *            character set to use (can use Charset.defaultCharset())
     * @param tmpdirectory
     *            location of the temporary files (set to null for default location)
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded.
     * @return a list of temporary flat files
     */
    public static List<File> sortInBatch(File file, Comparator<String> cmp,
            int maxtmpfiles, long maxMemory, Charset cs, File tmpdirectory, boolean distinct)
            throws IOException {
        return sortInBatch(file, cmp, maxtmpfiles, maxMemory, cs, tmpdirectory,
                distinct, 0, false);
    }

    /**
     * Sort a list and save it to a temporary file
     * 
     * @return the file containing the sorted data
     * @param tmplist
     *            data to be sorted
     * @param cmp
     *            string comparator
     * @param cs
     *            charset to use for output (can use Charset.defaultCharset())
     * @param tmpdirectory
     *            location of the temporary files (set to null for default location)
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded.
     */
    public static File sortAndSave(List<String> tmplist,
            Comparator<String> cmp, Charset cs, File tmpdirectory,
            boolean distinct, boolean usegzip) throws IOException {
        Collections.sort(tmplist, cmp);
        File newtmpfile = File.createTempFile("sortInBatch",
                "flatfile", tmpdirectory);
        newtmpfile.deleteOnExit();
        OutputStream out = new FileOutputStream(newtmpfile);
        int zipBufferSize = 2048;
        if (usegzip) {
            out = new GZIPOutputStream(out, zipBufferSize) {
                {
                    def.setLevel(Deflater.BEST_SPEED);
                }
            };
        }
        BufferedWriter fbw = new BufferedWriter(new OutputStreamWriter(
                out, cs));
        String lastLine = null;
        try {
            for (String r : tmplist) {
                // Skip duplicate lines
                if (!distinct || (lastLine == null || (lastLine != null && cmp.compare(r, lastLine) != 0))) {
                    fbw.write(r);
                    fbw.newLine();
                    lastLine = r;
                }
            }
        } finally {
            fbw.close();
        }
        return newtmpfile;
    }

    /**
     * Sort a list and save it to a temporary file
     * 
     * @return the file containing the sorted data
     * @param tmplist
     *            data to be sorted
     * @param cmp
     *            string comparator
     * @param cs
     *            charset to use for output (can use Charset.defaultCharset())
     * @param tmpdirectory
     *            location of the temporary files (set to null for default location)
     */
    public static File sortAndSave(List<String> tmplist,
            Comparator<String> cmp, Charset cs, File tmpdirectory)
            throws IOException {
        return sortAndSave(tmplist, cmp, cs, tmpdirectory, false, false);
    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     * @param outputfile
     *            file
     * @return The number of lines sorted. (P. Beaudoin)
     */
    public static int mergeSortedFiles(List<File> files, File outputfile) throws IOException {
        return mergeSortedFiles(files, outputfile, defaultcomparator,
                Charset.defaultCharset());
    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     * @param outputfile
     *            file
     * @return The number of lines sorted. (P. Beaudoin)
     */
    public static int mergeSortedFiles(List<File> files, File outputfile,
            final Comparator<String> cmp) throws IOException {
        return mergeSortedFiles(files, outputfile, cmp,
                Charset.defaultCharset());
    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     * @param outputfile
     *            file
     * @return The number of lines sorted. (P. Beaudoin)
     */
    public static int mergeSortedFiles(List<File> files, File outputfile,
            final Comparator<String> cmp, boolean distinct)
            throws IOException {
        return mergeSortedFiles(files, outputfile, cmp,
                Charset.defaultCharset(), distinct);
    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     *            The {@link List} of sorted {@link File}s to be merged.
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded. (elchetz@gmail.com)
     * @param outputfile
     *            The output {@link File} to merge the results to.
     * @param cmp
     *            The {@link Comparator} to use to compare {@link String}s.
     * @param cs
     *            The {@link Charset} to be used for the byte to character conversion.
     * @param append
     *            Pass <code>true</code> if result should append to {@link File} instead of
     *            overwrite. Default to be false for overloading methods.
     * @param usegzip
     *            assumes we used gzip compression for temporary files
     * @return The number of lines sorted. (P. Beaudoin)
     * @since v0.1.4
     */
    public static int mergeSortedFiles(List<File> files, File outputfile,
            final Comparator<String> cmp, Charset cs, boolean distinct,
            boolean append, boolean usegzip) throws IOException {
        ArrayList<BinaryFileBuffer> bfbs = new ArrayList<BinaryFileBuffer>();
        for (File f : files) {
            final int bufferSize = 2048;
            InputStream in = new FileInputStream(f);
            BufferedReader br;
            if (usegzip) {
                br = new BufferedReader(new InputStreamReader(
                        new GZIPInputStream(in, bufferSize), cs));
            } else {
                br = new BufferedReader(new InputStreamReader(in,
                        cs));
            }

            BinaryFileBuffer bfb = new BinaryFileBuffer(br);
            bfbs.add(bfb);
        }
        BufferedWriter fbw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputfile, append), cs));
        int rowcounter = merge(fbw, cmp, distinct, bfbs);
        for (File f : files) {
            f.delete();
        }
        return rowcounter;
    }

    /**
     * This merges several BinaryFileBuffer to an output writer.
     * <p>The structure to use PriorityQueue is elegant:</p>
     * <p>Basic Idea:
     * See <a href="http://stackoverflow.com/questions/19474924/merging-k-sorted-lists-using
     * -priority-queue">Merging K- Sorted Lists using Priority Queue</a>
     * The question description part is high-level design of external sort with use of
     * priority queue:</p>
     * <p>I have been asked in my algorithm class to make a K-way merge algorithm which is of 
     * O(nlogk) After searching i found it could be done via making a k length priority queue 
     * and enqueuing it with the first element of each list. Extract the minimum, append it 
     * to result and enqueue from the list whose element has been extracted.
     * (1) How will it know when a particular list is exhausted, suppose a list has smaller 
     * elements than every other element in other lists?
     * (2) How will it know the element was of which list(if a structue is not used to define)?
     * (3) How is the time complexity O(nlogk)?</p>
     * 
     * <p>The best explanation of external merge sort:</p>
     * <p>See <a href="http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/
     * L17-ExternalSortEX1.htm">External Sorting</a></p>
     * 
     * <p>Implement in this merge() method:</p>
     * <p>As each file(contain in {@code List<BinaryFileBuffer>} buffers) already been sorted by 
     * {@link #sortAndSave(List, Comparator, Charset, File, boolean, boolean)} method (the 
     * basic chunk or file used for merge sort required in sorted order is pre-requisition 
     * to implement merge sort). To merge small BinaryFileBuffer(s) to final file, every time we 
     * only pick up the most ahead BinaryFileBuffer on priority queue by poll() method, as 
     * BinaryFileBuffer actually contains the sorted lines of strings, we only retrieve the top 
     * string in BinaryFileBuffer by using pop() method(BinaryFileBuffer is implement by 
     * {@link java.io.BufferedReader#readLine()}}) and write it into final file. The most tricky
     * part is after this action, we need to implement recursively comparison with other 
     * BinaryFileBuffer, the implementation here is after pop out the first line on current
     * BinaryFileBuffer, if it is not empty, then reload this BinaryFileBuffer to priority queue,
     * the new BinaryFileBuffer(the new most ahead BinaryFileBuffer on queue) that need to poll out 
     * first line on queue will be determined by new header(as start with previous second line) of 
     * this BinaryFileBuffer compare with other BinaryFileBuffer(s), and because of this the new 
     * most ahead BinaryFileBuffer might be another BinaryFileBuffer, and will prepare to be polled 
     * out by poll() method and do same following steps. The process exactly match <a href="http://
     * faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX1.htm">External 
     * Sorting</a>'s description</p>
     * 
     * <p><pre>
     * Example: 
     * BinaryFileBuffer x: line 1 -> "3", line 2 -> "5", line 3 -> "9" 
     * BinrayFileBuffer y: line 1 -> "2", line 2 -> "7", line 3 -> "8"
     * BinrayFileBuffer z: line 1 -> "1", line 2 -> "4", line 3 -> "6"
     * PriorityQueue q: empty
     * Final File f: empty
     * Put x, y and z into q, because the comparator compare with below code part,
     * <pre>
     * {@code   new Comparator<BinaryFileBuffer>() {
                    @Override
                    public int compare(BinaryFileBuffer i,
                            BinaryFileBuffer j) {
                        return cmp.compare(i.peek(), j.peek());
                    }
                }}
     * </pre>
     * The effect is comparing first line of x, y and z with natural order, as "3" > "2" > "1", 
     * As beginning, when we initialize q, the order is
     * PriorityQueue q: z, y, x
     * Later pop out line 1 of z, now z is not empty, we need to put new z back to q, the new position
     * of z on q need to define based on new line 1 of z compare with line 1 of x and y, as "4" > "3" 
     * > "2", y will become the new most ahead BinaryFileBuffer and need to poll out first line.
     * BinaryFileBuffer x: line 1 -> "3", line 2 -> "5", line 3 -> "9" 
     * BinrayFileBuffer y: line 1 -> "2", line 2 -> "7", line 3 -> "8"
     * BinrayFileBuffer z: line 2 -> "4", line 3 -> "6"
     * PriorityQueue q: y, x, z
     * Final file f: "1"
     * 
     * Now pop out line 1 of y, now y is not empty, we need to put new y back to q, the new position
     * of y on q need to define based on new line 1 of y compare with line 1 of x and z, as "7" > "4"
     * > "3", x will become the new most ahead BinaryFileBuffer and need to poll out first line.
     * BinaryFileBuffer x: line 1 -> "3", line 2 -> "5", line 3 -> "9" 
     * BinrayFileBuffer y: line 1 -> "7", line 2 -> "8"
     * BinrayFileBuffer z: line 2 -> "4", line 3 -> "6"
     * PriorityQueue q: x, z, y
     * Final file f: "1", "2"
     * 
     * Repeat the same thing until all BinaryFileBuffer(s) empty.
     * Final file f: "1", "2", "3", "4", "5", "6", "7", "8", "9"
     * </pre></p>
     * 
     * 
     * @param fbw
     *            A buffer where we write the data.
     * @param cmp
     *            A comparator object that tells us how to sort the lines.
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded. (elchetz@gmail.com)
     * @param buffers
     *            Where the data should be read.
     * @return The number of lines sorted. (P. Beaudoin)
     * 
     * 
     */
    public static int merge(BufferedWriter fbw, final Comparator<String> cmp, boolean distinct,
            List<BinaryFileBuffer> buffers) throws IOException {
        PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(
                11, new Comparator<BinaryFileBuffer>() {
                    @Override
                    public int compare(BinaryFileBuffer i,
                            BinaryFileBuffer j) {
                        return cmp.compare(i.peek(), j.peek());
                    }
                });
        for (BinaryFileBuffer bfb : buffers) {
            if (!bfb.empty()) {
                pq.add(bfb);
            }
        }
        int rowcounter = 0;
        String lastLine = null;
        try {
            while (pq.size() > 0) {
                BinaryFileBuffer bfb = pq.poll();
                String r = bfb.pop();
                // Skip duplicate lines
                if (!distinct || (lastLine == null || (lastLine != null && cmp.compare(r, lastLine) != 0))) {
                    fbw.write(r);
                    fbw.newLine();
                    lastLine = r;
                }
                ++rowcounter;
                if (bfb.empty()) {
                    bfb.fbr.close();
                } else {
                    pq.add(bfb); // add it back
                }
            }
        } finally {
            fbw.close();
            for (BinaryFileBuffer bfb : buffers) {
                bfb.close();
            }
        }
        return rowcounter;

    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     *            The {@link List} of sorted {@link File}s to be merged.
     * @param distinct
     *            Pass <code>true</code> if duplicate lines should be discarded. (elchetz@gmail.com)
     * @param outputfile
     *            The output {@link File} to merge the results to.
     * @param cmp
     *            The {@link Comparator} to use to compare {@link String}s.
     * @param cs
     *            The {@link Charset} to be used for the byte to character conversion.
     * @return The number of lines sorted. (P. Beaudoin)
     * @since v0.1.2
     */
    public static int mergeSortedFiles(List<File> files, File outputfile,
            final Comparator<String> cmp, Charset cs, boolean distinct)
            throws IOException {
        return mergeSortedFiles(files, outputfile, cmp, cs, distinct,
                false, false);
    }

    /**
     * This merges a bunch of temporary flat files
     * 
     * @param files
     * @param outputfile
     *            file
     * @param cs
     *            character set to use to load the strings
     * @return The number of lines sorted. (P. Beaudoin)
     */
    public static int mergeSortedFiles(List<File> files, File outputfile,
            final Comparator<String> cmp, Charset cs) throws IOException {
        return mergeSortedFiles(files, outputfile, cmp, cs, false);
    }

    public static void displayUsage() {
        System.out
                .println("java com.google.externalsorting.ExternalSort inputfile outputfile");
        System.out.println("Flags are:");
        System.out.println("-v or --verbose: verbose output");
        System.out.println("-d or --distinct: prune duplicate lines");
        System.out
                .println("-t or --maxtmpfiles (followed by an integer): specify an upper bound on the number of temporary files");
        System.out
                .println("-m or --maxmembytes (followed by a long): specify an upper bound on the memory");
        System.out
                .println("-c or --charset (followed by a charset code): specify the character set to use (for sorting)");
        System.out
                .println("-z or --gzip: use compression for the temporary files");
        System.out
                .println("-H or --header (followed by an integer): ignore the first few lines");
        System.out
                .println("-s or --store (following by a path): where to store the temporary files");
        System.out.println("-h or --help: display this message");
    }

    public static void main(String[] args) throws IOException {
        boolean verbose = false;
        boolean distinct = false;
        int maxtmpfiles = DEFAULTMAXTEMPFILES;
        long maxMemory = DEFAULT_MAX_MEM_BYTES;        
        Charset cs = Charset.defaultCharset();
        String inputfile = null, outputfile = null;
        File tempFileStore = null;
        boolean usegzip = false;
        int headersize = 0;
        for (int param = 0; param < args.length; ++param) {
            if (args[param].equals("-v")
                    || args[param].equals("--verbose")) {
                verbose = true;
            } else if (args[param].equals("-h") || args[param]
                    .equals("--help")) {
                displayUsage();
                return;
            } else if (args[param].equals("-d") || args[param]
                    .equals("--distinct")) {
                distinct = true;
            } else if ((args[param].equals("-t") || args[param]
                    .equals("--maxtmpfiles"))
                    && args.length > param + 1) {
                param++;
                maxtmpfiles = Integer.parseInt(args[param]);
                if (headersize < 0) {
                    System.err
                            .println("maxtmpfiles should be positive");
                }
            } else if ((args[param].equals("-m") || args[param]
                        .equals("--maxmembytes"))
                        && args.length > param + 1) {
                    param++;
                    maxMemory = Long.parseLong(args[param]);
                    if (headersize < 0) {
                        System.err
                            .println("maxmembytes should be positive");
                    }
            } else if ((args[param].equals("-c") || args[param]
                    .equals("--charset"))
                    && args.length > param + 1) {
                param++;
                cs = Charset.forName(args[param]);
            } else if (args[param].equals("-z") || args[param]
                    .equals("--gzip")) {
                usegzip = true;
            } else if ((args[param].equals("-H") || args[param]
                    .equals("--header")) && args.length > param + 1) {
                param++;
                headersize = Integer.parseInt(args[param]);
                if (headersize < 0) {
                    System.err
                            .println("headersize should be positive");
                }
            } else if ((args[param].equals("-s") || args[param]
                    .equals("--store")) && args.length > param + 1) {
                param++;
                tempFileStore = new File(args[param]);
            } else {
                if (inputfile == null) {
                    inputfile = args[param];
                } else if (outputfile == null) {
                    outputfile = args[param];
                } else {
                    System.out.println("Unparsed: "
                            + args[param]);
                }
            }
        }
        if (outputfile == null) {
            System.out
                    .println("please provide input and output file names");
            displayUsage();
            return;
        }
        Comparator<String> comparator = defaultcomparator;
        List<File> l = sortInBatch(new File(inputfile), comparator,
                maxtmpfiles, maxMemory, cs, tempFileStore, distinct, headersize,
                usegzip);
        if (verbose) {
            System.out
                    .println("created " + l.size() + " tmp files");
        }
        mergeSortedFiles(l, new File(outputfile), comparator, cs,
                distinct, false, usegzip);
    }

    public static Comparator<String> defaultcomparator = new Comparator<String>() {
        @Override
        public int compare(String r1, String r2) {
            return r1.compareTo(r2);
        }
    };
}

class BinaryFileBuffer {
    public BufferedReader fbr;
    private String cache;
    private boolean empty;

    public BinaryFileBuffer(BufferedReader r)
            throws IOException {
        this.fbr = r;
        reload();
    }

    public boolean empty() {
        return this.empty;
    }

    private void reload() throws IOException {
        try {
            if ((this.cache = fbr.readLine()) == null) {
                this.empty = true;
                this.cache = null;
            } else {
                this.empty = false;
            }
        } catch (EOFException oef) {
            this.empty = true;
            this.cache = null;
        }
    }

    public void close() throws IOException {
        this.fbr.close();
    }

    public String peek() {
        if (empty()) {
            return null;
        }
        return this.cache;
    }

    public String pop() throws IOException {
        String answer = peek();
        reload();
        return answer;
    }

}












/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.apache.jackrabbit.oak.commons.sort;

/**
 * Source copied from a publicly available library.
 * @see <a
 *  href="https://code.google.com/p/externalsortinginjava/">https://code.google.com/p/externalsortinginjava</a>
 * 
 * @author Eleftherios Chetzakis
 * 
 */
public final class StringSizeEstimator {

    private static final int OBJ_HEADER;
    private static final int ARR_HEADER;
    private static final int INT_FIELDS = 12;
    private static final int OBJ_REF;
    private static final int OBJ_OVERHEAD;
    private static final boolean IS_64_BIT_JVM;

    /**
     * Private constructor to prevent instantiation.
     */
    private StringSizeEstimator() {
    }

    /**
     * Class initializations.
     */
    static {
        // By default we assume 64 bit JVM
        // (defensive approach since we will get
        // larger estimations in case we are not sure)
        boolean is64Bit = true;
        // check the system property "sun.arch.data.model"
        // not very safe, as it might not work for all JVM implementations
        // nevertheless the worst thing that might happen is that the JVM is 32bit
        // but we assume its 64bit, so we will be counting a few extra bytes per string object
        // no harm done here since this is just an approximation.
        String arch = System.getProperty("sun.arch.data.model");
        if (arch != null) {
            if (arch.indexOf("32") != -1) {
                // If exists and is 32 bit then we assume a 32bit JVM
                is64Bit = false;
            }
        }
        IS_64_BIT_JVM = is64Bit;
        // The sizes below are a bit rough as we don't take into account 
        // advanced JVM options such as compressed oops
        // however if our calculation is not accurate it'll be a bit over
        // so there is no danger of an out of memory error because of this.
        OBJ_HEADER = IS_64_BIT_JVM ? 16 : 8;
        ARR_HEADER = IS_64_BIT_JVM ? 24 : 12;
        OBJ_REF = IS_64_BIT_JVM ? 8 : 4;
        OBJ_OVERHEAD = OBJ_HEADER + INT_FIELDS + OBJ_REF + ARR_HEADER;

    }

    /**
     * Estimates the size of a {@link String} object in bytes.
     * 
     * @param s The string to estimate memory footprint.
     * @return The <strong>estimated</strong> size in bytes.
     */
    public static long estimatedSizeOf(String s) {
        return (s.length() * 2) + OBJ_OVERHEAD;
    }

}





