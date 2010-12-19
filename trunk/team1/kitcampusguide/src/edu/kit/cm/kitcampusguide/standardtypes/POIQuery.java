package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

/**
 * Represents a query for a POI request.
 * Saves the <code>MapSection</code>, a collection of the maps and a collection of the categories that will be filtered out by.
 * @author fred
 *
 */
public class POIQuery {
	/** Saves the section all returned POIs should lie on.*/
	private final MapSection section;
	
	/** Saves a collection of the maps the returned POIs should lie on.*/
	private final Collection<Map> maps;
	
	/** Saves a collection of the categories the returned POIs should lie in.*/
	private final Collection<Category> categories;
	
	/**
	 * Constructs a new <code>POIQuery</code>.
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
	 * Returns a section of the map by which will be filtered.
	 * @return The <code>MapSection</code> by which will be filtered. Can return <code>null</code>.
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
