package edu.kit.kitcampusguide.poi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * An implementation for a Socket-based TCP Server. Listens for incoming strings
 * and modifies them to uppercase. A client for this server is TCPClient.
 * 
 * @author Andrei Miclaus
 * 
 */
class TCPServer extends AbstractServer {

	private static final String EXCEPTION_MESSAGE = "Request was not understood. Server only accepts strings.";
	private static final int SERVER_PORT = 8000;
	private static final char CR = '\n';
	
	@Override
	protected int getServerPort() {
		return SERVER_PORT;
	}

	/**
	 * Reads a line of text from the client request.
	 */
	@Override
	protected Object getRequest() throws IOException {
		return new BufferedReader(new InputStreamReader(getConnectionSocket()
				.getInputStream())).readLine();
	}

	/**
	 * Modify incoming string to uppercase.
	 */
	@Override
	protected Object processRequest(Object request) {
		String result = null;
		if (request instanceof String) {
			result = ((String) request).toUpperCase() + CR;
		}
		return result;
	}

	/**
	 * Sends the modified data back to the client. 
	 */
	@Override
	protected void respondToClient(Object response) throws IOException {
		Writer w = new OutputStreamWriter(
				getConnectionSocket().getOutputStream());
		if (response != null && response instanceof String) {
			w.write((String) response);
		} else {
			w.write(EXCEPTION_MESSAGE);
		}
		w.flush();
		w.close();
	}

	/**
	 * Start routine of the server
	 * @param args
	 */
	public static void main(String args[]) {
		Server server = new TCPServer();
		server.start();
	}

}
