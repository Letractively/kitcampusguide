package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import edu.kit.cm.kitcampusguide.standardtypes.Category;

/**
 * Manages all categories to be currently displayed and all categories for the user. 
 * @author Fred
 *
 */
public class CategoryModel implements Serializable {
	/** Stores the currently active categories. Is a managed property. */
	private Set<Category> currentCategories;
	
	/** Stores all available categories. Is a managed property. */
	private Set<Category> categories;
	
	/**
	 * Default constructor.
	 */
	public CategoryModel() {
		
	}
		
	/**
	 * Returns all available categories which a user can use for filtering.
	 * @return All available categories.
	 */
	public Set<Category> getCategories() {
		return Collections.unmodifiableSet(categories);
	}
	
	/**
	 * Sets the current categories. Only these categories will be visible and shown
	 * to the user.
	 * @param categories The current categories.
	 * @throws NullPointerException if <code>categories</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>categories</code> is not not contained
	 * in categories attribute.
	 */
	public void setCurrentCategories(Set<Category> categories) {
		if (categories == null) {
			throw new NullPointerException();
		}
		
		if (!categories.isEmpty()) {
			System.out.print("Alle Parameter" + categories.toString());
			if (!this.categories.containsAll(categories)) {
				throw new IllegalArgumentException();
			}
		}

		
		this.currentCategories = categories;
	}
	
	/**
	 * Returns the currently visible categories.
	 * @return The currently visible categories.
	 */
	public Set<Category> getCurrentCategories() {
		return currentCategories;
	}
	
	/**
	 * Sets the categories-property.
	 * @param categories Not null.
	 * @throws NullPointerException if <code>categories</code> is <code>null</code>
	 */
	public void setCategories(Set<Category> categories) {
		if (categories == null) {
			throw new NullPointerException();
		}
		this.categories = categories;
	}
}
