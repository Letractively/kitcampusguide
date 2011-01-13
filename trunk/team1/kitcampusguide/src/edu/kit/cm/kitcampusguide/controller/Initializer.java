package edu.kit.cm.kitcampusguide.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingInitializer;
import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.StandardtypesInitializer;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationInitializer;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBInitializer;

/**
 * Class to initialize all necessary classes and data structures.
 * @author Fred
 *
 */
public class Initializer {
	/** The logger for this class.*/
	Logger logger = Logger.getRootLogger();
	
	/** Private constructor*/
	private Initializer() {
	}

	/**
	 * Initializes the classes and data structures necessary to run this program. Delegates these.
	 * @param args May have only one argument, the path to the configuration file for the program.
	 */
	public static void main(String[] args) {
		Initializer init = new Initializer();
		init.configure(args);
	}

	/**
	 * Constructs the document the sub configurators will be configured after. Uses therefore <code>configureSubconfigurators</code>.
	 * If a exception occurs, the program ends.
	 * @param args May have only one argument, the absolute path to the configuration file for the program.
	 */
	private void configure(String[] args) {
		BasicConfigurator.configure();
		try {
			if (args.length != 1) {
				logger.fatal("Wrong count for arguments required to initialize the program. (" + args.length + ")");
			} else {
				logger.info("Beginning initialization from " + args[0]);
				Document document = new SAXBuilder().build(args[0]);
				configureSubconfigurators(document);
				logger.info("Initialization succeeded");
			}
		} catch(Exception e) {
			logger.fatal("Initialization failed. Program ends.");
			System.exit(-1);
		}
		
	}

	/**
	 * Configures the program by calling all sub configurators with paths to their configuration files.
	 * Initializes the POI database, the standardtypes (map, building, categories), the logger, the routing and the translation.
	 * @param document The jdom document representing the configuration file.
	 * 
	 * @throws InitializationException If an error occured during initialization.
	 */
	private void configureSubconfigurators(Document document) throws InitializationException {
		Element r = document.getRootElement();
		PropertyConfigurator.configure(r.getChild("loggerConfiguration").getAttributeValue("filename"));
		POIDBInitializer.initialize(r.getChild("POIDBConfiguration").getAttributeValue("filename"));
		StandardtypesInitializer.initialize(r.getChild("standardtypesConfiguration").getAttributeValue("filename"));
		RoutingInitializer.initialize(r.getChild("routeConfiguration").getAttributeValue("filename"));
		TranslationInitializer.initialize(r.getChild("translationConfiguration").getAttributeValue("filename"));
	}
	
}
