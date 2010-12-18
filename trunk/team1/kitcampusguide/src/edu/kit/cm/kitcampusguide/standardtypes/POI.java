package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

public class POI {
	private final String id;
	private final String name;
	private final String description;
	private final WorldPosition position;
	private final Map map;
	private final int buildingID;
	private final Collection<Category> categories;
	
	public POI(String id, String name, String description, WorldPosition position, Map map, int buildingID, Collection<Category> categories) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.position = position;
		this.map = map;
		this.buildingID = buildingID;
		this.categories = categories;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public WorldPosition getPosition() {
		return position;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Building getBuildung() {
		return Building.getBuildingByID(buildingID);
	}
	
	public Collection<Category> getCategories() {
		return categories;
	}
}
