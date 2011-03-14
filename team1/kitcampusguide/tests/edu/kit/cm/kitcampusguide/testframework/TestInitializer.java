package edu.kit.cm.kitcampusguide.testframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingInitializer;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBInitializer;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.DefaultModelValues;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.Initializer;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationInitializer;
import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.StandardtypesInitializer;

public class TestInitializer {

	/** The logger for this class.*/
	Logger logger = Logger.getRootLogger();
	
	/** Private constructor*/
	private TestInitializer() {
	}

	/**
	 * Initializes the classes and data structures necessary to run this program. Delegates these.
	 * @param stream The input stream to the configuration file.
	 */
	public static void main(InputStream stream) {
		TestInitializer init = new TestInitializer();
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
	 * @throws FileNotFoundException 
	 */
	private void configureSubconfigurators(Document document) throws InitializationException, FileNotFoundException {
//		Element r = document.getRootElement();
//		FacesContext context = FacesContext.getCurrentInstance();
		TestStandardtypesInitializer.initialize(new FileInputStream(new File("WebContent/resources/config/StandardtypesConfiguration.xml")));
		TestStandardtypesInitializer.initializeMaps();
		TestStandardtypesInitializer.initializeCategories();
		POIDBInitializer.initialize(new FileInputStream(new File("WebContent/resources/config/POIDBConfiguration.xml")));
		TestStandardtypesInitializer.initializeBuildings();
		//RoutingInitializer.initialize(new FileInputStream(new File("WebContent/resources/config/RoutingConfiguration.xml")));
		//TranslationInitializer.initialize(new FileInputStream(new File("WebContent/resources/config/TranslationConfiguration.xml")));
		DefaultModelValues.initialize(new FileInputStream(new File("WebContent/resources/config/DefaultValues.xml")));
	}
	
}
