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

	/* The starting point. */
	private Poi from;

	/* The ending point. */
	private Poi to;

	/* Visibility of the selection of the categories of POI within a sidebar. */
	private boolean filterVisible;

	/* True if the sidebar is expanded, false if it is hidden. */
	private boolean extended;

	/* Categories of POI which must be shown on the map. */
	private List<PoiCategory> categories;

	/*
	 * Textual suggestions of POI which must be shown if no matching POI are
	 * found.
	 */
	private List<Poi> suggestions;

	/* Suggestions for the starting point. */
	private List<Poi> suggestionsFrom;

	/* Suggestions for the ending point. */
	private List<Poi> suggestionsTo;

	/* An error message which can be shown within the sidebar. */
	private String errorMessage;

	/**
	 * Creates a new sidebar which contains input fields for the route
	 * calculation.
	 */
	public SidebarModel() {
		this.setFrom(null);
		this.setTo(null);
		this.setFilterVisible(false);
		this.setExtended(true);
		this.categories = new ArrayList<PoiCategory>();
		this.suggestions = new ArrayList<Poi>();
		this.suggestionsFrom = new ArrayList<Poi>();
		this.suggestionsTo = new ArrayList<Poi>();
		this.errorMessage = null;
	}

	/**
	 * This method sets the starting point of the route to be calculated.
	 * 
	 * @param from
	 *            the starting point.
	 */
	public void setFrom(Poi from) {
		this.from = from;
	}

	/**
	 * This method sets the ending point of the route to be calculated.
	 * 
	 * @param to
	 *            the ending point.
	 */
	public void setTo(Poi to) {
		this.to = to;
	}

	/**
	 * This method sets the categories of POI which must be shown on the map.
	 * 
	 * @param categories
	 *            categories of POI which must be shown on the map.
	 */
	public void setCategories(List<PoiCategory> categories) {
		this.categories = categories;
	}

	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input.
	 * 
	 * @param suggestions
	 *            the textual suggestions of POI which must be shown during the
	 *            input.
	 */
	public void setSuggestions(List<Poi> suggestions) {
		this.suggestions = suggestions;
	}

	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input of the starting point.
	 * 
	 * @param suggestionsFrom
	 *            the textual suggestions of POI which must be shown during the
	 *            input of the starting point.
	 */
	public void setSuggestionsFrom(ArrayList<Poi> suggestionsFrom) {
		this.suggestionsFrom = suggestionsFrom;
	}

	/**
	 * This method sets the textual suggestions of POI which must be shown
	 * during the input of the ending point.
	 * 
	 * @param suggestionsTo
	 *            the textual suggestions of POI which must be shown during the
	 *            input of the ending point.
	 */
	public void setSuggestionsTo(List<Poi> suggestionsTo) {
		this.suggestionsTo = suggestionsTo;
	}

	/**
	 * This method sets the error message which must be shown if error occurs.
	 * 
	 * @param error
	 *            the error message which must be shown if error occurs.
	 */
	public void setError(String error) {
		this.errorMessage = error;
	}

	/**
	 * This method changes the visibility of the categories of POI within a
	 * sidebar.
	 * 
	 * @param visible
	 *            if true the categories of POI must be shown within a sidebar,
	 *            otherwise must be hidden.
	 */
	public void setFilterVisible(boolean visible) {
		this.filterVisible = visible;
	}

	/**
	 * This method expands or hides a sidebar.
	 * 
	 * @param extended
	 *            if true the sidebar must be expanded, otherwise must be
	 *            hidden.
	 */
	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	/**
	 * This method returns the starting point of the route to be calculated.
	 * 
	 * @return the starting point.
	 */
	public Poi getFrom() {
		return this.from;
	}

	/**
	 * This method returns the ending point of the route to be calculated.
	 * 
	 * @return the ending point.
	 */
	public Poi getTo() {
		return this.to;
	}

	/**
	 * This method returns the visibility of the selection of the categories of
	 * POI within a sidebar.
	 * 
	 * @return the visibility of the selection of the categories of POI within a
	 *         sidebar.
	 */
	public boolean isFilterVisible() {
		return this.filterVisible;
	}

	/**
	 * This method returns true if the sidebar is expanded, false if it is
	 * hidden.
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
	public List<PoiCategory> getCategories() {
		return this.categories;
	}

	/**
	 * This method returns the textual suggestions of POI which must be shown if
	 * no matching POI are found.
	 * 
	 * @return the textual suggestions of POI which must be shown if no matching
	 *         POI are found.
	 */
	public List<Poi> getSuggestions() {
		return this.suggestions;
	}

	/**
	 * This method returns the suggestions for the starting point.
	 * 
	 * @return the suggestions for the starting point.
	 */
	public List<Poi> getSuggestionsFrom() {
		return this.suggestionsFrom;
	}

	/**
	 * This method returns the suggestions for the ending point.
	 * 
	 * @return the suggestions for the ending point.
	 */
	public List<Poi> getSuggestionsTo() {
		return this.suggestionsTo;
	}

	/**
	 * This method returns the error message which can be shown within the
	 * sidebar.
	 * 
	 * @return the error message which can be shown within the sidebar.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
