package edu.kit.kitcampusguide.client;

import java.io.*;
import java.net.*;

/**
 * A client application for sending strings captured at the command prompt to a
 * server. To be used in conjunction with TCPServer.
 * 
 * @author Andrei Miclaus
 * 
 */
class TCPClient {
	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;
		// Get the data to be processed be the server:
		// read the sentence to be send to the server from the command prompt
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		// Open a socket to connect to the server
		Socket clientSocket = new Socket("localhost", 8000);
		// Obtain the connecting input and output streams
		// between client and server
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		sentence = inFromUser.readLine();
		// send the message to the server using the output stream to the server
		outToServer.writeBytes(sentence + '\n');
		// retrieve the server response using the input stream from the server
		modifiedSentence = inFromServer.readLine();
		// process server response: print sentence to commandline
		System.out.println("Antwort vom Server: " + modifiedSentence);
		// close the socket
		clientSocket.close();
	}
}
