package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;

/**
 * Represents a POI.
 * Stores the <code>id</code>, the <code>name</code>, the <code>description</code> and  the {@link WorldPosition} of the POI. Also stores the {@link Map map} the POI lies on, a collection of the {@link Category categories} the POI lies in and the {@link Building building} the POI represents. 
 * @author fred
 *
 */
public class POI {	
	/** Stores the ID of the POI.*/
	private final String id;
	
	/** Stores the name of the POI.*/
	private final String name;
	
	/** Stores the description of the POI.*/
	private final String description;
	
	/** Stores the {@link WorldPosition position} of the POI.*/
	private final WorldPosition position;
	
	/** Stores the {@link Map map} the POI lies on.*/
	private final Map map;
	
	/** Stores the ID of the {@link Building building} represented by the POI or <code>null</code> if the POI doesn't represent a building.*/
	private final Integer buildingID;
	
	/** Stores the {@link Category categories} the POI lies in.*/
	private final Collection<Category> categories;
	
	/**
	 * Constructs a new POI.
	 * @param id The ID the POI has. Should be unique. However, this is not tested.
	 * @param name The name of the POI.
	 * @param description The description of the POI.
	 * @param position The {@link WorldPosition position} of the POI.
	 * @param map The {@link Map map} the POI lies on.
	 * @param buildingID The id of the {@link Building building} represented by the POI or <code>null</code>, if the POI doesn't represent a {@link Building building}.
	 * @param categories A collection of the {@link Category categories} the POI lies in.
	 * 
	 * @throws NullPointerException If either id, name, description, position, map or categories is <code>null</code>.
	 */
	public POI(String id, String name, String description, WorldPosition position, Map map, Integer buildingID, Collection<Category> categories) {
		if (id == null || name == null || description == null || position == null || map == null || categories == null) {
			throw new NullPointerException();
		}
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
	 * Returns the {@link WorldPosition position} of the POI.
	 * @return The {@link WorldPosition position} of the POI.
	 */
	public WorldPosition getPosition() {
		return position;
	}
	
	/**
	 * Returns the {@link Map map} the POI lies on.
	 * @return The {@link Map map} the POI lies on.
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Returns the {@link Building building} the POI represents.
	 * @return The {@link Building building} the POI represents or <code>null</code> if the POI doesn't represent any.
	 */
	public Building getBuildung() {
		return Building.getBuildingByID(buildingID);
	}
	
	/**
	 * Returns a collection of the {@link Category categories} the POI lies in.
	 * @return A collection of the {@link Category categories} the POI lies in.
	 */
	public Collection<Category> getCategories() {
		return categories;
	}
}
