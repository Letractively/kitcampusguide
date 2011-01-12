package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.ArrayList;
import java.util.Collection;

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
	
	/** Saves the bounding box of the map*/
	private MapSection boundingBox;
	
	/** Saves the URL leading to the tiles*/
	private String tilesURL;
	
	/** Saves the minimal zoom level of the map.*/
	private int minZoom;
	
	/** Saves the maximal zoom level of the map.*/
	private int maxZoom;
	
	/** Saves a collection of all maps.*/
	private static Collection<Map> maps = new ArrayList<Map>();
	
	
	
	/**
	 * Creates a new map.
	 * @param id ID of the new map. Is required to be unique for a correct result. However, this is not tested.
	 * @param name Name of the map. Required not to be <code>null</code>.
	 * @param boundingBox The boundaries of this map. Required not to be <code>null</code>.
	 * @param tilesURL The path to the files.
	 * @param minZoom The minimal zoom level for this map. Required to be >= 0. Required to be <= <code>maxZoom</code>.
	 * @param maxZoom The maximal zoom level for this map. Required to be >= 0. Required to be >= <code>minZoom</code>.
	 */
	public Map(int id, String name, MapSection boundingBox, String tilesURL, int minZoom, int maxZoom) {
		if (name == null || boundingBox == null || tilesURL == null) {
			throw new NullPointerException();
		}
		if (minZoom > maxZoom) {
			throw new IllegalArgumentException("Map with ID " + id + ": minZoom > maxZoom.");
		}
		for (Map map: maps) {
			if (map.getID() == id) {
				throw new IllegalArgumentException("Map with ID " + id + " already exists.");
			}
		}
		this.id = id;
		this.name = name;
		this.boundingBox = boundingBox;
		this.tilesURL = tilesURL;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
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
	 * Returns the bounding box of the map.
	 * @return The bounding box of the map.
	 */
	public MapSection getBoundingBox() {
		return boundingBox;
	}
	
	/**
	 * Returns the URL of the tiles.
	 * @return The URL of the tiles.
	 */
	public String getTilesURL() {
		return tilesURL;
	}
	
	/**
	 * Returns the minimal zoom level.
	 * @return The minimal zoom level.
	 */
	public int getMinZoom() {
		return minZoom;
	}
	
	/**
	 * Returns the maximal zoom level.
	 * @return The maximal zoom level.
	 */
	public int getMaxZoom() {
		return maxZoom;
	}
	
	/**
	 * Returns the map with the ID <code>id</code>. 
	 * If there are two maps with this id, only one is returned. However, no guarantee of this is given for future versions.
	 * @param id The map with this ID will be returned.
	 * @return A map with the ID <code>id</code> or <code>null</code> if no such map exists.
	 */
	public static Map getMapByID(int id) {
		Map result = null;
		for (Map momMap : maps) {
			if (momMap.getID() == id) {
				result = momMap;
				break;
			}
		}
		return result;
	}
}
