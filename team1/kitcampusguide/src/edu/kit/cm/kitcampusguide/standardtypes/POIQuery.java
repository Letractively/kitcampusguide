package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

/**
 * Represents a query for a POI-Request.
 * Saves the MapSection, a collection of the Maps and a collection of the categories that will be filtered out by.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class POIQuery {
	private final MapSection section;
	private final Collection<Map> maps;
	private final Collection<Category> categories;
	
	/**
	 * Constructs a new POIQuery.
	 * Any of the arguments can be <code>null</code>, then it won't be filtered by.
	 * @param section The section that will be filtered by.
	 * @param maps The maps that will be filtered by.
	 * @param categories The categories that will be filtered by.
	 */
	public POIQuery(MapSection section, Collection<Map> maps, Collection<Category> categories) {
		this.section = section;
		this.maps = maps;
		this.categories = categories;
	}
	
	/**
	 * Returns the MapSection by which will be filtered.
	 * @return The MapSection by which will be filtered. Can return <code>null</code>.
	 */
	public MapSection getSection() {
		return section;
	}
	
	/**
	 * Returns a collection of maps by which will be filtered.
	 * @return Collection of maps by which will be filtered. Can return <code>null</code>.
	 */
	public Collection<Map> getMaps() {
		return maps;
	}
	
	/**
	 * Returns a collection of categories by which will be filtered. 
	 * @return A collection of categories by which will be filtered. Can return <code>null</code>
	 */
	public Collection<Category> getCategories() {
		return categories;
	}
}
