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
	public static void setDefaultValues(InputStream inputStream) throws InitializationException {
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
	
	// TODO: Comments!
	public MapLocator getDefaultMapLocator() {
		return new MapLocator(getDefaultMap().getBoundingBox());
	}
	
	public Collection<POI> getDefaultPOIs() {
		// TODO: Apply map and category filter
		return POISourceImpl.getInstance().getPOIsBySection(getDefaultMapLocator().getMapSection(), null, null);
	}
	
	public POI getDefaultHighlightedPOI() {
		return null;
	}
	
	public Building getDefaultBuilding() {
		return null;
	}
	
	public String getDefaultHighlightedPOIID() {
		return null;
	}
	
	public MapPosition getDefaultMarkerTo() {
		return null;
	}
	
	public MapPosition getDefaultMarkerFrom() {
		return null;
	}
	
	public Route getDefaultRoute() {
		return null;
	}
	
	public POI getDefaultBuildingPOI() {
		return null;
	}
	
	public List<POI> getDefaultBuildingPOIList() {
		return null;
	}
	
	public Integer getDefaultCurrentFloorIndex() {
		return null;
	}
	
}
