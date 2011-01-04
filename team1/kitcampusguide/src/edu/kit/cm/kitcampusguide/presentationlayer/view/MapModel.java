package edu.kit.cm.kitcampusguide.presentationlayer.view;

import static junit.framework.Assert.fail;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.DefaultPOIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.SimpleSearch;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

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

		String dbURL = "jdbc:sqlite:defaultpoidbtest.db";
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			fail("SQLLite Driver could not be established");
		}
		try {
			new Map(0, "map1");
			new Map(1, "map2");
			new Map(2, "map3");
			new Map(3, "map4");
			new Map(4, "map5");

			DefaultPOIDB.init(dbURL, new SimpleSearch(), false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
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
		return POISourceImpl.getInstance().getPOIsBySection(null, null, null);
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
