package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a building. 
 * Stores the {@link Map maps} representing the floors, which of these is the ground floor and the {@link POI} representing the building.
 * A building is unique, so creating a building with the same id leads to an error. Therefore buildings should best be initialized at or from one central
 * source, and afterwards only used with getBuildingByID.
 * @author fred
 *
 */
public class Building implements Serializable {
	
	/** Stores the unique ID of the building.*/
	private final int id;
	
	/** Stores the {@link Map maps} representing the floors from lowest to highest.*/
	private final List<Map> floors;
	
	/** Stores the index of the ground floor in the list saved in <code>floors</code>.*/
	private final int groundfloorIndex;
	
	/** Stores the {@link POI} representing the building.*/
	private final POI buildingPOI;
	
	/** Stores all buildings.*/
	private static HashMap<Integer, Building> allBuildings = new HashMap<Integer, Building>();
	
	/**
	 * Constructs a new building.
	 * The building needs the parameters <code>id</code>, <code>floors</code>, <code>groundfloorIndex</code> and <code>buildingPOI</code>.
	 * @param id The id of the building, needs to be unique.
	 * @param floors The {@link Map maps} representing the floors from lowest to highest.
	 * @param groundfloorIndex The index of the ground floor in <code>floors</code>. Is tested to be in range of <code>floors</code>.
	 * @param buildingPOI The {@link POI} representing the building. Required to be not <code>null</code>.
	 * 
	 * @throws IllegalArgumentException If a duplicate ID is given.
	 */
	Building(int id, List<Map> floors, int groundfloorIndex, POI buildingPOI) throws IllegalArgumentException {
		if (floors == null || buildingPOI == null) {
			throw new NullPointerException("Either floors or buildingPOI is null");
		}
		if (groundfloorIndex >= floors.size() || groundfloorIndex < 0) {
			throw new IllegalArgumentException("groundfloorIndex out of bounds.");
		}
		this.id = id;
		this.floors = new ArrayList<Map>(floors);
		this.groundfloorIndex = groundfloorIndex;
		this.buildingPOI = buildingPOI;
		if (allBuildings.containsKey(new Integer(id))) {
			throw new IllegalArgumentException("Building ID duplicate.");
		} else {
			allBuildings.put(Integer.valueOf(id), this);
		}
		
	}
	
	/**
	 * Returns the ID of the building. This id is unique, and can therefore be used in combination with
	 * {@link getBuildingByID()} to get a specific building.
	 * @return The ID of the building.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the {@link Map maps} representing the floors of the building. This list is ordered from the top floor down.
	 * To identify the ground floor, use {@link getGroundFloorIndex()}, or use getGroundFloor() to get its map directly.
	 * @return The {@link Map maps} representing the floors of the building.
	 */
	public List<Map> getFloors() {
		return Collections.unmodifiableList(floors);
	}
	
	/**
	 * Returns the index of the ground floor in the list gotten by <code>getFloors</code>. Use this in combination with getFloors to identify
	 * the map of the ground floor.
	 * @return The index of the ground floor in the list gotten by <code>getFloors</code>.
	 */
	public int getGroundFloorIndex() {
		return groundfloorIndex;
	}
	
	/**
	 * Returns the {@link POI} representing the building. This POI normally is jumped to (in the view)
	 * show after this building is left. To access the building, use the building-variable in a POI.
	 * @return The {@link POI} representing the building.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}
	
	/**
	 * Returns the building specified by <code>id</code>.
	 * If there are two buildings with <code>id</code>, only one of them is returned. No guarantee of this is given in future versions.
	 * As the id should be unique, this gives the possibility to exactly identify buildings and - provided a consistent source for buildings
	 * exists, store buildings over program starts and shutdowns.
	 * @param id The ID the building given back has.
	 * @return The building specified by <code>id</code> or <code>null</code>, if no such building exists.
	 */
	public static Building getBuildingByID(int id) {
		Building result = null;
		result = allBuildings.get(new Integer(id));
		return result;
	}
	
	/**
	 * Returns the building's ground floor as a {@link Map} object. This should be equivalent to
	 * getFloors().get(getGroundFloorIndex).
	 * @return the building's ground floor
	 */
	public Map getGroundFloor() {
		return floors.get(groundfloorIndex);
	}
}
