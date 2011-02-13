package edu.kit.cm.kitcampusguide.controller;

import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * This enumeration defines representations for the both input fields in the view.
 * <code>InputFields.ROUTE_FROM</code> represents the "Search/Route from"-input field.
 * <code>InputFiels.ROUTE_TO</code> represents the "Route to"-input field.
 */
enum InputFields {ROUTE_FROM, ROUTE_TO}

/**
 * The interface <code>InputListener</code> defines functions for reacting to user input.
 * This comprises (M1) the search after pois or routes and the selection of a new language
 * or a new map.
 * 
 * @author Team1 
 */
public interface InputListener {
	
	/**
	 * Triggers a search after <code>searchTerm</code> and ensures that the view
	 * will receive all information necessary to present the result.
	 * @param searchTerm Non-empty string that shall be searched after. Not null.
	 * @param inputField InputField with which the search request was triggered.
	 */
	public void searchTriggered(String searchTerm, InputFields inputField);
	
	/**
	 * Ensures that the view will receive all information necessary to present the poi <code>soughtAfter</code>
	 * as search result.
	 * @param soughtAfter Poi that shall be presented as search result. Not null.
	 */
	public void searchTriggered(POI soughtAfter);	
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from Non-empty string that represents the starting point of the route. Not null.
	 * @param to Non-empty string that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, String to);	
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from Non-empty string that represents the starting point of the route. Not null.
	 * @param to MapPosition that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, MapPosition to);
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from MapPosition that represents the starting point of the route. Not null.
	 * @param to Non-empty string that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, String to);
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from MapPosition that represents the starting point of the route. Not null.
	 * @param to MapPosition that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, MapPosition to);
	
	/**
	 * Changes the current language in which the view presents all text output to <code>language</code>.
	 * @param language String that represents the language to which shall be changed.
	 */
	public void languageChangeTriggered(String language);
	
	/**
	 * Changes to map view.
	 */
	public void changeToMapViewTriggered();
	
	/**
	 * Changes the floor being displayed within building view to <code>floor</code>.
	 * @param floor Map presenting the floor that shall be displayed.
	 */
	public void changeFloorTriggered(Map floor);
	
	/*
	public void choiceProposalTriggered(List<POI> proposalList);
	
	public void changeCategoryFilterTriggered();
	*/
	
}
