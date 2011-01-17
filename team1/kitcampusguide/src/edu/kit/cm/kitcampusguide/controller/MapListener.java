package edu.kit.cm.kitcampusguide.controller;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;

/**
 * 
 * @author Fabian
 * 
 */
public interface MapListener {
	
	/**
	 * The Method changes the {@link presentationlayer.view.MapSection} of the current {@link presentationlayer.view.MapModel} to <code>mapSection</code>.
	 * @param mapSection to which MapSection the MapSection in the current MapModel is set. 
	 */
	public void mapSectionChanged(MapSection mapSection);
	
	
	/**
	 * Sets the {@link presentationlayer.view.POI} specified by <code>poiID</code> as the highlighted one in the current {@link presentationlayer.view.MapModel}.
	 * @param poiID specifies the POI by his unique ID.
	 */
	public void clickOnPOI(String poiID);
	
	/**
	 * Sets the parameter {@link presentationlayer.view.MapModel#markerFrom} to the {@link presentationlayer.view.MapPositon} provided by <code>position</code>.
	 * Thereby changing where a route calculation should start.
	 * @param position the MapPosition provided.
	 */
	public void setRouteFromByContextMenu(MapPosition position);

	/**
	 * Sets the parameter {@link presentationlayer.view.MapModel.markerTo} to the {@link presentationlayer.view.MapPositon} provided by <code>position</code>.
	 * Thereby changing where a route calculation should end.
	 * @param position the MapPosition provided.
	 */
	public void setRouteToByContextMenu(MapPosition position);
}
