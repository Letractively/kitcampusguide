package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.Collection;
import java.util.Collections;

import edu.kit.cm.kitcampusguide.standardtypes.*;

/**
 * Manages all categories to be currently displayed and all categories for the user. 
 * @author Fred
 *
 */
public class CategoryModel {
	/** Stores the currently active categories.*/
	private Collection<Category> currentCategories;
	
	/** Stores all available categories */
	private Collection<Category> categories;
	
	/**
	 * Constructs a new CategoryModel.
	 */
	public CategoryModel(Collection<Category> currentCategories, Collection<Category> categories) {
		this.currentCategories = currentCategories;
		this.categories = categories;
	}
	
	/**
	 * Returns all available categories.
	 * @return All available categories.
	 */
	public Collection<Category> getCategories() {
		return Collections.unmodifiableCollection(categories);
	}
	
	/**
	 * Sets the current categories.
	 * @param categories The current categories. If <code>null</code>, no changes are made.
	 */
	public void setCurrentCategories(Collection<Category> categories) {
		if (categories != null) {
			this.currentCategories = categories;
		}
	}
	
	/**
	 * Returns the current categories.
	 * @return The current categories.
	 */
	public Collection<Category> getCurrentCategories() {
		return currentCategories;
	}
}
