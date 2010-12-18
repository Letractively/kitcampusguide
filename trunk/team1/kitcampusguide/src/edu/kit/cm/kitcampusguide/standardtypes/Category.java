package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Category {
	private final int id;
	private final String name;
	private static List<Category> categories;
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
		categories.add(this);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static Collection<Category> getAllCategories() {
		return categories;
	}
	
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
