package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;

public class StandardtypesInitializer {
	private static StandardtypesInitializer instance;
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Initializes the standard types via sub configurators.
	 * @param filename The absolute path to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization. 
	 */
	private StandardtypesInitializer(String filename) throws InitializationException {
		logger.info("Initializing Standardtypes from " + filename);
		Document document;
		try {
			document = new SAXBuilder().build(filename);
			initializeMaps(document.getRootElement().getChild("MapConfiguration").getAttributeValue("filename"));
			initializeBuildings(document.getRootElement().getChild("BuildingConfiguration").getAttributeValue("filename"));
			initializeCategories(document.getRootElement().getChild("CategoryConfiguration").getAttributeValue("filename"));
			logger.info("Standardtypes initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of standardtypes failed.");
		} catch (IOException e) {
			throw new InitializationException("Initialization of standardtypes failed.");
		}
	}
	
	/**
	 * Initializes the categories.
	 * @param filename The absolute path to the file defining the categories.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeCategories(String filename) throws InitializationException {
		try {
			logger.info("Initializing categories from " + filename);
			Document document;
			document = new SAXBuilder().build(filename);
			List<Element> listOfCategories = document.getRootElement().getChildren("category");
			for (Element e : listOfCategories) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				String name = e.getAttributeValue("name");
				new Category(id, name);
			}
			logger.info("Categories initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of categories failed. " + e.getMessage());
		} catch (IOException e) {
			throw new InitializationException("Initialization of categories failed. " + e.getMessage());
		}
		
	}

	/**
	 * Initializes the maps.
	 * @param filename The absolute path to the file defining the maps.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeMaps(String filename) throws InitializationException {	
		try {
			logger.info("Initializing maps from " + filename);
			Document document;
			document = new SAXBuilder().build(filename);
			List<Element> listOfMaps = document.getRootElement().getChildren("map");
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
			throw new InitializationException("Initialization of maps failed. " + e.getMessage());
		} catch (IOException e) {
			throw new InitializationException("Initialization of maps failed. " + e.getMessage());
		}
		
	}

	/**
	 * Initializes the buildings.
	 * The POI DB is required to run.
	 * @param filename The absolute path to the file defining the buildings.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private void initializeBuildings(String filename) throws InitializationException {
		try {
			logger.info("Initializing buildings from " + filename);
			Document document = new SAXBuilder().build(filename);
			List<Element> listOfBuildings = document.getRootElement().getChildren("building");
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
			throw new InitializationException("Initialization of buildings failed. " + e.getMessage());
		} catch (IOException e) {
			throw new InitializationException("Initialization of buildings failed. " + e.getMessage());
		}
	}

	/**
	 * Initializes the maps, buildings and categories.
	 * The POI DB is required to run. 
	 * @param filename The absolute path of the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initialize(String filename) throws InitializationException {
		if (instance == null) {
			instance = new StandardtypesInitializer(filename);
		}
	}
}
