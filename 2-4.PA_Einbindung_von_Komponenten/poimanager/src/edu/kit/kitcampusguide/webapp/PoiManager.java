package edu.kit.kitcampusguide.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
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
		String reqPoiCategory = (String) request.getParameter("category");
		// get the resource location
		String poiListLocation = getServletContext().getRealPath(RESOURCE_PATH);
		// retrieve the list of Pois from the XmlPoiManager
		XmlPoiManager poiMan = new XmlPoiManager();
		List<POI> matchingPois = null;
		if (reqPoiCategory != null && poiListLocation != null) {
			matchingPois = poiMan.getPoisByCategory(reqPoiCategory,
					poiListLocation);
		}

		PrintWriter wr = response.getWriter();
		wr.print(generateHtmlResponse(matchingPois));
	}


	private String generateHtmlResponse(List<POI> matchingPois) {
		return new StringWriter().append(generateHtmlHeader())
				.append(generatePoiListHtml(matchingPois))
				.append(generateHtmlFooter()).toString();
	}

	private String generatePoiListHtml(List<POI> pois) {
		StringWriter w = new StringWriter();
		if (pois == null || pois.isEmpty()) {
			w.append("Zu der Kategorie wurden keine Points of Interest gefunden.");
		} else {
			for (POI p : pois)
				w.append(generatePoiInfoHtml(p));
		}
		return w.toString();
	}

	private CharSequence generateHtmlFooter() {
		return new StringWriter().append("<br />")
		// insert a back button with a javascript command on the on mouse click
		// event
		// return to index.html works directly because in root folder?
				.append("<button onClick=\"window.location='index.html'\">Back home!</button>")
				// standard end of html document
				.append("</body>").append("</html>").toString();

	}

	private String generatePoiInfoHtml(POI p) {
		return new StringWriter().append("<br />").append("Name is: ")
				.append(p.getName()).append("<br />").append("ID is: ")
				.append(String.valueOf(p.getId())).append("<br />")
				.append("<br />").toString();
	}

	private String generateHtmlHeader() {
		// html 5 doctype
		return new StringWriter()
				.append("<!DOCTYPE html>")
				// start of html document
				.append("<html>")
				.append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">")
				// title tag of the html document
				.append("<title>KCG POI Management</title>").append("</head>")
				// start of the body tag where our data goes
				.append("<body>").toString();
	}

}
