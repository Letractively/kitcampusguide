package edu.kit.cm.kitcampusguide.standardtypes;


/**
 * Represents a position on a {@link Map map}. Extends {@link WorldPosition}.
 * Saves a {@Map map} additionally to the longitude and latitude saved by {@link WorldPosition}.
 * @author fred
 *
 */
public class MapPosition  extends WorldPosition {	
	/** Saves the {@link Map map} the position lies on.*/
	private final Map map;
	
	/**
	 * Constructs a new <code>MapPosition</code>.
	 * @param longitude The longitude of the position.
	 * @param latitude The latitude of the position.
	 * @param map The {@link Map map} the position lies on.
	 */
	public MapPosition(double longitude, double latitude, Map map) {
		super(longitude, latitude);
		this.map = map;
	}

	/**
	 * Returns the {@link Map map} the position lies on.
	 * @return The {@link Map map} the position lies on.
	 */
	public Map getMap() {
		return map;
	}
}
