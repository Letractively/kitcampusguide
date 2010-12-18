package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

public class POIQuery {
	private final MapSection section;
	private final Collection<Map> maps;
	private final Collection<Category> categories;
	
	public POIQuery(MapSection section, Collection<Map> maps, Collection<Category> categories) {
		this.section = section;
		this.maps = maps;
		this.categories = categories;
	}
	
	public MapSection getSection() {
		return section;
	}
	
	public Collection<Map> getMaps() {
		return maps;
	}
	
	public Collection<Category> getCategories() {
		return categories;
	}
}
