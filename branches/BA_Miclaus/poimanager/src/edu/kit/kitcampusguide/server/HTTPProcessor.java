package edu.kit.kitcampusguide.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.StringTokenizer;

public class HTTPProcessor {
	private static final String HTTP_GET_METHODNAME = "GET";

	private final static String CRLF = "\r\n";

	private static final String HTTP_200_OK = "HTTP/1.0 200 OK";

	private static final String HTTP_400_BAD_REQUEST = "HTTP/1.0 400 Bad Request"
			+ CRLF + CRLF;

	private final static int ERROR = 0;
	private final static int GET = 1;

	public String processInput(String requestMessageLine) {
		String response;
		StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
		switch (determineRequest(tokenizedLine.nextToken())) {
		// if the method is GET then we need to fetch the resource
		case GET:
			response = buildGetResponse(tokenizedLine.nextToken());
			break;
		// if it is not GET it is a bad request
		default:
			response = HTTP_400_BAD_REQUEST;
		} // switch
		return response;
	} // processInput

	private int determineRequest(String method) {
		if (method.equals(HTTP_GET_METHODNAME))
			return GET;
		else
			return ERROR;
	} // determineRequest

	private String buildGetResponse(String resourceName) {
		String resourceData = getResourceData(resourceName);
		String contentType = "";
		if (resourceName.endsWith(".txt"))
			contentType = "Content-Type: text/plain";
		else if (resourceName.endsWith(".html"))
			contentType = "Content-Type: text/html";

		StringBuffer response = new StringBuffer().append(HTTP_200_OK).append(CRLF)
				.append(contentType).append(CRLF)
				.append("Content-Length: ").append(resourceData.length()).append(CRLF)
				.append(CRLF)
				.append(resourceData).append(CRLF);
		return response.toString();
	} // buildGetResponse

	private String getResourceData(String resourceName) {
		if (resourceName.startsWith("/"))
			resourceName = resourceName.substring(1);
		String resourceData = "";
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(resourceName));

			StringWriter writer = new StringWriter();
			String line = null;
			do {
				line = fileReader.readLine();
				if (line == null) break;
				writer.append(line);
			} while (line != null);
			resourceData = writer.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return resourceData;
	} // getResource
}
