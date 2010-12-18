package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.List;

/**
 * Represents a Building. Saves the Maps representing the floors, which of these is the groundfloor and the POI representing the Building
 * @author frederik.diehl@student.kit.edu
 *
 */
public class Building {
	private final int id;
	private final List<Map> floors;
	private final int groundfloorIndex;
	private final POI buildingPOI;
	private static Collection<Building> allBuildings;
	
	/**
	 * Constructs a new Building.
	 * The Building needs the following parameters:
	 * @param id The id of the building, needs to be unique.
	 * @param floors The maps representing the floors from lowest to highest.
	 * @param groundfloorIndex The index of the ground floor in <code>floors</code>.
	 * @param buildingPOI The POI representing the Building.
	 */
	Building(int id, List<Map> floors, int groundfloorIndex, POI buildingPOI) {
		this.id = id;
		this.floors = floors;
		this.groundfloorIndex = groundfloorIndex;
		this.buildingPOI = buildingPOI;
		allBuildings.add(this);
	}
	
	/**
	 * Returns the ID of the Building.
	 * @return The ID of the Building.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the maps representing the floors of the Building.
	 * @return The maps representing the floors of the Building.
	 */
	public List<Map> getFloors() {
		return floors;
	}
	
	/**
	 * Returns the index of the ground floor in the List gotten by <code>getFloors</code>.
	 * @return The index of the ground floor in the List gotten by <code>getFloors</code>.
	 */
	public int getGroundFloorIndex() {
		return groundfloorIndex;
	}
	
	/**
	 * Returns the POI representing the Building.
	 * @return The POI representing the Building.
	 */
	public POI getBuildingPOI() {
		return buildingPOI;
	}
	
	/**
	 * Returns the Building specified by ID.
	 * If there are two Buildings with <code>id</code>, only one of them is returned.
	 * @param id The ID the Building given back has.
	 * @return The Building specified by ID or <code>null</code>, if no such building exists.
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
