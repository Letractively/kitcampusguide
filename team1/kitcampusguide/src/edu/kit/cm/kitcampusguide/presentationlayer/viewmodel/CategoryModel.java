package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.Collection;
import java.util.Collections;

import edu.kit.cm.kitcampusguide.controller.DefaultModelValues;
import edu.kit.cm.kitcampusguide.standardtypes.Category;

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
	
	private DefaultModelValues defaultModelValues = new DefaultModelValues();
	
	/**
	 * Constructs a new category model.
	 * @param currentCategories The categories set to visible at the beginning.
	 * @param categories All available categories.
	 */
	/*public CategoryModel(Collection<Category> currentCategories, Collection<Category> categories) {
		this.currentCategories = currentCategories;
		this.categories = categories;
	}*/
	
	public CategoryModel () {
		//TODO
		categories  = defaultModelValues.getDefaultCategories();
		currentCategories = defaultModelValues.getDefaultCurrentCategories();
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
