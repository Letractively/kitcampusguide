package edu.kit.cm.kitcampusguide.standardtypes;


/**
 * Represents a position on a {@link Map map}. Extends {@link WorldPosition}.
 * Stores a {@Map map} additionally to the longitude and latitude stored by {@link WorldPosition}.
 * @author fred
 *
 */
public class MapPosition  extends WorldPosition {	
	/** Saves the {@link Map map} the position lies on.*/
	private final Map map;
	
	/**
	 * Constructs a new <code>MapPosition</code>.
	 * @param latitude The latitude of the position.
	 * @param longitude The longitude of the position.
	 * @param map The {@link Map map} the position lies on.
	 * 
	 * @throws IllegalArgumentException If either latitude or longitude are out of bounds.
	 */
	public MapPosition(double latitude, double longitude, Map map) throws IllegalArgumentException {
		super(latitude, longitude);
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
