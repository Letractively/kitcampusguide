package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapPhaseListener;
import edu.kit.cm.kitcampusguide.presentationlayer.view.converters.MapModelConverter;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * This class manages the properties of the shown map. Only properties
 * directly related to the map are stored. Additionally, the <code>MapModel</code>
 * offers a mechanism to obtain which properties have changed since the last request
 * and which not. This allows the converters only to convert data which has really changed,
 * see {@link MapModelConverter}.
 * @author Fred
 *
 */
public class MapModel implements Serializable {

	private static Logger logger = Logger.getLogger(MapModel.class);
	
	/** The POIs currently shown. */
	private Collection<POI> pois;
	
	/**
	 * The building the currently displayed map is a floor of or
	 * <code>null</code>.
	 */
	private Building building;
	
	/**
	 * The index of the current floor in the current building or
	 * <code>null</code>.
	 */
	private Integer currentFloorIndex;
	
	/** The currently displayed map section. */
	private MapLocator mapLocator;
	
	/** The POI currently highlighted or <code>null</code>. */
	private POI highlightedPOI;
	
	/** The current starting position for a route or <code>null</code>. */
	private MapPosition markerFrom;
	
	/** The current end position for a route or <code>null</code>. */
	private MapPosition markerTo;
	
	/** The currently displayed route or <code>null</code>. */
	private Route route;
	
	/** The map currently displayed. */
	private Map map;

	/**
	 * The building POI for which a list of POIs inside the building should be
	 * displayed or <code>null</code>. If this property is <code>null</code>,
	 * <code>buildingPOIList</code> will be <code>null</code> as well.
	 * Furthermore, if the building POI is set, it is always equal to the
	 * highlighted POI.
	 * 
	 * @see #getBuildingPOIList()
	 */
	private POI buildingPOI;

	/**
	 * The list of POIs in the building for which the building POI is currently
	 * displayed or <code>null</code>. If this property is <code>null</code>,
	 * <code>buildingPOI</code> will be <code>null</code> as well.
	 * @see #getBuildingPOI()
	 */
	private List<POI> buildingPOIList;
	

	/**
	 * Stores all changed properties. If the current request is not a partial request, all
	 * properties are added automatically. Otherwise the set will be emptied before the 
	 * execute application logic phase (see {@link MapPhaseListener})
	 */
	private Set<MapProperty> changedProperties = new HashSet<MapModel.MapProperty>();

	/**
	 * Returns the map to display.
	 * 
	 * @return The map to display.
	 */
	public Map getMap() {
		return this.map;
	}

	/**
	 * Sets the map to be displayed.
	 * 
	 * @param map
	 *            The map to be displayed. If <code>null</code>, no change is
	 *            made.
	 * @throws NullPointerException
	 *             if <code>map</code> is <code>null</code>
	 */
	public void setMap(Map map) {
		if (map == null) {
			throw new NullPointerException();
		}
		if (this.map == null || !this.map.equals(map)) {
			if (changedProperties.add(MapProperty.map)) {
				logger.trace("map changed");
			}
			this.map = map;
		}
	}

	/**
	 * Returns the POIs to be displayed.
	 * 
	 * @return The POIs to be displayed.
	 */
	public Collection<POI> getPOIs() {
		return this.pois;
	}

	/**
	 * Sets the POIs to be displayed.
	 * 
	 * @param pois
	 *            The POIs to be displayed. If <code>null</code>, no change is
	 *            made.
	 * @throws NullPointerException
	 *             if <code>pois</code> is <code>null</code>
	 */
	public void setPOIs(Collection<POI> pois) {
		if (pois == null) {
			throw new NullPointerException();
		}
		this.pois = pois;
		if (changedProperties.add(MapProperty.POIs)) {
			logger.trace("pois changed");
		}
	}

	/**
	 * Returns the building currently displayed.
	 * 
	 * @return The building currently displayed. Can be <code>null</code>.
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Sets the currently displayed building.
	 * 
	 * @param building
	 *            The building to display. If no building should be shown
	 *            anymore, <code>null</code> can be passed.
	 */
	public void setBuilding(Building building) {
		if (changedProperties.add(MapProperty.building)) {
			logger.trace("building changed");
		}
		this.building = building;
	}

