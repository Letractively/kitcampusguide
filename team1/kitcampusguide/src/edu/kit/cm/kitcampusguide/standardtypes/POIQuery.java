package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a query for a {@link POI} request.
 * Saves the {@link MapSection}, a collection of the {@link Map maps} and a collection of the {@link Category categories} that will be filtered out by.
 * @author fred
 *
 */
public class POIQuery {
	/** Saves the {@link MapSection section} all returned {@link POI POIs} should lie on.*/
	private final MapSection section;
	
	/** Saves a collection of the {@link Map maps} the returned {@link POI POIs} should lie on.*/
	private final Collection<Map> maps;
	
	/** Saves a collection of the {@link Category categories} the returned {@link POI POIs} should lie in.*/
	private final Collection<Category> categories;
	
	/**
	 * Constructs a new <code>POIQuery</code>.
	 * Any of the arguments can be <code>null</code>, then it won't be filtered by.
	 * @param section The {@link MapSection section} that will be filtered by.
	 * @param maps The {@link Map maps} that will be filtered by.
	 * @param categories The {@link Category categories} that will be filtered by.
	 */
	public POIQuery(MapSection section, Collection<Map> maps, Collection<Category> categories) {
		this.section = section;
		this.maps = maps;
		this.categories = categories;
	}
	
	/**
	 * Returns a {@link MapSection map section} by which will be filtered.
	 * @return The {@link MapSection} by which will be filtered. Can return <code>null</code>.
	 */
	public MapSection getSection() {
		return section;
	}
	
	/**
	 * Returns a collection of {@link Map maps} by which will be filtered.
	 * @return A Collection of {@link Map maps} by which will be filtered. Can return <code>null</code>.
	 */
	public Collection<Map> getMaps() {
		return maps;
	}
	
	/**
	 * Returns a collection of {@link Category categories} by which will be filtered. 
	 * @return A collection of {@link Category categories} by which will be filtered. Can return <code>null</code>
	 */
	public Collection<Category> getCategories() {
		return (categories == null) ? null : Collections
				.unmodifiableCollection(categories);
	}
}
