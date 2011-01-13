package edu.kit.cm.kitcampusguide.standardtypes;


/**
 * Represents a position in the world.
 * Saves the <code>longitude</code> and <code>latitude</code>.
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
	 * @param latitude The latitude of the position.
	 * @param longitude The longitude of the position. 
	 * 
	 * @throws IllegalArgumentException If either <code>latitude</code> or <code>longitude</code> are out of bounds. 
	 */
	public WorldPosition(double latitude, double longitude) throws IllegalArgumentException {
		if ((latitude > 90) || (latitude < -90) || (longitude > 180) || (longitude < -180)) {
			throw new IllegalArgumentException("Latitude or Langitude out of bounds.");
		}
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
