package edu.kit.kitcampusguide.poi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A generic Java Sockets-based server.
 * 
 * @author Andrei Miclaus
 *
 */
public abstract class AbstractServer implements Server {

	private Socket connectionSocket;

	public AbstractServer() {
		super();
	}

	protected Socket getConnectionSocket() {
		return connectionSocket;
	}

	protected void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	protected abstract Object processRequest(Object request);

	protected abstract Object getRequest() throws IOException;

	protected abstract void respondToClient(Object response) throws IOException;

	protected abstract int getServerPort();

	/* (non-Javadoc)
	 * @see edu.kit.kitcampusguide.poi.server.Server#start()
	 */
	@Override
	public void start() {
		// create a socket to listen for and accept incoming client requests
		try {
			ServerSocket welcomeSocket = new ServerSocket(getServerPort());
			while (true) {
				// accept an incoming client request by opening a new socket
				setConnectionSocket(welcomeSocket.accept());
				// retrieve the data, send by the client
				Object request = getRequest();
				// process data
				Object response = processRequest(request);
				// send process result back to client
				respondToClient(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close the connection to the client
			try {
				getConnectionSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}