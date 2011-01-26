package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;

/**
 * Initializes the standardtypes in need of initialization. More specific: buildings, maps and categories. 
 * @author fred
 *
 */
public class StandardtypesInitializer {
	private static StandardtypesInitializer instance;
	private Logger logger = Logger.getLogger(getClass());
	private InputStream filenameMaps;
	private InputStream filenameCategories;
	private InputStream filenameBuildings;
	
	/**
	 * Configures the sub configurators for the standardtypes.
	 * @param stream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization. 
	 */
	private StandardtypesInitializer(InputStream stream) throws InitializationException {
		try {
			logger.info("Initializing Standardtypes from " + stream);
			Document document = new SAXBuilder().build(stream);
			FacesContext context = FacesContext.getCurrentInstance();
			Element root = document.getRootElement();
			filenameMaps = context.getExternalContext().getResourceAsStream(root.getChild("MapConfiguration").getAttributeValue("filename"));
			filenameBuildings = context.getExternalContext().getResourceAsStream(root.getChild("BuildingConfiguration").getAttributeValue("filename"));
			filenameCategories = context.getExternalContext().getResourceAsStream(root.getChild("CategoryConfiguration").getAttributeValue("filename"));
			logger.info("Standardtypes initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of standardtypes failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of standardtypes failed.", e);
		}
	}
	
	/**
	 * Initializes the Maps.
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initializeMaps() throws InitializationException {
		if (instance.filenameMaps != null) {
			instance.initializeMaps(instance.filenameMaps);
			instance.filenameMaps = null;
		}
		
	}
	
	/**
	 * Initializes the Categories.
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initializeCategories() throws InitializationException {
		if (instance.filenameCategories != null) {
			instance.initializeCategories(instance.filenameCategories);
			instance.filenameCategories = null;
		}
	}
	
	/**
	 * Initializes the Buildings.
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initializeBuildings() throws InitializationException {
		if (instance.filenameBuildings != null) {
			instance.initializeBuildings(instance.filenameBuildings);
			instance.filenameBuildings = null;
		}
	}
	
	/**
	 * Initializes the categories.
	 * @param streamCategories The input stream to the categories file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeCategories(InputStream streamCategories) throws InitializationException {
		try {
			logger.info("Initializing categories from " + streamCategories);
			List<Element> listOfCategories = new SAXBuilder().build(streamCategories).getRootElement().getChildren("category");
			for (Element e : listOfCategories) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				String name = e.getAttributeValue("name");
				new Category(id, name);
			}
			logger.info("Categories initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of categories failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of categories failed.", e);
		}
		
	}

	/**
	 * Initializes the maps.
	 * @param streamMaps The input stream to the configuration file defining the maps.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeMaps(InputStream streamMaps) throws InitializationException {	
		try {
			logger.info("Initializing maps from " + streamMaps);
			List<Element> listOfMaps = new SAXBuilder().build(streamMaps).getRootElement().getChildren("map");
			for (Element e : listOfMaps) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				String name = e.getAttributeValue("name");
				WorldPosition NW = new WorldPosition(Double.parseDouble(e.getAttributeValue("boundingNorth")), Double.parseDouble(e.getAttributeValue("boundingWest")));
				WorldPosition SE = new WorldPosition(Double.parseDouble(e.getAttributeValue("boundingSouth")), Double.parseDouble(e.getAttributeValue("boundingEast")));
				MapSection boundingBox = new MapSection(NW, SE);
				String mapFilename = e.getAttributeValue("filename");
				int minZoom = Integer.parseInt(e.getAttributeValue("minZoom"));
				int maxZoom = Integer.parseInt(e.getAttributeValue("maxZoom"));
				new Map(id, name, boundingBox, mapFilename, minZoom, maxZoom);
			}
			logger.info("Maps initialized");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of maps failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of maps failed.", e);
		}
		
	}

	/**
	 * Initializes the buildings.
	 * The POI DB is required to run.
	 * @param streamBuildings The input stream to the configuration file defining the buildings.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeBuildings(InputStream streamBuildings) throws InitializationException {
		try {
			logger.info("Initializing buildings from " + streamBuildings);
			List<Element> listOfBuildings = new SAXBuilder().build(streamBuildings).getRootElement().getChildren("building");
			for (Element e : listOfBuildings) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				int groundfloorIndex = Integer.parseInt(e.getAttributeValue("groundfloorIndex"));
				POI buildingPOI = POISourceImpl.getInstance().getPOIByID(e.getAttributeValue("BuildingPOIID"));
				List<Map> floors = new ArrayList<Map>();
				List<Element> mapElements= e.getChildren("map");
				for (Element mapElement : mapElements) {
					floors.add(Map.getMapByID(Integer.parseInt(mapElement.getAttributeValue("id"))));
				}
				new Building(id, floors, groundfloorIndex, buildingPOI);
			}
			logger.info("Buildings initialized");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of buildings failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of buildings failed.", e);
		}
	}

	/**
	 * Initializes the maps, buildings and categories.
	 * The POI DB is required to run. 
	 * @param stream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initialize(InputStream stream) throws InitializationException {
		if (instance == null) {
			instance = new StandardtypesInitializer(stream);
		}
	}
}
