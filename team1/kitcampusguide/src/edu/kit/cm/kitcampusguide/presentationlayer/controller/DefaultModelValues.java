package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Initializes and manages the default values.
 * The default values are the ones used when a user opens the website the first time.
 * The default values are:
 * The default map to be shown directly after the start.
 * The default POIs to be shown
 * The default map locator.
 * The default value for highlighted POI.
 * The default value for building.
 * The default value for the marker to.
 * The default value for the marker from.
 * The default value for the route.
 * The default value for the building POI.
 * The default value for the building POI list.
 * The default value for the floor index.
 * 
 * 
 * @author Fred
 *
 */
public class DefaultModelValues {
	/**The default map.*/
	private static Map defaultMap;
	
	/** The categories a user can choose between*/
	private static Collection<Category> defaultCategories;
	
	/**The categories visible to a user as default*/
	private static Collection<Category> defaultCurrentCategories;
	
	/**
	 * Constructs a new DefaultModelValues class. Note that all DefaultModelValues classes are identical in their functions.
	 */
	public DefaultModelValues() {
		
	}
	
	/**
	 * Initializes the default set current categories from the document.
	 * @param document The document specifying the categories.
	 */
	private static void initializeDefaultCurrentCategories(Document document) {
		Collection<Element> defaultCurrentCategoriesElements = (document.getRootElement().getChild("defaultCurrentCategories").getChildren("category"));
		defaultCurrentCategories = new ArrayList<Category>();
		List<Integer> defaultCurrentCategoriesIDs = new ArrayList<Integer>();
		for (Element e : defaultCurrentCategoriesElements) {
			defaultCurrentCategoriesIDs.add(Integer.parseInt(e.getAttributeValue("id")));
		}
		defaultCurrentCategories.addAll(Category.getCategoriesByIDs(defaultCurrentCategoriesIDs));
	}

	/**
	 * Initializes the default set categories from the document.
	 * @param document The document to initialize the categories from.
	 */
	private static void initializeDefaultCategories(Document document) {
		Collection<Element> defaultCategoriesElements = (document.getRootElement().getChild("defaultCategories").getChildren("category"));
		defaultCategories = new ArrayList<Category>();
		List<Integer> defaultCategoriesIDs = new ArrayList<Integer>();
		for (Element e : defaultCategoriesElements) {
			defaultCategoriesIDs.add(Integer.parseInt(e.getAttributeValue("id")));
		}
		defaultCategories.addAll(Category.getCategoriesByIDs(defaultCategoriesIDs));
	}

	/**
	 * Returns the default map for the view.
	 * @return The default map for the view.
	 */
	public Map getDefaultMap() {
		return defaultMap;
	}
	
	/**
	 * Returns the default map locator.
	 * @return The default map locator.
	 */
	public MapLocator getDefaultMapLocator() {
		return new MapLocator(getDefaultMap().getBoundingBox());
	}
	
	/**
	 * Returns the default POIs to be shown.
	 * @return The default POIs to be shown.
	 */
	public Collection<POI> getDefaultPOIs() {
		// TODO: Apply map and category filter, maybe use ControllerUtil
		return POISourceImpl.getInstance().getPOIsBySection(
				getDefaultMapLocator().getMapSection(), getDefaultMap(), null);
	}
	
	/**
	 * Returns the default highlighted POI.
	 * @return The default highlighted POI.
	 */
	public POI getDefaultHighlightedPOI() {
		return null;
	}
	
	/**
	 * Returns the default building.
	 * @return The default building.
	 */
	public Building getDefaultBuilding() {
		return null;
	}
	
	/**
	 * Returns the default marker to.
	 * @return The default marker to.
	 */
	public MapPosition getDefaultMarkerTo() {
		return null;
	}
	
	/**
	 * Returns the default marker from.
	 * @return The default marker from.
	 */
	public MapPosition getDefaultMarkerFrom() {
		return null;
	}
	
	/**
	 * Returns the default route.
	 * @return The default route.
	 */
	public Route getDefaultRoute() {
		return null;
	}
	
	/**
	 * Returns the default building POI.
	 * @return The default building POI.
	 */
	public POI getDefaultBuildingPOI() {
		return null;
	}
	
	/**
	 * Returns the default building POI list.
	 * @return The default building POI list.
	 */
	public List<POI> getDefaultBuildingPOIList() {
		return null;
	}
	
	/**
	 * Returns the default current floor index.
	 * @return The default current floor index.
	 */
	public Integer getDefaultCurrentFloorIndex() {
		return null;
	}
	
	/**
	 * Returns the default current categories.
	 * @return The default current categories.
	 */
	public Collection<Category> getDefaultCurrentCategories() {		
		return Collections.unmodifiableCollection(defaultCurrentCategories);		
	}
	
	/**
	 * Returns the default categories.
	 * @return The default categories.
	 */
	public Collection<Category> getDefaultCategories() {
		return Collections.unmodifiableCollection(defaultCategories);	
	}
	/**
	 * Sets the default values of all DefaultModelValues classes to those defined in the document given by inputStream.
	 * @param inputStream The xml-document defining the default values.
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void initialize(InputStream inputStream) throws InitializationException {
		try {
			Document document;
			document = new SAXBuilder().build(inputStream);
			defaultMap = Map.getMapByID(Integer.parseInt(document.getRootElement().getChild("defaultMap").getAttributeValue("ID")));
			initializeDefaultCategories(document);
			initializeDefaultCurrentCategories(document);
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		}
		
	}
}
