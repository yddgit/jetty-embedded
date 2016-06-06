package org.eclipse.jetty.embedded;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class StopHandler extends AbstractHandler {

	private Server server = null;
	public Boolean restartPlease = false;

	public StopHandler(Server server) {
		this.server = server;
	}

	private boolean stopServer(HttpServletResponse response) throws IOException {
		System.out.println("Stopping Jetty");
		response.setStatus(202);
		response.setContentType("text/plain");
		ServletOutputStream os = response.getOutputStream();
		os.println("Shutting down...");
		os.close();
		response.flushBuffer();
		try {
			// Stop the server.
			new Thread() {
				@Override
				public void run() {
					try {
						System.out.println("Shutting down Jetty...");
						server.setStopAtShutdown(true);
						server.stop();
						System.out.println("Jetty has stopped.");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String pathInfo = request.getPathInfo();

		// THIS SHOULD OBVIOUSLY BE SECURED!!!
		if ("/stop".equals(pathInfo)) {
			stopServer(response);
			return;
		}

		if ("/restart".equals(pathInfo)) {
			restartPlease = true;
			stopServer(response);
			return;
		}

		// Go off and do the rest of your RESTful calls...
		// And close off how you please
		response.sendRedirect("http://nowhere.com");
		
	}
}