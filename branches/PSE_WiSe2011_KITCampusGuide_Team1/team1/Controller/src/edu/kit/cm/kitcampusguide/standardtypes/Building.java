package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a building. 
 * Stores the {@link Map maps} representing the floors, which of these is the ground floor and the {@link POI} representing the building.
 * @author fred
 *
 */
public class Building {
	
	/** Stores the ID of the building.*/
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
			allBuildings.put(new Integer(id), this);
		}
		
	}
	
	/**
	 * Returns the ID of the building.
	 * @return The ID of the building.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the {@link Map maps} representing the floors of the building.
	 * @return The {@link Map maps} representing the floors of the building.
	 */
	public List<Map> getFloors() {
		return Collections.unmodifiableList(floors);
	}
	
	/**
	 * Returns the index of the ground floor in the list gotten by <code>getFloors</code>.
	 * @return The index of the ground floor in the list gotten by <code>getFloors</code>.
	 */
	public int getGroundFloorIndex() {
		return groundfloorIndex;
	}
	
	/**
	 * Returns the {@link POI} representing the building.
	 * @return The {@link POI} representing the building.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}
	
	/**
	 * Returns the building specified by <code>id</code>.
	 * If there are two buildings with <code>id</code>, only one of them is returned. No guarantee of this is given in future versions.
	 * @param id The ID the building given back has.
	 * @return The building specified by <code>id</code> or <code>null</code>, if no such building exists.
	 */
	public static Building getBuildingByID(int id) {
		Building result = null;
		result = allBuildings.get(new Integer(id));
		return result;
	}
}
