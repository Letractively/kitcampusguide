package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.List;

/**
 * Represents a Map.
 * Consists of an id and a name.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class Map {
	private int id;
	private String name;
	private static List<Map> maps;
	
	/**
	 * Creates a new <code>Map</code>.
	 * @param id ID of the new Map. Is required to be unique for a correct result.
	 * @param name Name of the Map.
	 */
	public Map(int id, String name) {
		this.id = id;
		this.name = name;
		maps.add(this);
	}
	
	/**
	 * Returns the ID of this Map.
	 * @return the ID of this Map.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the Name of this Map.
	 * @return the Name of this Map.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the Map with the id id. If there are two Maps with this id, only one is returned.
	 * @param id The Map with this ID will be returned.
	 * @return A Map with the ID id or <code>null</code> if no such Map exists.
	 */
	public static Map getMapByID(int id) {
		Map result = null;
		for (int i = 0; i < maps.size(); i++) {
			if (maps.get(i).id == id) {
				result = maps.get(i);
				break;
			}
		}
		return result;
	}
	
}
