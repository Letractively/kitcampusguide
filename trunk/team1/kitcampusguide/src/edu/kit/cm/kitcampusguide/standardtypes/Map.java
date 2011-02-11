package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a map.
 * A map will be used for all floors in each building and the campus map.
 * Each map will be represented by exactly one map-object.
 * Therefore, maps should be constructed either from a consistent source or all at once.
 * If using a consistent source, the map ids can be used to identify the map over the start and end of the program itself.
 * Each map will be saved in the collection of maps once the constructor is used successfully.
 * Stores a unique id, a name, the path to the tiles, a bounding box and the minimal and maximal zoom level.
 * The id is used to identify a map by using getMapByID.
 * The path is used to identify the path of the map tiles used by open layers to render the map.
 * The bounding box is used to mark an box, over which the map can't be dragged. 
 * The minZoom and maxZoom is used to define a border for the zoom.
 * 
 * @author fred
 *
 */
public class Map implements Serializable {

	/** Stores the id of the map.*/
	private int id;
	
	/** Stores the name of the map*/
	private String name;
	
	/** Stores the bounding box of the map*/
	private MapSection boundingBox;
	
	/** Stores the URL leading to the tiles*/
	private String tilesURL;
	
	/** Stores the minimal zoom level of the map.*/
	private int minZoom;
	
	/** Stores the maximal zoom level of the map.*/
	private int maxZoom;
	
	/** Stores all maps.*/
	private static java.util.Map<Integer, Map> maps = new HashMap<Integer, Map>();
	
	
	
	/**
	 * Creates a new map.
	 * Once this constructor has been successfully called, the map is added to the list of all maps and can later be retrieved by getMapByID. 
	 * @param id ID of the new map. Is required to be unique for a correct result. However, this is not tested.
	 * @param name Name of the map. Required not to be <code>null</code>.
	 * @param boundingBox The boundaries of this map. Required not to be <code>null</code>.
	 * @param tilesURL The path to the files.
	 * @param minZoom The minimal zoom level for this map. Required to be >= 0. Required to be <= <code>maxZoom</code>.
	 * @param maxZoom The maximal zoom level for this map. Required to be >= 0. Required to be >= <code>minZoom</code>.
	 * 
	 * @throws NullPointerException If either name, boundingBox or tilesURL is null.
	 * @throws IllegalArgumentException if minZoom > maxZoom or map with a duplicate id would be created.  
	 */
	public Map(int id, String name, MapSection boundingBox, String tilesURL, int minZoom, int maxZoom) throws NullPointerException, IllegalArgumentException {
		if (name == null || boundingBox == null || tilesURL == null) {
			throw new NullPointerException();
		}
		if (minZoom > maxZoom) {
			throw new IllegalArgumentException("Map with ID " + id + ": minZoom > maxZoom.");
		}
		if (maps.containsKey(new Integer(id))) {
			throw new IllegalArgumentException("Map with ID " + id + " already exists.");
		}
		this.id = id;
		this.name = name;
		this.boundingBox = boundingBox;
		this.tilesURL = tilesURL;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		maps.put(new Integer(id), this);
	}
	
	/**
	 * Returns the ID of this map. This id is unique. Can be used in combination with getMapByID. 
	 * @return the ID of this map.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this map. This name is displayed to the user p.ex. when changing floors.
	 * @return the name of this map.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the bounding box of the map. This box is used to define how far the map can be dragged.
	 * @return The bounding box of the map.
	 */
	public MapSection getBoundingBox() {
		return boundingBox;
	}
	
	/**
	 * Returns the URL of the tiles. This URL is used to identify the tiles open layers uses to draw the map. 
	 * @return The URL of the tiles.
	 */
	public String getTilesURL() {
		return tilesURL;
	}
	
	/**
	 * Returns the minimal zoom level. This is used by open layers.
	 * @return The minimal zoom level.
	 */
	public int getMinZoom() {
		return minZoom;
	}
	
	/**
	 * Returns the maximal zoom level. This is used by open layers.
	 * @return The maximal zoom level.
	 */
	public int getMaxZoom() {
		return maxZoom;
	}
	
	/**
	 * Returns the map with the ID <code>id</code>. 
	 * If there are two maps with this id, only one is returned. However, no guarantee of this is given for future versions.
	 * Can be used with getID.
	 * @param id The map with this ID will be returned.
	 * @return A map with the ID <code>id</code> or <code>null</code> if no such map exists.
	 */
	public static Map getMapByID(int id) {
		Map result = null;
		result = maps.get(new Integer(id));
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		return ((Map)obj).id == this.id;
	}
}
