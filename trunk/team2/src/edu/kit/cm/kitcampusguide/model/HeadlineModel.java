package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;

/**
 * This class contains the information about the headline which contains
 * a search input field.
 * 
 * @author Kateryna Yurchenko
 * 
 */

public class HeadlineModel {

	/* The name of the place to be searched.*/
	private String search;
	
	/* The suggestions for the input. */
	private ArrayList<POI> suggestions;
	
	/**
	 * Creates a new headline which contains a search input field.
	 */
	public HeadlineModel() {
		this.search = null;
		this.suggestions = null;
	}
	
	/**
	 * This method returns the name of the place to be searched.
	 * 
	 * @return the name of the place to be searched.
	 */
	public String getSearch() {
		return this.search;
	}
		
	/**
	 * This method sets the name of the place to be searched.
	 * 
	 * @param search the name of the place to be searched.
	 */
	public void setSearch(String search) {
		this.search = search; 
	}
	
	/**
	 * This method sets the suggestions for the input.
	 * 
	 * @param suggestions the suggestions for the input.
	 */
	public void setSuggestions(ArrayList<POI> suggestions) {
		this.suggestions = suggestions; 
	}	
}
