package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.List;

/**
 * Represents a map.
 * Saves an id and a name.
 * @author fred
 *
 */
public class Map {
	
	/** Saves the id of the map.*/
	private int id;
	
	/** Saves the name of the map*/
	private String name;
	
	/** Saves a list of all maps.*/
	private static List<Map> maps;
	
	/**
	 * Creates a new map.
	 * @param id ID of the new map. Is required to be unique for a correct result.
	 * @param name Name of the map.
	 */
	public Map(int id, String name) {
		this.id = id;
		this.name = name;
		maps.add(this);
	}
	
	/**
	 * Returns the ID of this map.
	 * @return the ID of this map.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this map.
	 * @return the name of this map.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the map with the ID <code>id</code>. 
	 * If there are two maps with this id, only one is returned. However, no guarantee of this is given for future versions.
	 * @param id The map with this ID will be returned.
	 * @return A map with the ID <code>id</code> or <code>null</code> if no such map exists.
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
