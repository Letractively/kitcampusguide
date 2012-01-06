package edu.kit.pse.ass.gui.model;

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

	/**
	 * the selected filters
	 */
	private Collection<Property> filters;

	public String getSearchNearLocation() {
		return searchNearLocation;
	}

	public void setSearchNearLocation(String searchNearLocation) {
		this.searchNearLocation = searchNearLocation;
	}

	public int getSearchNearDistance() {
		return searchNearDistance;
	}

	public void setSearchNearDistance(int searchNearDistance) {
		this.searchNearDistance = searchNearDistance;
	}

	public Collection<Property> getFilters() {
		return filters;
	}

	public void setFilters(Collection<Property> filters) {
		this.filters = filters;
	}

}
