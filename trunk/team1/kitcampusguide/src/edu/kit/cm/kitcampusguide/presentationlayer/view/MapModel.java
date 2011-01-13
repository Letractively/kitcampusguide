package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

// TODO: Javadocs!
public class MapModel {

	private Collection<POI> pois;
	private Building building;
	private MapSection mapSection;
	private POI highlightedPOI;
	private MapPosition markerFrom;
	private MapPosition markerTo;
	private Route route;
	private POI buildingPOI;
	private List<POI> buildingPOIList;
	private Map map;

	static {
		// TODO: Test code, needs to be deleted later
		MapSection box = new MapSection(new WorldPosition(49.0179, 8.40232),
				new WorldPosition(49.0078, 8.42622));
	new Map(1, "campus", box,
//				"http://tile.openstreetmap.org/${z}/${x}/${y}.png");
				"./resources/tiles/campus/${z}/${x}/${y}.png", 14, 18);
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return Map.getMapByID(1);
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the pois
	 */
	public Collection<POI> getPOIs() {
		// TODO: Remove this after the controller is ready
		return Collections.emptyList();
	}

	/**
	 * @param pois
	 *            the pois to set
	 */
	public void setPOIs(Collection<POI> pois) {
		this.pois = pois;
	}

	/**
	 * @return the buildingID
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * @param building
	 *            the building to set
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * @return the mapSection
	 */
	public MapSection getMapSection() {
		return mapSection;
	}

	/**
	 * @param mapSection
	 *            the mapSection to set
	 */
	public void setMapSection(MapSection mapSection) {
		this.mapSection = mapSection;
	}

	/**
	 * @return the highlightedPOI
	 */
	public POI getHighlightedPOI() {
		return highlightedPOI;
	}

	/**
	 * @param highlightedPOI
	 *            the highlightedPOI to set
	 */
	public void setHighlightedPOI(POI highlightedPOI) {
		this.highlightedPOI = highlightedPOI;
	}

	/**
	 * @return the markerFrom
	 */
	public MapPosition getMarkerFrom() {
		return markerFrom;
	}

	/**
	 * @param markerFrom
	 *            the markerFrom to set
	 */
	public void setMarkerFrom(MapPosition markerFrom) {
		this.markerFrom = markerFrom;
	}

	/**
	 * @return the markerTo
	 */
	public MapPosition getMarkerTo() {
		return markerTo;
	}

	/**
	 * @param markerTo
	 *            the markerTo to set
	 */
	public void setMarkerTo(MapPosition markerTo) {
		this.markerTo = markerTo;
	}

	/**
	 * @return the route
	 */
	public Route getRoute() {
		 return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * @return the buildingPOI
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}

	/**
	 * @param buildingPOI
	 *            the buildingPOI to set
	 */
	public void setBuildingPOI(POI buildingPOI) {
		this.buildingPOI = buildingPOI;
	}

	/**
	 * @return the buildingPOIList
	 */
	public List<POI> getBuildingPOIList() {
		return buildingPOIList;
	}

	/**
	 * @param buildingPOIList
	 *            the buildingPOIList to set
	 */
	public void setBuildingPOIList(List<POI> buildingPOIList) {
		this.buildingPOIList = buildingPOIList;
	}

}
