package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<POICategory> categories;
	
	/* Textual suggestions of POI which must be shown if no matching POI are found. */
	private List<POI> suggestions;
	
	/* Suggestions for the starting point. */
	private List<POI> suggestionsFrom;

	/* Suggestions for the ending point. */
	private List<POI> suggestionsTo;
	
	/* An error message which can be shown within the sidebar. */
	private String errorMessage;
	
	/**
	 * Creates a new sidebar which contains input fields for the route calculation.
	 */
	public SidebarModel() {
		this.setFrom(null);
		this.setTo(null);
		this.setFilterVisible(true);
		this.setExtended(true);
		this.categories = new ArrayList<POICategory>();
		this.suggestions = new ArrayList<POI>();
		this.suggestionsFrom = new ArrayList<POI>();
		this.suggestionsTo = new ArrayList<POI>();
		this.errorMessage = null;
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
	public void setCategories(List<POICategory> categories) {
		this.categories = categories; 
	}
	
	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input.
	 * 
	 * @param suggestions the textual suggestions of POI which must be shown
	 * during the input.
	 */
	public void setSuggestions(List<POI> suggestions) {
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
	public void setSuggestionsTo(List<POI> suggestionsTo) {
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

	//TODO: im Entwurf Getter hinzufuegen!
	
	/**
	 * This method returns the starting point of the route to be calculated.
	 * 
	 * @return the starting point.
	 */
	public POI getFrom() {
		return this.from;
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
	 * This method returns the visibility of the selection of the categories of POI within a sidebar.
	 * 
	 * @return the visibility of the selection of the categories of POI within a sidebar.
	 */
	public boolean isFilterVisible() {
		return this.filterVisible;
	}

	/**
	 * This method returns true if the sidebar is expanded, false if it is hidden.
	 * 
	 * @return true if the sidebar is expanded, false if it is hidden.
	 */
	public boolean isExtended() {
		return this.extended;
	}

	/**
	 * This method returns the categories of POI which must be shown on the map.
	 * 
	 * @return the categories of POI which must be shown on the map.
	 */
	public List<POICategory> getCategories() {
		return this.categories;
	}

	/**
	 * This method returns the textual suggestions of POI which must be shown if no matching POI are found.
	 * 
	 * @return the textual suggestions of POI which must be shown if no matching POI are found.
	 */
	public List<POI> getSuggestions() {
		return this.suggestions;
	}

	/**
	 * This method returns the suggestions for the starting point.
	 * 
	 * @return the suggestions for the starting point.
	 */
	public List<POI> getSuggestionsFrom() {
		return this.suggestionsFrom;
	}

	/**
	 * This method returns the suggestions for the ending point.
	 * 
	 * @return the suggestions for the ending point.
	 */
	public List<POI> getSuggestionsTo() {
		return this.suggestionsTo;
	}

	/**
	 * This method returns the error message which can be shown within the sidebar.
	 * 
	 * @return the error message which can be shown within the sidebar.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}	
}
