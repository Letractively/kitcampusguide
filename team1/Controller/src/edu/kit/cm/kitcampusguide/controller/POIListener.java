package edu.kit.cm.kitcampusguide.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel;

@ManagedBean
@SessionScoped

public class POIListener {

	private MapModel sessionMapModel;
	
	
	public POIListener(MapModel model) {
		this.sessionMapModel = model;
	}
	
	/**
	 * 	Shows a template optimized for printing the information of the currently highlighted POI
	 * @return Path to the JSF-Form for the print view.
	 */
	public String printTriggered() {
		return "/POIprintView.xhtml"; /* The question is do we actually need this on the controller, since
		everything we need to know to display this is already known by View by the time this would be called.*/
	}
	
	public void changeToBuildingMapTriggered() {
		sessionMapModel.setBuilding(sessionMapModel.getHighlightedPOI().getBuilding());
		sessionMapModel.setMap(sessionMapModel.getBuilding().getFloors().get(sessionMapModel.getBuilding().getGroundFloorIndex()));
		//We need to store which floor is currently displayed.
		sessionMapModel.setHighlightedPOI(null);
	}
	
	
}
