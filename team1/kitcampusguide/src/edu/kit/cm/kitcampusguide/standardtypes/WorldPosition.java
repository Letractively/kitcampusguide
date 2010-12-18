package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a Position in the World.
 * Saves the longitude and latitude.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class WorldPosition {
	private final double longitude;
	private final double latitude;
	
	/**
	 * Constructs a new WorldPosition from the longitude and latitude.
	 * @param longitude The longitude the WorldPosition lies on. 
	 * @param latitude The latitude the WorldPosition lies on.
	 */
	public WorldPosition(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/**
	 * Returns the latitude the WorldPosition lies on. 
	 * @return The latitude the WorldPosition lies on.
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Returns the longitude the WorldPosition lies on.
	 * @return The longitude the WorldPosition lies on.
	 */
	public double getLongitude() {
		return longitude;
	}
}
