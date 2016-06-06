package org.eclipse.jetty.embedded;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * To embed a Jetty server the following steps are typical and are illustrated
 * by the examples in this tutorial:
 * <ol>
 * <li>Create a Server instance.</li>
 * <li>Add/Configure Connectors.</li>
 * <li>Add/Configure Handlers and/or Contexts and/or Servlets.</li>
 * <li>Start the Server.</li>
 * <li>Wait on the server or do something else with your thread.</li>
 * </ol>
 * 
 * @author yang.dongdong
 *
 */
public class HelloWorld extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>Hello World</h1>");
		baseRequest.setHandled(true);
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new HelloWorld());
		server.start();
		server.join();
	}
}