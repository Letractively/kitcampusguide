package main.java.edu.kit.cm.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.kit.cm.auth.LoginHandler;

/**
 * Servlet implementation class QrLoginHandler
 */
public class ClassicLoginHandler extends LoginHandler {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("username", "Classic");
		request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
	}
}
