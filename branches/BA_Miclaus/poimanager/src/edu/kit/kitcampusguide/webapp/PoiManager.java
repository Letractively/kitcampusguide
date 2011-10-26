package edu.kit.kitcampusguide.webapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.kit.kitcampusguide.poi.XmlPoiManager;
import edu.kit.kitcampusguide.poi.model.POI;

/**
 * The POIManager servlet provides a list of POIs matching the category the user
 * has entered in a web form that calls this servlet.
 * 
 * @author Andrei Miclaus
 * 
 *         The Annotation is used to declare the URL where the servlet can be
 *         found. This replaces the declaration in the web.xml descriptor file
 */
@WebServlet(description = "Provides a list of POIs to a matching category", urlPatterns = { "/search" })
public class PoiManager extends HttpServlet {

	private static final String CATEGORY_PARAM_NAME = "category";
	private static final String RESULT_ATTR = "results";
	private static final String RESULT_PAGE = "results.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String RESOURCE_PATH = "/WEB-INF/data/POIList.xml";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PoiManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handles a post request with the POI category as parameter and writes the
	 * data of the POIs found matching that category as HTML to the response
	 * object. <br />
	 * {@code poiCategory }, the category of the POI to be searched, is passed
	 * as HTTP request parameter.
	 * 
	 * @param request
	 *            an HttpServletRequest object that contains the request the
	 *            client has made of the servlet
	 * @param response
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get the category for which to show POIs
		String reqPoiCategory = (String) request
				.getParameter(CATEGORY_PARAM_NAME);
		// get the resource location
		String poiListLocation = getServletContext().getRealPath(RESOURCE_PATH);
		// retrieve the list of Pois from the XmlPoiManager
		XmlPoiManager poiMan = new XmlPoiManager();
		List<POI> matchingPois = null;
		if (reqPoiCategory != null && poiListLocation != null) {
			matchingPois = poiMan.getPoisByCategory(reqPoiCategory,
					poiListLocation);
		}

		if (matchingPois == null) {
			request.getSession().setAttribute("errorMsg", "Undefined");
			response.sendRedirect(ERROR_PAGE);
		} else {
			// save the result list to the session object
			request.getSession().setAttribute(RESULT_ATTR, matchingPois);
			response.sendRedirect(RESULT_PAGE);
		}
	}

}
