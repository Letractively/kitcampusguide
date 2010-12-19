package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a position on a map. Extends <code>WorldPosition</code>.
 * Saves a map additionally to the longitude and latitude saved by <code>WorldPosition</code>.
 * @author fred
 *
 */
public class MapPosition  extends WorldPosition {
	
	/** Saves the map the position lies on.*/
	private final Map map;
	
	/**
	 * Constructs a new <code>MapPosition</code>.
	 * @param longitude The longitude of the position.
	 * @param latitude The latitude of the position.
	 * @param map The map the position lies on.
	 */
	public MapPosition(double longitude, double latitude, Map map) {
		super(longitude, latitude);
		this.map = map;
	}

	/**
	 * Returns the map the position lies on.
	 * @return The map the position lies on.
	 */
	public Map getMap() {
		return map;
	}
}
