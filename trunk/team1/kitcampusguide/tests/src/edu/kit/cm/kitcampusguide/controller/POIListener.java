package edu.kit.cm.kitcampusguide.controller;

import edu.kit.cm.kitcampusguide.standardtypes.Map;

/**
 * 
 * @author Fabian
 *
 */
public interface POIListener {
	
	/**
	 * Changes the {@link Map} displayed to the map specified by <code>mapID</code> by setting the attribute {@link MapPosition.map}
	 * @param mapID the unique ID of the map to which should be switched.
	 */
	public void changeToBuildingMap(int mapID);
	
	/**
	 * Changes the attribute pois of the current MapModel to the list of POIs, which are in the building specified by <code>buildingID</code>
	 * @param buildingID the unique ID of the building
	 */
	public void showPOIsInBuilding(int buildingID);
}
