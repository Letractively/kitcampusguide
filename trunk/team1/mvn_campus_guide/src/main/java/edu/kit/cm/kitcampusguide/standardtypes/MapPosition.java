package edu.kit.cm.kitcampusguide.standardtypes;


/**
 * Represents a position on a {@link Map}. Extends {@link WorldPosition}.
 * Stores a {@Map map} additionally to the longitude and latitude stored by {@link WorldPosition}.
 * Positions representing the same longitude and latitude on the same map don't need to be the same object, rather,
 * due to longitude and latitude being double, it's better to create a new MapPosition each time it is needed.
 * @author fred
 *
 */
public class MapPosition  extends WorldPosition {	
	/** Saves the {@link Map} the position lies on.*/
	private final Map map;
	
	/**
	 * Constructs a new <code>MapPosition</code>.
	 * @param latitude The latitude of the position.
	 * @param longitude The longitude of the position.
	 * @param map The {@link Map} the position lies on.
	 * 
	 * @throws IllegalArgumentException If either latitude or longitude are out of bounds.
	 */
	public MapPosition(double latitude, double longitude, Map map) throws IllegalArgumentException {
		super(latitude, longitude);
		this.map = map;
	}

	/**
	 * Returns the {@link Map} the position lies on.
	 * @return The {@link Map} the position lies on.
	 */
	public Map getMap() {
		return map;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		MapPosition other = (MapPosition) obj;
		return (other == this)
				|| (super.equals(other) && other.map.equals(this.map));
	}

	/**
	 * Calculates the distance between to given MapPositions. If the positions
	 * are placed on different maps, <code>Double.MAX_VALUE</code> is returned.
	 * Otherwise the distance in meters as calculated in
	 * {@link WorldPosition#calculateDistance(WorldPosition, WorldPosition)} is
	 * returned.
	 * 
	 * @param pos1
	 *            a <code>MapPosition</code>
	 * @param pos2
	 *            a <code>MapPosition</code>
	 * @return the distance between the two given points.
	 */
	public static double calculateDistance(MapPosition pos1, MapPosition pos2) {
		return (pos1.map.equals(pos2.map)) ? WorldPosition.calculateDistance(
				pos1, pos2) : Double.POSITIVE_INFINITY;
	}
}
