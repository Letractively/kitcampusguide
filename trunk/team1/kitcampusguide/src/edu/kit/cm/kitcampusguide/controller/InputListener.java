package edu.kit.cm.kitcampusguide.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

enum InputFields {ROUTE_FROM, ROUTE_TO}

public interface InputListener {
	
	public void searchTriggered(String searchTerm, InputFields inputField);
	
	public void routeTriggered(String from, String to);
	
	public void languageChangeTriggered(ActionEvent ae);
	
	public void changeToMapViewTriggered();
	
	public void changeFloorTriggered();
	
	public void choiceProposalTriggered(List<POI> proposalList);
	
	public void changeCategoryFilterTriggered();
	
}
