package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.Map;

/**
 * Initializes the Routing sub system.
 * @author Fred
 *
 */
public class RoutingInitializer {
	/** The logger for this class.*/
	private Logger logger = Logger.getLogger(getClass()); 
	
	/** The only instance of RoutingInitializer*/
	private static RoutingInitializer instance = null;
	
	/**
	 * Initializes the subsystem Routing
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private RoutingInitializer(InputStream inputStream) throws InitializationException {
		try {
			logger.info("Initializing Routing from " + inputStream);
			Document document;
			document = new SAXBuilder().build(inputStream);
			RoutingStrategyImpl.getInstance();
			FacesContext context = FacesContext.getCurrentInstance();
			Element e = document.getRootElement().getChild("RoutingGraph");
			RoutingGraph.initializeGraph(context.getExternalContext().getResourceAsStream(e.getAttributeValue("filename")), Map.getMapByID(Integer.parseInt(e.getAttributeValue("standardMapID"))));
			DijkstraRouting.getInstance();
			logger.info("Routing initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of routing failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of routing failed.", e);
		}
		
	}
	
	/**
	 * Initializes the routing sub system if no such initialization has been attempted before.
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization. 
	 */
	public static void initialize(InputStream inputStream) throws InitializationException {
		if (instance == null) {
			instance = new RoutingInitializer(inputStream);
		}
	}
}
