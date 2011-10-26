package edu.kit.kitcampusguide.webapp;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The FirstServlet is an example servlet created to familiarize students with
 * Java servlets. The FirstServlet returns a basic HTML page with a title and
 * the current date obtained from the java.util.Date class.
 * 
 * @author Andrei Miclaus
 * 
 *         The Annotation is used to declare the URL where the servlet can be
 *         found. This replaces the declaration in the web.xml descriptor file
 */
@WebServlet(description = "A test servlet to get started with servlet programming", urlPatterns = { "/first" })
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FirstServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		java.util.Date today = new java.util.Date();
		response.getWriter().write(new StringWriter().append("<!DOCTYPE HTML>").append("<html>")
				.append("<body>")
				.append("<h1 align=center>My First Servlet</h1>")
				.append("<br>").append(today.toString()).append("</body>")
				.append("</html>").toString());
	}

}
