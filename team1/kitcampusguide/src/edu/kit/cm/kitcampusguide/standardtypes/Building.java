package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.List;

/**
 * Represents a building. 
 * Saves the maps representing the floors, which of these is the ground floor and the POI representing the building
 * @author fred
 *
 */
public class Building {
	/** Saves the ID of the building.*/
	private final int id;
	
	/** Saves the floors from lowest to highest.*/
	private final List<Map> floors;
	
	/** Saves the index of the ground floor in the list saved in <code>floors</code>.*/
	private final int groundfloorIndex;
	
	/** Saves the POI representing the building.*/
	private final POI buildingPOI;
	
	/** Saves a collection of all buildings.*/
	private static Collection<Building> allBuildings;
	
	/**
	 * Constructs a new building.
	 * The building needs the following parameters:
	 * @param id The id of the building, needs to be unique.
	 * @param floors The maps representing the floors from lowest to highest.
	 * @param groundfloorIndex The index of the ground floor in <code>floors</code>.
	 * @param buildingPOI The POI representing the building.
	 */
	Building(int id, List<Map> floors, int groundfloorIndex, POI buildingPOI) {
		this.id = id;
		this.floors = floors;
		this.groundfloorIndex = groundfloorIndex;
		this.buildingPOI = buildingPOI;
		allBuildings.add(this);
	}
	
	/**
	 * Returns the ID of the building.
	 * @return The ID of the building.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the maps representing the floors of the building.
	 * @return The maps representing the floors of the building.
	 */
	public List<Map> getFloors() {
		return floors;
	}
	
	/**
	 * Returns the index of the ground floor in the list gotten by <code>getFloors</code>.
	 * @return The index of the ground floor in the list gotten by <code>getFloors</code>.
	 */
	public int getGroundFloorIndex() {
		return groundfloorIndex;
	}
	
	/**
	 * Returns the POI representing the building.
	 * @return The POI representing the building.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}
	
	/**
	 * Returns the building specified by <code>id</code>.
	 * If there are two buildings with <code>id</code>, only one of them is returned. No guarantee of this is given in future versions.
	 * @param id The ID the building given back has.
	 * @return The building specified by ID or <code>null</code>, if no such building exists.
	 */
	public static Building getBuildingByID(int id) {
		Building result = null;
		for(Building b : allBuildings) {
			if (b.getID() == id) {
				result = b;
				break;
			}
		}
		return result;
	}
}
