package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.Collection;
import java.util.List;

public class Building {
	private final int id;
	private final List<Map> floors;
	private final int groundfloorIndex;
	private final POI buildingPOI;
	private static Collection<Building> allBuildings;
	
	Building(int id, List<Map> floors, int groundfloorIndex, POI buildingPOI) {
		this.id = id;
		this.floors = floors;
		this.groundfloorIndex = groundfloorIndex;
		this.buildingPOI = buildingPOI;
		allBuildings.add(this);
	}
	
	public int getID() {
		return id;
	}
	
	public List<Map> getFloors() {
		return floors;
	}
	
	public int getGroundFloorIndex() {
		return groundfloorIndex;
	}
	
	public POI getBuildingPOI() {
		return buildingPOI;
	}
	
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