	/**
	 * Returns the {@link MapLocator} which is used to determine the currently
	 * displayed map section.
	 * 
	 * @return the current map locator which is used to determine the map
	 *         section.
	 */
	public MapLocator getMapLocator() {
		return this.mapLocator;
	}

	/**
	 * Sets the {@link MapLocator} which is used to determine the map section to
	 * be displayed.
	 * 
	 * @param mapLocator
	 *            a <code>MapLocator</code>
	 * @throws NullPointerException
	 *             if <code>mapLocator<code> is <code>null</code>
	 */
	public void setMapLocator(MapLocator mapLocator) {
		if (mapLocator == null) {
			throw new NullPointerException();
		}
		if (changedProperties.add(MapProperty.mapLocator)) {
			logger.trace("mapLocator changed");
		}
		this.mapLocator = mapLocator;
	}

	/**
	 * Sets the currently highlighted POI.
	 * 
	 * @param highlightedPOI
	 *            The currently highlighted POI or <code>null</code>
	 *            if no POI should be highlighted
	 */
	public void setHighlightedPOI(POI highlightedPOI) {
		if (changedProperties.add(MapProperty.highlightedPOI)) {
			logger.trace("highlightedPOI changed");
		}
		this.highlightedPOI = highlightedPOI;
		// change Building POI as well
		if (highlightedPOI == null) {
			createBuildingPOIList(null, null);
		}
	}

	/**
	 * Returns the currently highlighted POI.
	 * 
	 * @return the highlighted POI or <code>null</code> if no POI is highlighted
	 */
	public POI getHighlightedPOI() {
		return highlightedPOI;
	}

	/**
	 * Returns the position of the from marker. If the from marker is set it
	 * will be drawn on the map with a s special symbol. The from marker and the
	 * to marker are used to let the user specify an arbitrary map position, for
	 * example for calculating the route between to points on the campus map.
	 * 
	 * @return The position of the currently from marker or <code>null</code> if
	 *         no such marker was set.
	 * 
	 * @see #getMarkerTo()
	 */
	public MapPosition getMarkerFrom() {
		return markerFrom;
	}

	/**
	 * Sets the <code>markerFrom</code> property, see {@link #getMarkerFrom()}
	 * 
	 * @param markerFrom
	 *            The new marker from location. Can be <code>null</code>.
	 */
	public void setMarkerFrom(MapPosition markerFrom) {
		if (changedProperties.add(MapProperty.markerFrom)) {
			logger.trace("markerFrom changed");
		}
		this.markerFrom = markerFrom;
	}

	/**
	 * Returns the position of the to marker. If the to marker is set it
	 * will be drawn on the map with a s special symbol. The from marker and the
	 * to marker are used to let the user specify an arbitrary map position, for
	 * example for calculating the route between to points on the campus map.
	 * 
	 * @return The position of the currently to marker or <code>null</code> if
	 *         no such marker was set.
	 * 
	 * @see #getMarkerFrom()
	 */
	public MapPosition getMarkerTo() {
		return markerTo;
	}

	/**
	 * Sets the marker to location. See {@link #getMarkerTo()}.
	 * 
	 * @param markerTo
	 *            the new location of the marker to. If the marker should be
	 *            deleted, pass <code>null</code>.
	 */
	public void setMarkerTo(MapPosition markerTo) {
		if(changedProperties.add(MapProperty.markerTo)) {
			logger.trace("markerTo changed");
		}
		this.markerTo = markerTo;
	}

	/**
	 * Returns the route currently drawn.
	 * 
	 * @return The current route or <code>null</code> if no route was set.
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Sets the route to be drawn.
	 * 
	 * @param route
	 *            The route to be drawn or <code>null</code> if no route should
	 *            be displayed.
	 */
	public void setRoute(Route route) {
		if(changedProperties.add(MapProperty.route)) {
			logger.trace("route changed");
		}
		this.route = route;
	}

