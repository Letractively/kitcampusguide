package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.util.List;
import java.util.Set;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * This enumeration defines representations for the both input fields in the view.
 * <code>InputField.ROUTE_FROM</code> represents the "Search/Route from"-input field.
 * <code>InputField.ROUTE_TO</code> represents the "Route to"-input field.
 */
enum InputField {ROUTE_FROM, ROUTE_TO}

/**
 * The interface <code>InputListener</code> defines functions for reacting to user input.
 * This comprises (M1) the search after POIs or routes and the selection of a new language
 * or a new map.
 * 
 * @author Team1 
 */
public interface InputListener {
	
	/**
	 * Triggers a search after <code>searchTerm</code> and ensures that the view
	 * will receive all information necessary to present the result.
	 * If <code>searchTerm</code> can be interpreted as geographical coordinates, the view will be provoked to
	 * set an appropriate marker at the corresponding position. 
	 * Else a search after an appropriate POI will be triggered.
	 * @param searchTerm Non-empty <code>String</code> that shall be searched after. Not null.
	 * @param inputField {@link InputField} with which the search request was triggered.
	 */
	public void searchTriggered(String searchTerm, InputField inputField);
	
	/**
	 * Ensures that the view will receive all information necessary to present the POI <code>soughtAfter</code>
	 * as search result.
	 * @param soughtAfter {@link POI} that shall be presented as search result. Not null.
	 */
	public void searchTriggered(POI soughtAfter);	
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from Non-empty <code>String</code> that represents the starting point of the route. Not null.
	 * @param to Non-empty <code>String</code> that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, String to);	
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from Non-empty <code>String</code> that represents the starting point of the route. Not null.
	 * @param to {@link MapPosition} that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, MapPosition to);
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from {@link MapPosition} that represents the starting point of the route. Not null.
	 * @param to Non-empty <code>String</code> that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, String to);
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code> and ensures that 
	 * the view will receive all information necessary to present the result.
	 * @param from {@link MapPosition} that represents the starting point of the route. Not null.
	 * @param to {@link MapPosition} that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, MapPosition to);
	
	/**
	 * Triggers the presentation of the proposal list <code>proposalList</code> after a search with 
	 * multiple results.
	 * @param proposalList List of {@link POI}s which have been found as search results. 
	 * @param inputField {@link InputField} with which the search request was triggered.
	 */
	public void choiceProposalTriggered(List<POI> proposalList, InputField inputField);
	
	/**
	 * Changes the current language in which the view presents all text output to <code>language</code>.
	 * Only changes the language if it is available on the system.
	 * @param language <code>String</code> that represents the language to which shall be changed. Not null.
	 */
	public void languageChangeTriggered(String language);
	
	/**
	 * Changes to map view.
	 */
	public void changeToMapViewTriggered();
	
	/**
	 * Changes the floor being displayed within building view to <code>floor</code>.
	 * @param floor {@link Map} presenting the floor that shall be displayed. Not null.
	 */
	public void changeFloorTriggered(Map floor);

	/**
	 * Is triggered when the user chooses a different set of categories which
	 * should be used for filtering.
	 * 
	 * @param enabledCategories
	 *            the new set of categories used for filtering.
	 */
	public void changeCategoryFilterTriggered(Set<Category> enabledCategories);
}
