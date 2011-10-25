package edu.kit.kitcampusguide.poi.server;

public class WebServer extends TCPServer {

	private final static String CRLF = "\r\n";

	private static final String HTTP_400_BAD_REQUEST = "HTTP/1.0 400 Bad Request"
			+ CRLF + CRLF;

	@Override
	protected Object processRequest(Object request) {
		String response = "";
		
		if (request != null && request instanceof String) {
			HTTPProcessor http = new HTTPProcessor();
			response = http.processInput((String) request);
		} else {
			response = HTTP_400_BAD_REQUEST;
		}
		return response;
	}

	public static void main (String[] args){
		Server webServer = new WebServer();
		webServer.start();
	}
} // WebServer
