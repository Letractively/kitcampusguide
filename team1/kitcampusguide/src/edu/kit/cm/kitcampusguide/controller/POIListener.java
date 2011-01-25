package edu.kit.cm.kitcampusguide.controller;

import edu.kit.cm.kitcampusguide.standardtypes.Map;

/**
 * Classes that implement this interfaces are used to react to events that
 * originate from POIs by changing the model accordingly and updating the view
 * if necessary.
 * 
 * @author Fabian
 * 
 */
public interface POIListener {

	/**
	 * Changes the {@link Map} displayed to the map specified by
	 * <code>mapID</code> by setting the attribute {@link MapPosition.map}
	 * 
	 * @param mapID
	 *            the unique ID of the map to which should be switched.
	 */
	public void changeToBuildingMap(int mapID);

	/**
	 * Changes the attribute <code>pois</code> of the current MapModel to the
	 * list of POIs, which are in the building specified by
	 * <code>buildingID</code>
	 * 
	 * @param buildingID
	 *            the unique ID of the building
	 */
	public void showPOIsInBuilding(int buildingID);
}
