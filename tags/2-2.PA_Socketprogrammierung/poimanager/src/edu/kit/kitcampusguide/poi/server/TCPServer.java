package edu.kit.kitcampusguide.poi.server;

import java.io.*;
import java.net.*;

/**
 * An example Server implemented with Java Sockets. Listens for incoming strings
 * and modifies them to uppercase. A client for this server is TCPClient.
 * 
 * @author Andrei Miclaus
 * 
 */
class TCPServer {

	public static void main(String argv[]) throws Exception {

		String clientSentence;
		String capitalizedSentence;
		// create a socket to listen for and accept incoming client requests
		ServerSocket welcomeSocket = new ServerSocket(8000);
		while (true) {
			// accept an incoming client request by opening a new socket
			Socket connectionSocket = welcomeSocket.accept();
			// Obtain the connecting input and output streams
			// between client and server
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			// retrieve the data, send by the client
			clientSentence = inFromClient.readLine();
			// process data: modify incoming string to uppercase
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			// send process result back to client
			outToClient.writeBytes(capitalizedSentence);
			// close the connection to the client
			connectionSocket.close();
		}
	}
}
