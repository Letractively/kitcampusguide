package edu.kit.kitcampusguide.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebClient {

	public static void main(String[] args) throws Exception {
		String request;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		Socket clientSocket = new Socket("localhost", 8000);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		request = inFromUser.readLine();
		outToServer.writeBytes(request + "\r\n");
		System.out.println("Antwort vom Server: ");
		String reply = "";
		do {
			reply = inFromServer.readLine();
			if (reply == null) break;
			System.out.println(reply);
		} while (reply != null);
			
		clientSocket.close();
	}
}