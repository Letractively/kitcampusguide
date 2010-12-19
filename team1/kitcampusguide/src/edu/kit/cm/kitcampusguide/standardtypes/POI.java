package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

/**
 * Represents a POI.
 * Saves the id, the name, the description and  the position of the POI. Also stores the map the POI lies on and a collection of the categories the POI lies in. 
 * @author fred
 *
 */
public class POI {
	
	/** Saves the ID of the POI.*/
	private final String id;
	
	/** Saves the name of the POI.*/
	private final String name;
	
	/** Saves the description of the POI.*/
	private final String description;
	
	/** Saves the position of the POI.*/
	private final WorldPosition position;
	
	/** Saves the map the POI lies on.*/
	private final Map map;
	
	/** Saves the ID of the building represented by the POI or <code>null</code> if the POI doesn't represent a building.*/
	private final Integer buildingID;
	
	/** Saves the categories the POI lies in.*/
	private final Collection<Category> categories;
	
	/**
	 * Constructs a new POI.
	 * @param id The id the POI has. Should be unique.
	 * @param name The name of the POI.
	 * @param description The description of the POI.
	 * @param position The position of the POI.
	 * @param map The map the POI lies on.
	 * @param buildingID The id of the building represented by the POI or <code>null</code>, if the POI doesn't represent a building.
	 * @param categories A collection of the categories the POI lies in.
	 */
	public POI(String id, String name, String description, WorldPosition position, Map map, int buildingID, Collection<Category> categories) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.position = position;
		this.map = map;
		this.buildingID = buildingID;
		this.categories = categories;
	}
	
	/**
	 * Returns the ID of the POI.
	 * @return The ID of the POI.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Returns the name of the POI. 
	 * @return The name of the POI.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the description of the POI.
	 * @return The description of the POI.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the position of the POI.
	 * @return The Position of the POI.
	 */
	public WorldPosition getPosition() {
		return position;
	}
	
	/**
	 * Returns the map the POI lies on.
	 * @return The map the POI lies on.
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Returns the building the POI represents.
	 * @return The building the POI represents or <code>null</code> if the POI doesn't represent any.
	 */
	public Building getBuildung() {
		return Building.getBuildingByID(buildingID);
	}
	
	/**
	 * Returns a collection of the categories the POI lies in.
	 * @return A collection of the categories the POI lies in.
	 */
	public Collection<Category> getCategories() {
		return categories;
	}
}
