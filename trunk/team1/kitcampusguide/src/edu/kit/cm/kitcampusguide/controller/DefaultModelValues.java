package edu.kit.cm.kitcampusguide.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Initializes and manages the default values.
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
	
	/**
	 * Constructs a new DefaultModelValues class. Note that all DefaultModelValues classes are identical in their functions.
	 */
	public DefaultModelValues() {
		
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
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		}
		
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
		// TODO: Apply map and category filter
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
}
