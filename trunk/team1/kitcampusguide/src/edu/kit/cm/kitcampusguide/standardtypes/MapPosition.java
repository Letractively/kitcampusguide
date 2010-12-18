package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a Position on a Map. Extends a WorldPosition.
 * Saves a map additionally to the longitude and latitude saved by WorldPosition.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class MapPosition  extends WorldPosition {
	private final Map map;
	
	/**
	 * Constructs a new MapPosition.
	 * @param longitude The longitude the MapPosition should lie on.
	 * @param latitude The latitude the MapPosition should lie on.
	 * @param map The map the MapPosition should lie on.
	 */
	public MapPosition(double longitude, double latitude, Map map) {
		super(longitude, latitude);
		this.map = map;
	}

	/**
	 * Returns the map the MapPosition lies on.
	 * @return The map the MapPosition lies on.
	 */
	public Map getMap() {
		return map;
	}
}
