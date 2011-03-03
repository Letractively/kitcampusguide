package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * Classes that implement this interface are used to react to certain events on
 * the map by changing the model accordingly and updating the view if necessary.
 * 
 * @author Fabian
 * 
 */
public interface MapListener {

	/**
	 * This method is called when the user changed the shown map section.
	 * 
	 * @param mapLocator
	 *            the new mapLocator which determines the new map location the
	 *            user entered
	 */
	public void mapLocatorChanged(MapLocator mapLocator);

	/**
	 * Is called when the user clicked on POI marker.
	 * 
	 * @param poiID
	 *            specifies the clicked POI
	 */
	public void clickOnPOI(String poiID);

	/**
	 * Is called when the user wants to calculate a route from a given start
	 * point.
	 * 
	 * @param position
	 *            the MapPosition which marks the start of the route.
	 */
	public void setRouteFromByContextMenu(MapPosition position);

	/**
	 * Is called when the user wants to calculate a route to a given end point.
	 * 
	 * @param position
	 *            the MapPosition which marks the end point of the route.
	 */
	public void setRouteToByContextMenu(MapPosition position);
}
