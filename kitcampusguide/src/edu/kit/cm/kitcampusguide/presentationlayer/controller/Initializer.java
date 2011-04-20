package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingInitializer;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBInitializer;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationInitializer;
import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.StandardtypesInitializer;

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
	 * @param stream The input stream to the configuration file.
	 */
	public static void main(InputStream stream) {
		Initializer init = new Initializer();
		init.configure(stream);
	}

	/**
	 * Constructs the document the sub configurators will be configured after. Uses therefore <code>configureSubconfigurators</code>.
	 * If a exception occurs, the program ends.
	 * @param stream The input stream to the configuration file.
	 */
	private void configure(InputStream stream) {
		BasicConfigurator.configure();
		try {
				logger.info("Beginning initialization");		
				Document document = new SAXBuilder().build(stream);
				configureSubconfigurators(document);
				logger.info("Initialization succeeded");
		} catch(Exception e) {
			logger.fatal("Initialization failed. Program ends.", e);
			
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
		FacesContext context = FacesContext.getCurrentInstance();
		StandardtypesInitializer.initialize(context.getExternalContext().getResourceAsStream(r.getChild("standardtypesConfiguration").getAttributeValue("filename")));
		StandardtypesInitializer.initializeMaps();
		StandardtypesInitializer.initializeCategories();
		POIDBInitializer.initialize(context.getExternalContext().getResourceAsStream(r.getChild("POIDBConfiguration").getAttributeValue("filename")));
		StandardtypesInitializer.initializeBuildings();
		RoutingInitializer.initialize(context.getExternalContext().getResourceAsStream(r.getChild("routeConfiguration").getAttributeValue("filename")));
		TranslationInitializer.initialize(context.getExternalContext().getResourceAsStream(r.getChild("translationConfiguration").getAttributeValue("filename")));
		DefaultModelValues.initialize(context.getExternalContext().getResourceAsStream(r.getChild("defaultValueConfiguration").getAttributeValue("filename")));
	}
}
