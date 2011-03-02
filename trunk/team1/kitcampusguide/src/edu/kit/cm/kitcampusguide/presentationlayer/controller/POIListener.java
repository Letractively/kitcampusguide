package edu.kit.cm.kitcampusguide.presentationlayer.controller;


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
	 * Is called when the user wants to change into a building.
	 * 
	 * @param buildingID
	 *            the unique ID of the building to which should be switched
	 */
	public void changeToBuildingMap(int buildingID);

	/**
	 * Is called when the user selects an entry of a POI list of the currently
	 * highlighted building POI.
	 * 
	 * @param poiID
	 *            the selected poiID of the clicked POI.
	 * @throws NullPointerException
	 *             if poiID is <code>null</code>
	 */
	public void listEntryClicked(String poiID);
	
	
	/**
	 * Is called when the user wants to see a list of all POIs inside a specific
	 * building.
	 * 
	 * @param buildingID
	 *            the unique ID of the building
	 */
	public void showPOIsInBuilding(int buildingID);
}
