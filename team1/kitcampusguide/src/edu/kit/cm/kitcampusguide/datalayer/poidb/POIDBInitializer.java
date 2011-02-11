package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.apache.lucene.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;

/**
 * Initializes the POI DB.
 * @author Fred
 *
 */
public class POIDBInitializer {
	/** The only instance of POIDBInitializer.*/
	private static POIDBInitializer instance;
	/** The searcher to be used.*/
	private static POIDBSearcher searcher;
	/** The logger for this class*/
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Initializes the POI DB.
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private POIDBInitializer(InputStream inputStream) throws InitializationException {
		try {
			logger.info("Initializing POI database from " + inputStream);
			Document document;
			document = new SAXBuilder().build(inputStream);
			getSearcherClass(document);
			initializePOIDB(document);
			initializeSearcher();
			logger.info("POI database initialized");
		} catch (JDOMException e) {
			throw new InitializationException("POI DB initialization failed.", e);
		} catch (IOException e) {
			throw new InitializationException("POI DB initialization failed.", e);
		}
		
	}
	
	private void initializeSearcher() {
		
	}

	/**
	 * Reads out and initializes the searcher class from the document defining it.
	 * @param document The document defining the searcher class
	 * @throws InitializationException In case any error occurs.
	 */
	private void getSearcherClass(Document document) throws InitializationException {
		
		try {
			Element e = document.getRootElement().getChild("POIDB");
			Class<POIDBSearcher> searcherClass;
			searcherClass = (Class<POIDBSearcher>) Class.forName(e.getAttributeValue("searcherClass"));
			Constructor<POIDBSearcher> con = searcherClass.getConstructor();
			searcher = con.newInstance();
		} catch (ClassNotFoundException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (SecurityException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (InstantiationException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new InitializationException("POI DB searcher initialization failed. " + e.getMessage());
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
		} catch (IllegalArgumentException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		} catch (SQLException e) {
			throw new InitializationException("POI DB initialization failed. " + e.getMessage());
		}
	}

	/**
	 * Initializes the POI DB. If such an initialization has already been done or no initialization of the file paths has been attempted before,
	 * the maps will not be initialized.
	 * It is required for the maps and categories to be initialized before this initialization is called.
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initialize(InputStream inputStream) throws InitializationException {
		if (instance == null) {
			instance = new POIDBInitializer(inputStream);
		}
	}
}