	/**
	 * Returns the POI representing the building currently displayed. If this
	 * POI is not <code>null</code>, it is always the same as
	 * {@link #getHighlightedPOI()}.
	 * 
	 * @return The POI representing the building currently displayed. Can be
	 *         <code>null</code>.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}

	/**
	 * Sets the POI representing the current building and the list of POIs
	 * inside the building.
	 * 
	 * @param listPOI
	 *            The POI representing the current building. Can be
	 *            <code>null</code> only if <code>list</code> is
	 *            <code>null</code> too.
	 * @param list
	 *            The list of POIs inside the building represented by
	 *            <code>listPOI</code>. Can be <code>null</code> only if
	 *            <code>listPOI</code> is <code>null</code> too.
	 * @throws IllegalArgumentException
	 *             if <code>list</code> is <code>null</code> and
	 *             <code>listPOI</code> is not <code>null</code> or if
	 *             <code>listPOI</code> is <code>null</code> and
	 *             <code>list</code> is not <code>null</code>
	 */
	public void createBuildingPOIList(POI listPOI, List<POI> list) {
		if ((listPOI == null && list == null)
				|| (listPOI != null && list != null)) {
			buildingPOI = listPOI;
			buildingPOIList = list;
			if (changedProperties.add(MapProperty.buildingPOI)){
				logger.trace("buildingPOI changed");
			}
			if (changedProperties.add(MapProperty.buildingPOIList)) {
				logger.trace("buildingPOIList changed");
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns a list of POIs in the currently displayed building.
	 * 
	 * @return The POIs inside the currently displayed building or
	 *         <code>null</code> if no building was set.
	 */
	public List<POI> getBuildingPOIList() {
		return buildingPOIList;
	}

	/**
	 * Sets the index of the current floor.
	 * 
	 * @param currentFloorIndex
	 *            The index of the current floor. Can be <code>null</code>.
	 */
	public void setCurrentFloorIndex(Integer currentFloorIndex) {
		if (changedProperties.add(MapProperty.currentFloorIndex)) {
			logger.trace("currentFloorIndex changed");
		}
		this.currentFloorIndex = currentFloorIndex;
	}

	/**
	 * Returns the index of the current floor.
	 * 
	 * @return currentFloorIndex
	 *            The index of the current floor. Can be <code>null</code>.
	 */
	public Integer getCurrentFloorIndex() {
		return currentFloorIndex;
	}

	/**
	 * Returns all changed properties. The properties are indicated with the
	 * {@link MapProperty} enum.
	 * 
	 * @return a set with identifiers for all changed properties.
	 */
	public Set<MapProperty> getChangedProperties() {
		return Collections.unmodifiableSet(changedProperties);
	}

	/**
	 * Resets all changed properties. After the call,
	 * {@link #getChangedProperties()} will return an empty set.
	 */
	public void resetChangedProperties() {
		this.changedProperties.clear();
		logger.debug("changed properties were reset");
	}

	/**
	 * Marks all properties as changed. This is for example necessary if the
	 * model is created the first time.
	 */
	public void addAllProperties() {
		logger.debug("all properties added");
		for (MapProperty prop : MapProperty.values()) {
			changedProperties.add(prop);
		}
	}
	
	/**
	 * Enum naming all properties of {@link MapModel}. The enum is used
	 * to mark a property as changed.
	 * @see MapModel#getChangedProperties()
	 */
	public static enum MapProperty {
		/**
		 * Identifies the map-property.
		 * @see MapModel#getMap()
		 */
		map,
		
		/**
		 * Identifies the building-property.
		 * @see MapModel#getBuilding()
		 */
		building,
		
		/**
		 * Identifies the POIs-property.
		 * @see MapModel#getPOIs()
		 */
		POIs,
		
		/**
		 * Identifies the mapLocator-property.
		 * @see MapModel#getMapLocator()
		 */
		mapLocator,
		
		/**
		 * Identifies the highlightedPOI-property.
		 * @see MapModel#getHighlightedPOI()
		 */
		highlightedPOI, 
		
		/**
		 * Identifies the markerFrom-property.
		 * @see MapModel#markerFrom
		 */
		markerFrom, 
		
		/**
		 * Identifies the markerTo-property.
		 * @see MapModel#markerTo
		 */
		markerTo, 
		
		/**
		 * Identifies the route-property.
		 * @see MapModel#getRoute()
		 */
		route, 
		
		/**
		 * Identifies the buildingPOI-property
		 * @see MapModel#getBuildingPOI()
		 */
		buildingPOI, 
		
		/**
		 * Identifies the buildingPOIList-property.
		 * @see MapModel#getBuildingPOIList()
		 */
		buildingPOIList, 
		
		/**
		 * Identifies the currentFloorIndex-property.
		 * @see MapModel#currentFloorIndex
		 */
		currentFloorIndex
	}
}
