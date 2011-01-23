package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;

/**
 * This class contains the information about the sidebar, which can be expanded
 * or hidden. It contains input fields for the route calculation.
 * 
 * @author Kateryna Yurchenko
 * 
 */

public class SidebarModel {

	// TODO : im Entwurf from und to sind vom Typ String (in der Beschreibung),
	// TODO : aber in den Mehtoden zB getFrom wird POI zurückgegeben usw,
	// TODO : deshalb habe ich zu POI hier geändert, wenn ok, ändert bitte auch im Entwurf.
	
	
	/* The starting point. */
	private POI from;
	
	/* The ending point. */
	private POI to;
	
	/* Visibility of the selection of the categories of POI within a sidebar. */
	private boolean filterVisible;
	
	/* True if the sidebar is expanded, false if it is hidden. */	
	private boolean extended;
	
	/* Categories of POI which must be shown on the map. */
	private ArrayList<POICategory> categories;
	
	/* Textual suggestions of POI which must be shown if no matching POI are found. */
	private ArrayList<POI> suggestions;
	
	/* Suggestions for the starting point. */
	private ArrayList<POI> suggestionsFrom;

	/* Suggestions for the ending point. */
	private ArrayList<POI> suggestionsTo;
	
	/* An error message which can be shown within the sidebar. */
	private String errorMessage;
	
	/**
	 * Creates a new sidebar which contains input fields for the route calculation.
	 */
	public SidebarModel() {
		this.from = null;
		this.to = null;
		this.filterVisible = true;
		this.extended = true;
		this.categories = null;
		this.suggestions = null;
		this.suggestionsFrom = null;
		this.suggestionsTo = null;
		this.errorMessage = null;
	}
	
	/**
	 * This method returns the starting point of the route to be calculated.
	 * 
	 * @return the starting point.
	 */
	public POI getFrom() {
		return this.from;
	}
		
	/**
	 * This method sets the starting point of the route to be calculated.
	 * 
	 * @param from the starting point.
	 */
	public void setFrom(POI from) {
		this.from = from; 
	}
	
	/**
	 * This method returns the ending point of the route to be calculated.
	 * 
	 * @return the ending point.
	 */
	public POI getTo() {
		return this.to;
	}
		
	/**
	 * This method sets the ending point of the route to be calculated.
	 * 
	 * @param to the ending point.
	 */
	public void setTo(POI to) {
		this.to = to; 
	}
	
	/**
	 * This method sets the categories of POI which must be shown on the map.
	 * 
	 * @param categories categories of POI which must be shown on the map.
	 */
	public void setCategories(ArrayList<POICategory> categories) {
		this.categories = categories; 
	}
	
	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input.
	 * 
	 * @param suggestions the textual suggestions of POI which must be shown
	 * during the input.
	 */
	public void setSuggestions(ArrayList<POI> suggestions) {
		this.suggestions = suggestions; 
	}
	
	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input of the starting point.
	 * 
	 * @param suggestions the textual suggestions of POI which must be shown
	 * during the input of the starting point.
	 */
	public void setSuggestionsFrom(ArrayList<POI> suggestionsFrom) {
		this.suggestionsFrom = suggestionsFrom; 
	}
	
	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input of the ending point.
	 * 
	 * @param suggestions the textual suggestions of POI which must be shown
	 * during the input of the ending point.
	 */
	public void setSuggestionsTo(ArrayList<POI> suggestionsTo) {
		this.suggestionsTo = suggestionsTo; 
	}
	
	/**
	 * This method sets the error message which must be shown if error occurs.
	 * 
	 * @param error the error message which must be shown if error occurs.
	 */
	public void setError(String error) {
		this.errorMessage = error; 
	}
	
	/**
	 * This method changes the visibility of the categories of POI within a sidebar.
	 * 
	 * @param visible if true the categories of POI must be shown within a sidebar,
	 * otherwise must be hidden.
	 */
	public void setFilterVisible(boolean visible) {
		this.filterVisible = visible; 
	}
	
	/**
	 * This method expands or hides a sidebar.
	 * 
	 * @param extended if true the sidebar must be expanded, otherwise must be hidden.
	 */
	public void setExtended(boolean extended) {
		this.extended = extended; 
	}

	//TODO: JavaDoc + im Entwurf Getter hinzufŸgen
	public boolean isFilterVisible() {
		return filterVisible;
	}

	public boolean isExtended() {
		return extended;
	}

	public ArrayList<POICategory> getCategories() {
		return categories;
	}

	public ArrayList<POI> getSuggestions() {
		return suggestions;
	}

	public ArrayList<POI> getSuggestionsFrom() {
		return suggestionsFrom;
	}

	public ArrayList<POI> getSuggestionsTo() {
		return suggestionsTo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	
}
