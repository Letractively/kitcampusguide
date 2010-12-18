package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.Collections;

/**
 * Models Categories.
 * Saves the respective id, the name and gives functions to get Categories.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class Category {
	private final int id;
	private final String name;
	private static Collection<Category> categories;
	
	/**
	 * Constructs a new Category.
	 * Adds it to the categories-Collection.
	 * @param id The ID the new category should have. Must be unique.
	 * @param name The name of the new category.
	 */
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
		categories.add(this);
	}
	
	/**
	 * Returns the ID of this ID.
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
		return categories;
	}
	
	/**
	 * Returns a collection of the categories with an ID in <code>IDs</code>. 
	 * If there are dublicate IDs all of the categories with that id are returned.
	 * @param IDs A collection of IDs representing the categories that should be returned.
	 * @return A collection of the categories with an ID in <code>IDs</code>.
	 */
	public static Collection<Category> getCategoriesByIDs(Collection<Integer> IDs) {
		Collection<Category> result = Collections.emptyList();
		for (Category c : getAllCategories()) {
			if (IDs.contains(c.getID())) {
				result.add(c);
			}
		}
		return result;
	}
}
