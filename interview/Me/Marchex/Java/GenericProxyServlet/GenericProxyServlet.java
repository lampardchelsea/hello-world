package com.polk.soa.gateway.servlet;

import static com.polk.tetra.utl.log.SingleLineLogger.Singleton;

import com.polk.soa.gateway.config.ProxyConfig;
import com.polk.soa.gateway.init.RemoteCapacity;
import com.polk.soa.gateway.priority.ActivePriorityQueue;
import com.polk.soa.gateway.priority.PriorityRequest;
import com.polk.soa.gateway.priority.ServiceInfo;
import com.polk.soa.gateway.server.GatewayThreadPoolExecutor;
import com.polk.soa.gateway.util.ProxyUtil;
import com.polk.soa.spi.ref.ProviderHandler;
import com.polk.tetra.utl.data.Pair;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.InputStreamContentProvider;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;

/**
 * Extensible Asynchronous ProxyServlet.
 * <p>
 * Acts as a Transparent Proxy and forwards requests to another server per HTTP 1.1 standards (as defined by RFC2616).
 * </p>
 * <p>
 * This class provided extension points for specific proxy implementations to extend this servlet and apply relevant
 * rules for that proxy
 * </p>
 *
 * @author Raj Gattu
 */
public class GenericProxyServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2263901577118559434L;

	/** The log. */
	private Logger log = Singleton.getLogger(GenericProxyServlet.class.getCanonicalName());

	/** The Constant ASYNC_CONTEXT. */
	protected static final String ASYNC_CONTEXT = GenericProxyServlet.class.getName() + ".asyncContext";

	/** The priority pool. */
	private final ExecutorService priorityPool = Executors.newCachedThreadPool();
	private final GatewayThreadPoolExecutor exec = new GatewayThreadPoolExecutor();

	/** The Constant HOP_HEADERS. */
	private static final Set<String> HOP_HEADERS = new HashSet<>();

	@Inject
	private RemoteCapacity remoteCapacity;

	/** The pq handler. */
	@SuppressWarnings("unused")
	// TODO: Work in progress
	private static PriorityQueueHandler pqHandler; // = new PriorityQueueHandler(remoteCapacity);

	static {
		HOP_HEADERS.add("proxy-connection");
		HOP_HEADERS.add("connection");
		HOP_HEADERS.add("keep-alive");
		HOP_HEADERS.add("transfer-encoding");
		HOP_HEADERS.add("te");
		HOP_HEADERS.add("trailer");
		HOP_HEADERS.add("proxy-authorization");
		HOP_HEADERS.add("proxy-authenticate");
		HOP_HEADERS.add("upgrade");
		HOP_HEADERS.add("referer");
		HOP_HEADERS.add("cookie");
	}

	/** The via host. */
	private String viaHost;

	/** The timeout. */
	private long timeout;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		pqHandler = new PriorityQueueHandler(remoteCapacity);
		viaHost = viaHost();
		// ServletContext servletContext = getServletContext();
		// proxyHttpClient = (HttpClient) servletContext.getAttribute("proxy.httpclient");
	}

	/**
	 * Extension for the http client.
	 *
	 * @param servletRequest
	 *            the servlet request
	 * @return the proxy http client
	 */
	protected HttpClient getProxyHttpClient(HttpServletRequest servletRequest) {
		return null;
	}

	/**
	 * Sets the via host.
	 *
	 * @param viaHost
	 *            the new via host
	 */
	public void setViaHost(String viaHost) {
		this.viaHost = viaHost;
	}

	/**
	 * Gets the via host.
	 *
	 * @return the via host
	 */
	public String getViaHost() {
		return viaHost;
	}

	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Sets the timeout.
	 *
	 * @param timeout
	 *            the new timeout
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * Via host.
	 *
	 * @return the string
	 */
	protected static String viaHost() {
		try {
			return InetAddress.getLocalHost()
					.getHostName();
		} catch (UnknownHostException x) {
			return "localhost";
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		// try {
		// proxyHttpClient.stop();
		// } catch (Exception x) {
		// log.info(x.getMessage());
		// }
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		Pair<URI, Integer> newUriWithStatus = rewriteURI(request);
		URI rewrittenURI = newUriWithStatus.getKey();
		if (newUriWithStatus.getValue()
				.intValue() == 401
				|| newUriWithStatus.getValue()
						.intValue() == 403) {
			processError(request, response, newUriWithStatus.getValue()
					.intValue());
			return;
		} else if (newUriWithStatus.getValue()
				.intValue() == 999) {
			redirectTo(request, response, rewrittenURI);
			return;
		}

		if (rewrittenURI == null) {
			onRewriteFailed(request, response);
			return;
		}

		// ProviderHandler provider = ProviderHandler.locateProvider(pqHandler.getRemoteCapacity(), request);
		// PriorityRequest priorityRequest = provider.createPriorityRequest(pqHandler, request);
		// pqHandler.process(priorityRequest);

		// if (request.getMethod()
		// .equals(HttpMethod.POST.asString())) {
		// Future<Integer> futureCall = getFutureCall(request, response, rewrittenURI);
		// PriorityRequest priorityRequest = ProviderHandler.locateProvider(pqHandler.getRemoteCapacity(), request)
		// .createPriorityRequest(pqHandler, request, futureCall);
		// pqHandler.process(priorityRequest);
		// } else {
		
		if (request.getMethod().equals(HttpMethod.POST.asString())) 
		{		
				Future<Integer> futureCall = getFutureCall(request, response, rewrittenURI);
				PriorityRequest priorityRequest = ProviderHandler.locateProvider(pqHandler.getRemoteCapacity(), request)
				.createPriorityRequest(pqHandler, request, futureCall);
				pqHandler.process(priorityRequest);
		} else {
			final Pair<Request, Boolean> proxyRequest = getProxyRequest(request, rewrittenURI);
			asyncCall(request, response, proxyRequest.getKey(), proxyRequest.getValue()
					.booleanValue());
		}

	}
	
	
	// private PriorityRequest createPriorityRequest(HttpServletRequest request, HttpServletResponse response,
	// URI rewrittenURI) {
	// Future<Integer> futureCall = getFutureCall(request, response, rewrittenURI);
	// Map<String, String> queryMap = ProxyUtil.getQueryMap(request.getQueryString());
	// String svcName = queryMap.get("service");
	// String svcType = queryMap.get("type");
	// String nodeName = pqHandler.getRemoteCapacity()
	// .getNode(svcName, svcType);
	// ServiceInfo service = new ServiceInfo(svcName, svcType, nodeName);
	// // client priority hard coded to 1 - replace from config table or config xml
	// PriorityRequest priorityRequest = new PriorityRequest(request, futureCall, service, 1);
	// return priorityRequest;
	// }

	/**
	 * Gets the future call.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param rewrittenURI
	 *            the rewritten URI
	 * @return the future call
	 */
	@SuppressWarnings("unused")
	// TODO: Work in progress
	private Future<Integer> getFutureCall(HttpServletRequest request, HttpServletResponse response, URI rewrittenURI) {
		Future<Integer> number =  priorityPool.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				final Pair<Request, Boolean> proxyRequest = getProxyRequest(request, rewrittenURI);
				asyncCall(request, response, proxyRequest.getKey(), proxyRequest.getValue()
							.booleanValue());
				return System.identityHashCode(request);
				//return Integer.valueOf(1478861525);
			}
		});
		return number;
	}

	/**
	 * Gets the proxy request.
	 *
	 * @param request
	 *            the request
	 * @param uri
	 *            the uri
	 * @return the proxy request
	 */
	protected Pair<Request, Boolean> getProxyRequest(HttpServletRequest request, URI uri) {
		ProxyConfig proxyConfig = ProxyUtil.getProxyConfig(request);
		Request proxyRequest = null;
		String method = request.getMethod();
		HttpClient proxyHttpClient = getProxyHttpClient(request);
		System.out.println("getProxyRequest: " + request.getMethod() + " >> " + uri);
		if (method.equalsIgnoreCase("POST")) {
			proxyRequest = proxyHttpClient.POST(uri)
					.version(HttpVersion.fromString(request.getProtocol()));
		} else {

			proxyRequest = proxyHttpClient.newRequest(uri)
					.method(request.getMethod())
					.version(HttpVersion.fromString(request.getProtocol()));
		}

		// Copy headers
		boolean hasContent = false;
		for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements();) {
			String headerName = headerNames.nextElement();
			String lowerHeaderName = headerName.toLowerCase(Locale.ENGLISH);

			// Remove hop-by-hop headers
			if (HOP_HEADERS.contains(lowerHeaderName)) {
				continue;
			}

			if (proxyConfig.getHostHeader() != null && HttpHeader.HOST.is(proxyConfig.getHostHeader())) {
				continue;
			}

			if (request.getContentLength() > 0
					|| request.getContentType() != null
					|| HttpHeader.TRANSFER_ENCODING.is(headerName)) {
				hasContent = true;
			}

			for (Enumeration<String> headerValues = request.getHeaders(headerName); headerValues.hasMoreElements();) {
				String headerValue = headerValues.nextElement();
				if (headerValue != null) {
					proxyRequest.header(headerName, headerValue);
				}
			}
		}

		// Force the Host header if configured
		if (proxyConfig.getHostHeader() != null) {
			proxyRequest.header(HttpHeader.HOST, proxyConfig.getHostHeader());
		}

		// Add proxy headers
		addViaHeader(proxyRequest);
		addXForwardedHeaders(proxyRequest, request);

		return Pair.of(proxyRequest, Boolean.valueOf(hasContent));
	}

	/**
	 * Async call.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param proxyRequest
	 *            the proxy request
	 * @param hasContent
	 *            the has content
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void asyncCall(HttpServletRequest request, HttpServletResponse response, Request proxyRequest,
			boolean hasContent) throws ServletException, IOException {
		final AsyncContext asyncContext = request.startAsync();
		// timeout the proxy request, not the continuation
		asyncContext.setTimeout(0);
		request.setAttribute(ASYNC_CONTEXT, asyncContext);

		if (hasContent) {
			//System.out.println(SoapParser.extractSoapEnvelope(request));
			// System.out.println(SoapParser.extractSoapEnvelope(proxyRequest));
			proxyRequest.content(proxyRequestContent(proxyRequest, request));
		}

		Request customProxyRequest = customizeProxyRequest(proxyRequest, request);
		customProxyRequest.timeout(getTimeout(), TimeUnit.MILLISECONDS);
		customProxyRequest.send(new ProxyResponseListener(request, response));
	}

	/**
	 * Process error.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param statusCode
	 *            the status code
	 */
	protected void processError(final HttpServletRequest request, final HttpServletResponse response,
			final int statusCode) {
		// extension point for custom error handling
	}

	/**
	 * Redirect to.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param uri
	 *            the uri
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void redirectTo(final HttpServletRequest request, final HttpServletResponse response, final URI uri)
			throws IOException {
		// extension point for redirect to home
	}

	/**
	 * Proxy request content.
	 *
	 * @param proxyRequest
	 *            the proxy request
	 * @param request
	 *            the request
	 * @return the content provider
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected ContentProvider proxyRequestContent(Request proxyRequest, final HttpServletRequest request)
			throws IOException {
		return new InputStreamContentProvider(request.getInputStream()) {
			@Override
			public long getLength() {
				return request.getContentLength();
			}

			@Override
			protected ByteBuffer onRead(byte[] buffer, int offset, int length) {
				log.info((String.format("%d proxying content to upstream: %d bytes", getRequestId(request), length)));
				return super.onRead(buffer, offset, length);
			}
		};
	}

	/**
	 * Gets the modified content.
	 *
	 * @param content
	 *            the content
	 * @return the modified content
	 */
	protected String getModifiedContent(String content) {
		// extension point
		return content;
	}

	/**
	 * On rewrite failed.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void onRewriteFailed(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * Adds the via header.
	 *
	 * @param proxyRequest
	 *            the proxy request
	 * @return the request
	 */
	protected Request addViaHeader(Request proxyRequest) {
		return proxyRequest.header(HttpHeader.VIA, "http/1.1 " + getViaHost());
	}

	/**
	 * Adds the X forwarded headers.
	 *
	 * @param proxyRequest
	 *            the proxy request
	 * @param request
	 *            the request
	 */
	protected void addXForwardedHeaders(Request proxyRequest, HttpServletRequest request) {
		proxyRequest.header(HttpHeader.X_FORWARDED_FOR, request.getRemoteAddr());
		proxyRequest.header(HttpHeader.X_FORWARDED_PROTO, request.getScheme());
		proxyRequest.header(HttpHeader.X_FORWARDED_HOST, request.getHeader(HttpHeader.HOST.asString()));
		proxyRequest.header(HttpHeader.X_FORWARDED_SERVER, request.getLocalName());
	}

	/**
	 * On response headers.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param proxyResponse
	 *            the proxy response
	 */
	protected void onResponseHeaders(HttpServletRequest request, HttpServletResponse response, Response proxyResponse) {
		for (HttpField field : proxyResponse.getHeaders()) {
			String headerName = field.getName();
			String lowerHeaderName = headerName.toLowerCase(Locale.ENGLISH);
			if (HOP_HEADERS.contains(lowerHeaderName)) {
				continue;
			}
			String newHeaderValue = filterResponseHeader(request, headerName, field.getValue());
			if (newHeaderValue == null || newHeaderValue.trim()
					.length() == 0) {
				continue;
			}
			response.addHeader(headerName, newHeaderValue);
		}
		addCustomResponseHeaders(request, response);
	}

	/**
	 * On response content.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param proxyResponse
	 *            the proxy response
	 * @param buffer
	 *            the buffer
	 * @param offset
	 *            the offset
	 * @param length
	 *            the length
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void onResponseContent(HttpServletRequest request, HttpServletResponse response, Response proxyResponse,
			byte[] buffer, int offset, int length) throws IOException {
		response.getOutputStream()
				.write(buffer, offset, length);
		log.fine(String.format("%d proxying content to downstream: %d bytes", getRequestId(request), length));
	}

	/**
	 * On response success.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param proxyResponse
	 *            the proxy response
	 */
	protected void onResponseSuccess(HttpServletRequest request, HttpServletResponse response, Response proxyResponse) {
		AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
		asyncContext.complete();
		log.fine(String.format("%d proxying successful", getRequestId(request)));
	}

	/**
	 * On response failure.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param proxyResponse
	 *            the proxy response
	 * @param failure
	 *            the failure
	 */
	protected void onResponseFailure(HttpServletRequest request, HttpServletResponse response, Response proxyResponse,
			Throwable failure) {
		log.fine(String.format(getRequestId(request) + " proxying failed", failure));
		if (!response.isCommitted()) {
			if (failure instanceof TimeoutException) {
				response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
			}
		}
		AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
		asyncContext.complete();
	}

	/**
	 * Gets the request id.
	 *
	 * @param request
	 *            the request
	 * @return the request id
	 */
	protected int getRequestId(HttpServletRequest request) {
		return System.identityHashCode(request);
	}

	/**
	 * Rewrite URI.
	 *
	 * @param request
	 *            the request
	 * @return the pair
	 */
	protected Pair<URI, Integer> rewriteURI(HttpServletRequest request) {
		StringBuffer uri = request.getRequestURL();
		String query = request.getQueryString();
		if (query != null) {
			uri.append("?")
					.append(query);
		}

		return new Pair<>(URI.create(uri.toString()), 200);
	}

	/**
	 * Extension point for subclasses to customize the proxy request. The default implementation does nothing.
	 *
	 * @param proxyRequest
	 *            the proxy request to customize
	 * @param request
	 *            the request to be proxied
	 * @return the request
	 */
	protected Request customizeProxyRequest(Request proxyRequest, HttpServletRequest request) {
		// by default - return give proxy request
		return proxyRequest;
	}

	/**
	 * Extension point for remote server response header filtering. The default implementation returns the header value
	 * as is. If null is returned, this header won't be forwarded back to the client.
	 *
	 * @param request
	 *            the request to proxy
	 * @param headerName
	 *            the header name
	 * @param headerValue
	 *            the header value
	 * @return filteredHeaderValue the new header value
	 */
	protected String filterResponseHeader(HttpServletRequest request, String headerName, String headerValue) {
		return headerValue;
	}

	/**
	 * Adds the custom response headers.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	protected void addCustomResponseHeaders(HttpServletRequest request, HttpServletResponse response) {
		// extension point
	}

	/**
	 * The Class PriorityQueueHandler.
	 *
	 * @author Raj Gattu
	 */
	public class PriorityQueueHandler {

		/** The remote capacity. */
		private RemoteCapacity remoteCapacity;

		/** The wait queue. */
		private ActivePriorityQueue<PriorityRequest> waitQueue;

		/** The concurrent map containing an executor for each service. */
		private ConcurrentHashMap<ServiceInfo, ExecutorService> execMap;

		/**
		 * Instantiates a new priority queue handler.
		 *
		 * @param remoteCapacity
		 *            the remote capacity
		 */
		// @Inject
		public PriorityQueueHandler(RemoteCapacity remoteCapacity) {
			super();
			this.remoteCapacity = remoteCapacity;
			this.waitQueue = new ActivePriorityQueue<>();
			this.execMap = new ConcurrentHashMap<>();
		}

		public void process(PriorityRequest priorityRequest) {
			waitQueue.offer(priorityRequest);
			activateService(priorityRequest.getService());
		}

		private void activateService(ServiceInfo svcInfo) {
			ThreadPoolExecutor svcExec = (ThreadPoolExecutor) getExecutorService(svcInfo);
			if (svcExec.getActiveCount() < svcExec.getMaximumPoolSize()) {
				int svcCount = 0;
				do {
					svcCount = svcExec.getMaximumPoolSize() - svcExec.getActiveCount();
					Queue<PriorityRequest> activeQueue = waitQueue.drain(svcCount);
					Queue<PriorityRequest> tempQueue = activeQueue;
					while (!tempQueue.isEmpty()) {
						
						svcExec.submit(new Runnable() {
							@Override
							public void run() {
								PriorityRequest pr = activeQueue.poll();
								if (pr != null && pr.getFutureCall().isDone() && !pr.getFutureCall().isCancelled()) {
										pr.process();
								}
							}
						});
						tempQueue.poll();
					}
				} while (waitQueue.size() > 0);
			}
		}

		/**
		 * Gets the executor service.
		 *
		 * @param svcInfo
		 *            the svc info
		 * @return the executor service
		 */
		private synchronized ExecutorService getExecutorService(ServiceInfo svcInfo) {
			if (execMap.containsKey(svcInfo)) {
				return execMap.get(svcInfo);
			}
			ExecutorService exec = Executors.newFixedThreadPool(remoteCapacity.getNodeThreads(svcInfo));
			execMap.put(svcInfo, exec);
			return exec;
		}

		public RemoteCapacity getRemoteCapacity() {
			return remoteCapacity;
		}

	}

	/**
	 * The listener interface for receiving proxyResponse events. The class that is interested in processing a
	 * proxyResponse event implements this interface, and the object created with that class is registered with a
	 * component using the component's <code>addProxyResponseListener<code> method. When the proxyResponse event occurs,
	 * that object's appropriate method is invoked.
	 *
	 * @see ProxyResponseEvent
	 */
	private class ProxyResponseListener extends Response.Listener.Adapter {

		/** The request. */
		private final HttpServletRequest request;

		/** The response. */
		private final HttpServletResponse response;

		/**
		 * Instantiates a new proxy response listener.
		 *
		 * @param request
		 *            the request
		 * @param response
		 *            the response
		 */
		public ProxyResponseListener(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onBegin(org.eclipse.jetty.client.api.Response)
		 */
		@Override
		public void onBegin(Response proxyResponse) {
			response.setStatus(proxyResponse.getStatus());
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onHeaders(org.eclipse.jetty.client.api.Response)
		 */
		@Override
		public void onHeaders(Response proxyResponse) {
			onResponseHeaders(request, response, proxyResponse);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onContent(org.eclipse.jetty.client.api.Response,
		 * java.nio.ByteBuffer)
		 */
		@Override
		public void onContent(Response proxyResponse, ByteBuffer content) {
			// _log.info("in onContent of ProxyResponseListener -- length: " + content.remaining());
			byte[] buffer;
			int offset;
			int length = content.remaining();
			if (content.hasArray()) {
				buffer = content.array();
				offset = content.arrayOffset();
			} else {
				buffer = new byte[length];
				content.get(buffer);
				offset = 0;
			}

			try {
				onResponseContent(request, response, proxyResponse, buffer, offset, length);
			} catch (IOException x) {
				log.log(Level.SEVERE, "!!!!!!!!!!!! About to abort request: "
						+ getRequestId(request)
						+ " >>> "
						+ request.getRequestURI(), x);
				proxyResponse.abort(x);
				AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
				if (asyncContext != null) {
					asyncContext.complete();
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onSuccess(org.eclipse.jetty.client.api.Response)
		 */
		@Override
		public void onSuccess(Response proxyResponse) {
			onResponseSuccess(request, response, proxyResponse);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onFailure(org.eclipse.jetty.client.api.Response,
		 * java.lang.Throwable)
		 */
		@Override
		public void onFailure(Response proxyResponse, Throwable failure) {
			onResponseFailure(request, response, proxyResponse, failure);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jetty.client.api.Response.Listener.Adapter#onComplete(org.eclipse.jetty.client.api.Result)
		 */
		@Override
		public void onComplete(Result result) {
			log.fine(String.format("%d proxying complete", getRequestId(request)));
		}
	}
}
