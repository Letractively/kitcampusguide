package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.io.IOException;
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
	 * @param filename The absolute path to the configuration file for routing.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private RoutingInitializer(String filename) throws InitializationException {
		try {
			logger.info("Initializing Routing from " + filename);
			Document document;
			document = new SAXBuilder().build(filename);
			DijkstraRouting.getInstance();
			RoutingStrategyImpl.getInstance();
			Element e = document.getRootElement().getChild("RoutingGraph");
			RoutingGraph.initializeGraph(e.getAttributeValue("filename"), Map.getMapByID(Integer.parseInt(e.getAttributeValue("standardMapID"))));
			logger.info("Routing initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of routing failed. " + e.getMessage());
		} catch (IOException e) {
			throw new InitializationException("Initialization of routing failed. " + e.getMessage());
		}
		
	}
	
	/**
	 * Initializes the routing sub system if no such initialization has been attempted before.
	 * @param filename The absolute path to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization. 
	 */
	public static void initialize(String filename) throws InitializationException {
		if (instance == null) {
			instance = new RoutingInitializer(filename);
		}
	}
}
