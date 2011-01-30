package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapPhaseListener;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * This class manages the properties of the shown map.
 * @author Fred
 *
 */
public class MapModel implements Serializable {

	/** The POIs currently shown.*/
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
	/**
	 * The building POI for which a list of POIs inside the building should be
	 * displayed or <code>null</code>.
	 */
	private POI buildingPOI;
	/**
	 * The list of POIs in the building for which the building POI is currently
	 * displayed or <code>null</code>.
	 */
	private List<POI> buildingPOIList;
	/** The map currently displayed. */
	private Map map;

	/**
	 * Stores all changed properties. If the current request is not a partial request, all
	 * properties are added automatically. Otherwise the set will be emptied before the 
	 * execute application logic phase (see {@link MapPhaseListener})
	 */
	private Set<MapProperty> changedProperties = new HashSet<MapModel.MapProperty>();
	
	private DataModel<Map> floors;

	public MapModel() {
		System.out.println("created");
		addAllProperties();
		List<Map> list = new ArrayList<Map>();
		list.add(Map.getMapByID(1));
		setFloors(new ListDataModel<Map>(list));
	}

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
	 */
	public void setMap(Map map) {
		if (map == null) {
			throw new NullPointerException();
		}
		this.map = map;
		changedProperties.add(MapProperty.map);
	}

	/**
	 * Returns the POIs to be displayed. TESTCODE, needs to be removed once
	 * controller is finished.
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
	 */
	public void setPOIs(Collection<POI> pois) {
		if (pois != null) {
			this.pois = pois;
			changedProperties.add(MapProperty.POIs);
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
	 * Sets the currently displayed building to building.
	 * 
	 * @param building
	 *            The building to display. Can be <code>null</code>.
	 */
	public void setBuilding(Building building) {
		changedProperties.add(MapProperty.building);
		this.building = building;
	}

	/**
	 * Returns the {@link MapLocator} which is used to determine the currently
	 * displayed map section.
	 * 
	 * @return The MapSection currently displayed.
	 */
	public MapLocator getMapLocator() {
		return this.mapLocator;
	}

	/**
	 * Sets the {@link MapLocator} which is used to determine the map section to
	 * be displayed.
	 * 
	 * @param mapLocator
	 *            Sets the MapSection to be displayed mapSection. If
	 *            <code>null</code>, no change is made.
	 */
	public void setMapLocator(MapLocator mapLocator) {
		changedProperties.add(MapProperty.mapLocator);
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
		changedProperties.add(MapProperty.highlightedPOI);
		this.highlightedPOI = highlightedPOI;
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
	 * Returns the MapPosition a route is drawn from.
	 * 
	 * @return The MapPosition a route is drawn from. Can return
	 *         <code>null</code>.
	 */
	public MapPosition getMarkerFrom() {
		return markerFrom;
	}

	/**
	 * Sets the MapPosition a route is drawn from.
	 * 
	 * @param markerFrom
	 *            The MapPosition a route is drawn from. Can be
	 *            <code>null</code>.
	 */
	public void setMarkerFrom(MapPosition markerFrom) {
		changedProperties.add(MapProperty.markerFrom);
		this.markerFrom = markerFrom;
	}

	/**
	 * Returns the MapPosition a route is drawn to.
	 * 
	 * @return The MapPosition a route is drawn to. Can be <code>null</code>.
	 */
	public MapPosition getMarkerTo() {
		return markerTo;
	}

	/**
	 * Sets the MapPosition a route is drawn to.
	 * 
	 * @param markerTo
	 *            The MapPosition a route is drawn to. Can be <code>null</code>.
	 */
	public void setMarkerTo(MapPosition markerTo) {
		changedProperties.add(MapProperty.markerTo);
		this.markerTo = markerTo;
	}

	/**
	 * Sets the route to be drawn.
	 * 
	 * @return The route to be drawn. Can return <code>null</code>.
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Sets the route to be drawn.
	 * 
	 * @param route
	 *            The route to be drawn. Can be <code>null</code>.
	 */
	public void setRoute(Route route) {
		changedProperties.add(MapProperty.route);
		this.route = route;
	}

	/**
	 * Returns the POI representing the building currently displayed.
	 * 
	 * @return The POI representing the building currently displayed. Can be
	 *         <code>null</code>.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}

	/**
	 * Sets the POI representing the current building and the list of POIs
	 * inside the building. If both list and listPOI are <code>null</code>,or
	 * both are not <code>null</code>, changes are made.
	 * 
	 * @param listPOI
	 *            The POI representing the current building. Can be
	 *            <code>null</code> only if <code>list</code> is
	 *            <code>null</code> too.
	 * @param list
	 *            The list of POIs inside the building represented by
	 *            <code>listPOI</code>. Can be <code>null</code> only if
	 *            <code>listPOI</code> is <code>null</code> too.
	 */
	public void createBuildingPOIList(POI listPOI, List<POI> list) {
		if ((listPOI == null && list == null)
				|| (listPOI != null && list != null)) {
			buildingPOI = listPOI;
			buildingPOIList = list;
			changedProperties.add(MapProperty.buildingPOI);
			changedProperties.add(MapProperty.buildingPOIList);
		}
	}

	/**
	 * Returns a list of POIs in the currently displayed building.
	 * 
	 * @return The POIs inside the currently displayed building. Can be
	 *         <code>null</code>.
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
		changedProperties.add(MapProperty.currentFloorIndex);
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
	
	// TODO
	public Set<MapProperty> getChangedProperties() {
		return Collections.unmodifiableSet(changedProperties);
	}

	
	public void resetChangedProperties() {
		System.out.println("reset");
		this.changedProperties.clear();
	}

	public static enum MapProperty {
		map, POIs, building, mapLocator, highlightedPOI, markerFrom, markerTo, route, buildingPOI, buildingPOIList, currentFloorIndex
	}

	public void addAllProperties() {
		for (MapProperty prop : MapProperty.values()) {
			changedProperties.add(prop);
		}
	}

	public void setFloors(DataModel<Map> floors) {
		this.floors = floors;
	}

	public DataModel<Map> getFloors() {
		return floors;
	}
}
