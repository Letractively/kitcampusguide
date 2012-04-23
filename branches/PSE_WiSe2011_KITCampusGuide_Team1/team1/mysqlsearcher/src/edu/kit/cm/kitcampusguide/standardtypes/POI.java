package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a POI.
 * A POI can be any interesting point.
 * POIs are uniquely defined.
 * It is recommended to initialize the POIs from a single source or a single class to avoid discrepancy.
 * Stores the <code>id</code>, the <code>name</code>, the <code>description</code> and  the {@link WorldPosition} of the POI. Also stores the {@link Map map} the POI lies on, a collection of the {@link Category categories} the POI lies in and the {@link Building building} the POI represents.
 * The id is unique and used to identify the POIs. If constructed from a consistent source, it can be used over the start and shutdown of the program.
 * The name is shown to the user. It can be not-unique.
 * The description is shown to the user to describe the POI.
 * The WorldPosition of the POI is where the POI lies.
 * The map is the map the POI lies on.
 * The category-collection saved is the collection of all categories the POI lies in. It can be filtered by these. 
 * The building the POI represents. Each building should be identified by exactly one POI. The options for changing into that building and showing the POIs in that building will then be accessible via this POIs context menue.
 * @author fred
 *
 */
public class POI implements Serializable {	
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
	 * To avoid discrepancies it is recommended to construct POIs from a consistent source and/or a single class.
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
		if (id == null || name == null || position == null || map == null || categories == null) {
			throw new NullPointerException();
		}
		this.id = id;
		this.name = name;
		this.description = description;
		this.position = position;
		this.map = map;
		this.buildingID = buildingID;
		this.categories = new ArrayList<Category>(categories);
	}
	
	/**
	 * Returns the ID of the POI. It can be used to identify the POI and - if constructed from a consistent source - identify them between start and shutdown of the program.
	 * Used p.ex. to identify the building POI for each building.
	 * @return The ID of the POI.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Returns the name of the POI. This is displayed to the user.
	 * @return The name of the POI.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the description of the POI. This is displayed to the user. Can contain html-code.
	 * @return The description of the POI.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the {@link WorldPosition position} of the POI. This is used to print the POI via open layers.
	 * @return The {@link WorldPosition position} of the POI.
	 */
	public WorldPosition getPosition() {
		return position;
	}
	
	/**
	 * Returns the {@link Map map} the POI lies on. The POI is only displayed if it lies on the same map. Also used to identify which POIs to display if the POIs inside a building are displayed.
	 * @return The {@link Map map} the POI lies on.
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Returns the {@link Building building} the POI represents. 
	 * In this POIs context menu the option for changing into that building and to display the POIs inside that building will be availabe.
	 * @return The {@link Building building} the POI represents or <code>null</code> if the POI doesn't represent any.
	 */
	public Building getBuilding() {
		return (buildingID == null) ? null : Building.getBuildingByID(buildingID);
	}
	
	/**
	 * Returns a collection of the {@link Category categories} the POI lies in. Used to filter.
	 * @return A collection of the {@link Category categories} the POI lies in.
	 */
	public Collection<Category> getCategories() {
		return categories;
	}
}
