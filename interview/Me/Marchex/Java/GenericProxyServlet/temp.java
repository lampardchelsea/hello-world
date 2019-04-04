I am building up a server which need to work on both synchronous and asynchronous module, it works as a transit point between two clients A and B, which receive request from A then forward to B, and receive response from B then send back to A. The [Blocking & Non-Blocking API][1] is from `Jetty 9.3.x`.

There are two methods to complete this process, code below:

    public class ProxyServlet extends HttpServlet {
    	/**
    	 * Sync call
    	 * @param request     Request from client A which need to forward to client B
    	 * @param response    Response contains content retreived from resp from client B 
    	 *                    which should send back to client A
    	 * @param newUri      The url point to client B
    	 */
    	public void forwardRequestSyncCall(HttpServletRequest request, HttpServletResponse response, URI newUri) {
    		final Pair<Request, Boolean> requestPair = getProxyRequest(request, newUri);
    		try {
    			// Get response from client B with synchronized call (Blocking API)
    			ContentResponse resp = requestPair.getKey().send();
    			byte[] buffer = resp.getContent();
    			// Create response should send back to client A
    			response.getOutputStream().write(buffer, 0, buffer.length);
    		} catch (Exception e) {
    			// Catch Exception
    		}
    	}
    
    	/**
    	 * Async call: Same parameters as Sych call
    	 */
    	public void forwardRequestAsyncCall(HttpServletRequest request, HttpServletResponse response, URI newUri) {
    		final Pair<Request, Boolean> requestPair = getProxyRequest(request, newUri);
    		final AsyncContext asyncContext = request.startAsync();
    		// Set timeout on asynchronized request
    		asyncContext.setTimeout(10000);
    		request.setAttribute(ASYNC_CONTEXT, asyncContext); 
    		boolean isResponseComplete = false;
    		// Create response listener 
    		ResponseListener listener = new ResponseListener(request, response, isResponseComplete);
    		// Get response from client B with asynchronized call (Non-blocking API)
    		requestPair.getKey().send(listener);
    	}
    
    	private class ResponseListener extends Response.Listener.Adapter {
    		public ResponseListener(HttpServletRequest request, HttpServletResponse response,
    				boolean isResponseComplete) {
    			this.request = request;
    			this.response = response;
    			this.isResponseComplete = isResponseComplete;
    		}
    		
    		// The response which send back to A create in onContent() method
    		@Override
    		public void onContent(Response responseFromB, ByteBuffer content) {
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
    				// Create response should send back to client A
    				response.getOutputStream().write(buffer, offset, length);
    			} catch (IOException e) {
    				responseFromB.abort(e);
    				AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
    				if (asyncContext != null) {
    					asyncContext.complete();
    				}
    			}
    		}
    	}
    }


For asynchronous call, we use Non-Blocking API as `send(listener)` with customized implementation of `listener`, we implement details of  [onContent(Response response, ByteBuffer content)][2] method to handle `Response response` from client B and write it into new `HttpServletResponse response` which send back to A. When we use [write(byte\[\] b, int off, int len)][3] method (line 423 to 544), it works fine.

For synchronous call, we use Blocking API as `send()` waiting for `Response response` from client B and write it into new `HttpServletResponse response` which send back to A. When we use [same write()][3] method it will throw out exception as

    java.lang.IllegalStateException: cannot reset buffer on committed response
    	at org.eclipse.jetty.server.Response.resetBuffer(Response.java:1222)
    	at org.eclipse.jetty.server.HttpChannel.handle(HttpChannel.java:371)
    	at org.eclipse.jetty.server.HttpChannel.run(HttpChannel.java:266)
    	at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:671)
    	at org.eclipse.jetty.util.thread.QueuedThreadPool$2.run(QueuedThreadPool.java:589)
    	at java.lang.Thread.run(Thread.java:745)

The issue is "java.lang.IllegalStateException: **cannot reset buffer on committed response**". The request from client A used for test is exactly same, which suppose to create same response if `write()` method works fine.

Could someone give an idea ? Appreciate any help, :) !!


  [1]: https://www.eclipse.org/jetty/documentation/9.3.x/http-client-api.html
  [2]: http://download.eclipse.org/jetty/stable-9/apidocs/org/eclipse/jetty/client/api/Response.Listener.Adapter.html#onContent-org.eclipse.jetty.client.api.Response-java.nio.ByteBuffer-
  [3]: https://github.com/eclipse/jetty.project/blob/jetty-9.4.x/jetty-server/src/main/java/org/eclipse/jetty/server/HttpOutput.java