package main.java.edu.kit.cm.ivu.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxHandler
 */
@WebServlet("/status")
public class AjaxHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxHandler()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String content = "{ \"userId\": ";
		
		if (request.getSession().getAttribute("userId") != null) {
			content += request.getSession().getAttribute("userId");
			// Log out user after status has been displayed
			request.getSession().removeAttribute("userId");
		} else {
			content += "null";
		}
		
		content += " }";
		
		response.setContentType("application/json");
		response.getWriter().println(content);
	}
}
