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
	 * 				<code>String</code> that shall be searched after. Not <code>null</code>.
	 * @param inputField 
	 * 				{@link InputField} which the search term has been typed into. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>searchTerm</code> or <code>inputField</code> is <code>null</code>.
	 */
	public void searchTriggered(String searchTerm, InputField inputField) throws NullPointerException;
	
	/**
	 * Is called when the user wants to search after a certain <code>POI</code>.
	 * 
	 * @param soughtAfter 
	 * 				{@link POI} that shall be searched after. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>soughtAfter</code> is <code>null</code>.
	 */
	public void searchTriggered(POI soughtAfter) throws NullPointerException;	
	
	/**
	 * Is called when the user wants to search after a route between two points represented by <code>String</code>s.
	 * 
	 * @param routeFrom 
	 * 				<code>String</code> that represents the starting point of the route. Not <code>null</code>.
	 * @param routeTo 
	 * 				<code>String</code> that represents the end point of the route. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>routeFrom</code> or <code>routeTo</code> is <code>null</code>.
	 */
	public void routeTriggered(String routeFrom, String routeTo) throws NullPointerException;	
	
	/**
	 * Is called when the user wants to search after a route between two points, one represented by a <code>String</code>,
	 * the other by a <code>MapPosition</code>.
	 *
	 * @param routeFrom 
	 * 				<code>String</code> that represents the starting point of the route. Not <code>null<code>.
	 * @param to 
	 * 				{@link MapPosition} that represents the end point of the route. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>routeFrom</code> or <code>to</code> is <code>null</code>.
	 */
	public void routeTriggered(String from, MapPosition to) throws NullPointerException;
	
	/**
	 * Is called when the user wants to search after a route between two points, one represented by a <code>String</code>,
	 * the other by a <code>MapPosition</code>.
	 * 
	 * @param from 
	 * 				{@link MapPosition} that represents the starting point of the route. Not <code>null</code>.
	 * @param routeTo 
	 *				<code>String</code> that represents the end point of the route. Not <code>null</code>.
	 *@throws
	 * 				NullPointerException if <code>from</code> or <code>routeTo</code> is <code>null</code>.
	 */
	public void routeTriggered(MapPosition from, String routeTo) throws NullPointerException;
	
	/**
	 * Is called when the user wants to search after a route between two <code>MapPosition</code>s.
	 * 
	 * @param from 
	 * 				{@link MapPosition} that represents the starting point of the route. Not <code>null</code>.
	 * @param to 
	 * 				{@link MapPosition} that represents the end point of the route. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>from</code> or <code>to</code> is <code>null</code>.
	 */
	public void routeTriggered(MapPosition from, MapPosition to) throws NullPointerException;
	
	/**
	 * Is called after a search with multiple search results.
	 * 
	 * @param proposalList 
	 * 				List of {@link POI}s which have been found as search results. Not <code>null</code>. 
	 * @param inputField 
	 * 				{@link InputField} with which the search request was triggered. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>proposalList</code> or <code>inputField</code> is <code>null</code>.
	 */
	public void choiceProposalTriggered(List<POI> proposalList, InputField inputField) throws NullPointerException;
	
	/**
	 * Is called when the user wants to change the language.
	 * 
	 * @param language 
	 * 				<code>String</code> that represents the language to which shall be changed.
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
	 * 				{@link Map} presenting the floor that shall be displayed. Not <code>null</code>.
	 * @throws
	 * 				NullPointerException if <code>floor</code> is <code>null</code>.
	 */
	public void changeFloorTriggered(Map floor) throws NullPointerException;

	/**
	 * Is triggered when the user chooses a different set of categories which
	 * should be used for filtering.
	 * 
	 * @param enabledCategories
	 *            	the new set of categories used for filtering.
	 * @throws
	 * 				NullPointerException if <code>enabledCategories</code> is <code>null</code>.    
	 */
	public void changeCategoryFilterTriggered(Set<Category> enabledCategories) throws NullPointerException;
}
