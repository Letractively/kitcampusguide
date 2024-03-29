package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the information about the headline which contains a
 * search input field.
 * 
 * @author Kateryna Yurchenko
 * 
 */

public class HeadlineModel {

	/* The name of the place to be searched. */
	private String search;

	/* The suggestions for the input. */
	private List<Poi> suggestions;

	/**
	 * Creates a new headline which contains a search input field.
	 */
	public HeadlineModel() {
		this.search = null;
		this.suggestions = new ArrayList<Poi>();
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
	 * @param search
	 *            the name of the place to be searched.
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * This method sets the suggestions for the input.
	 * 
	 * @param list
	 *            the suggestions for the input.
	 */
	public void setSuggestions(List<Poi> list) {
		this.suggestions = list;
	}

	/**
	 * This method returns the suggestions for the input.
	 * 
	 * @return the suggestions for the input.
	 */
	public List<Poi> getSuggestions() {
		return this.suggestions;
	}
}
