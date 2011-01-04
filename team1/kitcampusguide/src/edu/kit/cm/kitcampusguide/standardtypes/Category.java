package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents categories.
 * Saves the respective <code>id</code>, the <code>name</code> and gives functions to get categories.
 * @author fred
 *
 */
public class Category {	
	/** Saves the id of the category*/
	private final int id;
	
	/** Saves the name of the category*/
	private final String name;
	
	/** Saves all categories.*/
	private static Collection<Category> categories = new ArrayList<Category>();
	
	/**
	 * Constructs a new category.
	 * Adds it to the collection of categories.
	 * @param id The ID the new category should have. Must be unique. However, this is not tested.
	 * @param name The name of the new category.
	 */
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
		categories.add(this);
	}
	
	/**
	 * Returns the ID of this category.
	 * @return The ID of this category.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this category.
	 * @return The name of this category.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a collection of all categories.
	 * @return A collection containing all categories.
	 */
	public static Collection<Category> getAllCategories() {
		return Collections.unmodifiableCollection(categories);
	}
	
	/**
	 * Returns a collection of the categories with an ID in <code>ids</code>. 
	 * If there are duplicate IDs all of the categories with that id are returned. However no guarantee is given for that in future versions.
	 * @param ids A collection of IDs representing the categories that should be returned.
	 * @return A collection of the categories with an ID in <code>ids</code>.
	 */
	public static Collection<Category> getCategoriesByIDs(Collection<Integer> ids) {
		Collection<Category> result = new ArrayList<Category>(ids.size());
		for (Category c : getAllCategories()) {
			if (ids.contains(c.getID())) {
				result.add(c);
			}
		}
		return result;
	}
}
