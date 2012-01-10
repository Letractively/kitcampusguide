package edu.kit.pse.ass.gui.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

/**
 * This class represents the SearchFilter model on the advanced search page
 * 
 * @author Jannis Koch
 * 
 */
public class SearchFilterModel {

	/**
	 * the location for the location search
	 * 
	 * (location search not yet implemented -> wunschkriterium!)
	 */
	private String searchNearLocation;

	/**
	 * the distance in metres for the location search
	 * 
	 * (location search not yet implemented -> wunschkriterium!)
	 */
	private int searchNearDistance = 300;

	/** the selected filters. */
	private Collection<Property> filters;

	/**
	 * Gets the search near location.
	 * 
	 * @return the search near location
	 */
	public String getSearchNearLocation() {
		return searchNearLocation;
	}

	/**
	 * Sets the search near location.
	 * 
	 * @param searchNearLocation
	 *            the new search near location
	 */
	public void setSearchNearLocation(String searchNearLocation) {
		this.searchNearLocation = searchNearLocation;
	}

	/**
	 * Gets the search near distance.
	 * 
	 * @return the search near distance
	 */
	public int getSearchNearDistance() {
		return searchNearDistance;
	}

	/**
	 * Sets the search near distance.
	 * 
	 * @param searchNearDistance
	 *            the new search near distance
	 */
	public void setSearchNearDistance(int searchNearDistance) {
		this.searchNearDistance = searchNearDistance;
	}

	/**
	 * Gets the filters.
	 * 
	 * @return the filters
	 */
	public Collection<Property> getFilters() {
		return filters;
	}

	/**
	 * Sets the filters.
	 * 
	 * @param filters
	 *            the new filters
	 */
	public void setFilters(Collection<Property> filters) {
		this.filters = filters;
	}

}
