package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a position in the world.
 * Saves the longitude and latitude.
 * @author fred
 *
 */
public class WorldPosition {
	
	/** Saves the longitude of the position.*/
	private final double longitude;
	
	/** Saves the latitude of the position.*/
	private final double latitude;
	
	/**
	 * Constructs a new <code>WorldPosition</code> from the longitude and latitude.
	 * @param longitude The longitude of the position. 
	 * @param latitude The latitude of the position.
	 */
	public WorldPosition(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/**
	 * Returns the latitude of this position. 
	 * @return The latitude of this position.
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Returns the longitude of this position.
	 * @return The longitude of this position.
	 */
	public double getLongitude() {
		return longitude;
	}
}
