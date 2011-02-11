package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents categories.
 * Stores the respective <code>id</code>, the <code>name</code> and gives functions to get categories.
 * A category is used to both identify the type of a POI (p.ex. office or beverage machine), and to later
 * filter by it. A category is only saved in the POIs and does (apart from id and name and all categories) not save any data.
 * The list of categories saved in the categories is used to identify all existing categories.
 * Each category (identified by the id) is represented by exactly one object. Therefore it is advised to initialize all
 * categories either at once or from a single source. Initializing them this way can be done easily by calling the constructor, without any
 * other actions necessary.
 * If a persistent source is used for the categories, the IDs can be preserved over start and shutdown of the program.
 * @author fred
 *
 */
public class Category implements Serializable {	
	/** Stores the id of the category*/
	private final int id;
	
	/** Stores the name of the category*/
	private final String name;
	
	/** Stores all categories.*/
	private static Map<Integer, Category> categories = new HashMap<Integer, Category>();
	
	/**
	 * Constructs a new category.
	 * Adds it to the collection of categories. Therefore it just needs to be called once to initialize
	 * a category with the given id and name, which can later be gotten via the function
	 * getCategoriesByIDs or getAllCategories.
	 * @param id The ID the new category should have. Must be unique. However, this is not tested.
	 * @param name The name of the new category.
	 * 
	 * @throws NullPointerException If the name of the category is null.
	 * @throws IllegalArgumentException If the category created would have a duplicate ID.
	 */
	public Category(int id, String name) throws IllegalArgumentException, NullPointerException {
		if (name == null) {
			throw new NullPointerException("Name of categorie " + id + " is null");
		}
		if (categories.containsKey(new Integer(id))) {
				throw new IllegalArgumentException("Category with ID " + id + " already exists.");
			}
		this.id = id;
		this.name = name;
		categories.put(new Integer(id), this);
	}
	
	/**
	 * Returns the ID of this category. This can be used to identify the category in combination
	 * with getCategoriesByIDs.
	 * @return The ID of this category.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this category. This name is displayed to the user. The name doesn't need to be unique.
	 * @return The name of this category.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a collection of all categories. This categories are all for which the constructor has been successfully called.
	 * @return A collection containing all categories.
	 */
	public static Collection<Category> getAllCategories() {
		return Collections.unmodifiableCollection(categories.values());
	}
	
	/**
	 * Returns a collection of the categories with an ID in <code>ids</code>. 
	 * If there are duplicate IDs all of the categories with that id are returned. However no guarantee is given for that in future versions.
	 * All categories for which the constructor has been successfully called will be taken into account for this list.
	 * Should best be used in combination with getID.  
	 * @param ids A collection of IDs representing the categories that should be returned.
	 * @return A collection of the categories with an ID in <code>ids</code>.
	 */
	public static Collection<Category> getCategoriesByIDs(Collection<Integer> ids) {
		Collection<Category> result = new ArrayList<Category>(ids.size());
		for (Integer i : ids) {
			if (categories.containsKey(i)) {
				result.add(categories.get(i));
			}
		}
		return result;
	}
}
