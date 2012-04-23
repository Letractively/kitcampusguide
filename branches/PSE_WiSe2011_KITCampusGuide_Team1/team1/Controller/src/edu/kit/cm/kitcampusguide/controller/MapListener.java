package edu.kit.cm.kitcampusguide.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapSectionChangedListener;

@ManagedBean
@SessionScoped

public class MapListener {
	
	private MapModel sessionMapModel;
	
	public MapListener(MapModel model) {
		this.sessionMapModel = model;
	}
	
	/**
	 * Sets the which POI clicked on as the highlighted one in the MapModel
	 */
	public void clickOnPOI() {
		//How do I get the POI which was clicked?
		//sessionMapModel.setHighlightedPOI(poiClicked);
	}
	
	/**
	 * Sets the marker where the route should start to the position where the right click occurred.
	 */
	public void setRouteFromByContextMenu() {
		//How do I get the coordinates of the right mouse click which opened up the context menu?
		//sessionMapModel.setMarkerFrom(markerFrom);
	}
	
	/**
	 * Sets the marker where the route should end to the position where the right click occurred.
	 */
	public void setRouteToByContextMenu() {
		//How do I get the coordinates of the right mouse click which opened up the context menu?
		//sessionMapModel.setMarkerTo(markerTo);
	}
	
	/**
	 *  {@see MapSectionChangedListener} Already implemented there.
	 */
	public void mapSectionChangedTriggered () {
		
	}

}
