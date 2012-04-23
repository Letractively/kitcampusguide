package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;

/**
 * Initializes the POI DB.
 * @author Fred
 *
 */
public class POIDBInitializer {
	/** The only instance of POIDBInitializer.*/
	private static POIDBInitializer instance;
	/** The logger for this class*/
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Initializes the POI DB.
	 * @param filename The absolute path to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private POIDBInitializer(String filename) throws InitializationException {
		try {
			logger.info("Initializing POI database from " + filename);
			Document document;
			document = new SAXBuilder().build(filename);
			initializePOIDB(document);
			logger.info("POI database initialized");
		} catch (JDOMException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (IOException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		}
		
	}
	
	/**
	 * Initializes the POI DB.
	 * @param document The jdom document defining the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializePOIDB(Document document) throws InitializationException {
		try {
			Element e = document.getRootElement().getChild("POIDB");
			String dbURL = e.getAttributeValue("DBURL");
			Class<POIDBSearcher> searcherClass = (Class<POIDBSearcher>) Class.forName(e.getAttributeValue("searcherClass"));
			Constructor<POIDBSearcher> con = searcherClass.getConstructor();
			POIDBSearcher searcher = con.newInstance();
			boolean create = false;
			if (e.getAttributeValue("create").equalsIgnoreCase("true")) {
				create = true;
			}
			Class.forName("org.sqlite.JDBC"); //TODO change, inside POIDB.init
			DefaultPOIDB.init(dbURL, searcher, create);
			logger.info("Created POIDB from " + dbURL + " with searcher " + e.getAttributeValue("searcherClass") + ". Create is " + create);
		} catch (ClassNotFoundException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (SecurityException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (InstantiationException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (SQLException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		}
	}

	/**
	 * Initializes the POI DB.
	 * @param filename The absolute path to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initialize(String filename) throws InitializationException {
		if (instance == null) {
			instance = new POIDBInitializer(filename);
		}
	}
}
