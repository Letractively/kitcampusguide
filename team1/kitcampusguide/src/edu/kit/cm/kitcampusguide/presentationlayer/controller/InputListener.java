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
 * The interface <code>InputListener</code> defines functions for reacting to events caused by user input
 * by changing the model accordingly and thus ensuring that the view receives all information necessary.
 * 
 * @author Team1 
 */
public interface InputListener {
	
	/**
	 * Is called when the user wants to search after a certain search term.
	 * 
	 * @param searchTerm 
	 * 				Non-empty <code>String</code> that shall be searched after. Not null.
	 * @param inputField 
	 * 				{@link InputField} which the search term has been typed into.
	 */
	public void searchTriggered(String searchTerm, InputField inputField);
	
	/**
	 * Is called when the user wants to search after a certain <code>POI</code>.
	 * 
	 * @param soughtAfter 
	 * 				{@link POI} that shall be searched after. Not null.
	 */
	public void searchTriggered(POI soughtAfter);	
	
	/**
	 * Is called when the user wants to search after a route between two points represented by <code>String</code>s.
	 * 
	 * @param from 
	 * 				Non-empty <code>String</code> that represents the starting point of the route. Not null.
	 * @param to 
	 * 				Non-empty <code>String</code> that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, String to);	
	
	/**
	 * Is called when the user wants to search after a route between two points, one represented by a <code>String</code>,
	 * the other by a <code>MapPosition</code>.
	 *
	 * @param from 
	 * 				Non-empty <code>String</code> that represents the starting point of the route. Not null.
	 * @param to 
	 * 				{@link MapPosition} that represents the end point of the route. Not null.
	 */
	public void routeTriggered(String from, MapPosition to);
	
	/**
	 * Is called when the user wants to search after a route between two points, one represented by a <code>String</code>,
	 * the other by a <code>MapPosition</code>.
	 * 
	 * @param from 
	 * 				{@link MapPosition} that represents the starting point of the route. Not null.
	 * @param to 
	 *				Non-empty <code>String</code> that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, String to);
	
	/**
	 * Is called when the user wants to search after a route between two <code>MapPosition</code>s.
	 * 
	 * @param from 
	 * 				{@link MapPosition} that represents the starting point of the route. Not null.
	 * @param to 
	 * 				{@link MapPosition} that represents the end point of the route. Not null.
	 */
	public void routeTriggered(MapPosition from, MapPosition to);
	
	/**
	 * Is called after a search with multiple search results.
	 * 
	 * @param proposalList 
	 * 				List of {@link POI}s which have been found as search results. 
	 * @param inputField 
	 * 				{@link InputField} with which the search request was triggered.
	 */
	public void choiceProposalTriggered(List<POI> proposalList, InputField inputField);
	
	/**
	 * Is called when the user wants to change the language.
	 * 
	 * @param language 
	 * 				<code>String</code> that represents the language to which shall be changed. Not null.
	 */
	public void languageChangeTriggered(String language);
	
	/**
	 * Is called when the user wants to change to map view.
	 */
	public void changeToMapViewTriggered();
	
	/**
	 * Is called when the user wants to change the floor within building view.
	 * 
	 * @param floor 
	 * 				{@link Map} presenting the floor that shall be displayed. Not null.
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
