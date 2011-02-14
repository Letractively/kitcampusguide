package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import edu.kit.cm.kitcampusguide.standardtypes.Category;

/**
 * Manages all categories to be currently displayed and all categories for the user. 
 * @author Fred
 *
 */
public class CategoryModel implements Serializable {
	/** Stores the currently active categories. Is a managed property. */
	private Collection<Category> currentCategories;
	
	/** Stores all available categories. Is a managed property. */
	private Collection<Category> categories;
	
	
	/**
	 * Returns all available categories which a user can use for filtering.
	 * @return All available categories.
	 */
	public Collection<Category> getCategories() {
		return Collections.unmodifiableCollection(categories);
	}
	
	/**
	 * Sets the current categories. Only these categories will be visible and shown
	 * to the user.
	 * @param categories The current categories.
	 * @throws NullPointerException if <code>categories</code> is <code>null</code>
	 */
	public void setCurrentCategories(Collection<Category> categories) {
		if (categories == null) {
			throw new NullPointerException();
		}
		this.currentCategories = categories;
	}
	
	/**
	 * Returns the currently visible categories.
	 * @return The currently visible categories.
	 */
	public Collection<Category> getCurrentCategories() {
		return currentCategories;
	}
}
