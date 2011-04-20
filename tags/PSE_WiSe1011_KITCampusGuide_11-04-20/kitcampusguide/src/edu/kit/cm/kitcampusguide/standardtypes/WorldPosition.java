package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;

/**
 * Represents a position in the world. Saves the <code>longitude</code> and
 * <code>latitude</code>.
 * Positions representing the same longitude and latitude don't need to be the same object, rather,
 * due to longitude and latitude being double, it's better to create a new WorldPosition each time it is needed.
 * @author fred
 * 
 */
public class WorldPosition implements Serializable {
	
	
	private static final double EPS = 1e-7;

	/** Saves the longitude of the position. */
	private final double longitude;

	/** Saves the latitude of the position. */
	private final double latitude;

	/**
	 * Constructs a new <code>WorldPosition</code> from the longitude and
	 * latitude.
	 * 
	 * @param latitude
	 *            The latitude of the position.
	 * @param longitude
	 *            The longitude of the position.
	 * 
	 * @throws IllegalArgumentException
	 *             If either <code>latitude</code> or <code>longitude</code> are
	 *             out of bounds, see {@link #checkBounds}
	 */
	public WorldPosition(double latitude, double longitude)
			throws IllegalArgumentException {
		checkBounds(latitude, longitude);
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Checks if two given coordinates specify a valid position. That means that
	 * latitude is in the range of -90° and +90°, the longitude needs to be
	 * between -180° and +180°.
	 * 
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @return <code>true</code> if the given coordinates specify a valid
	 *         position.
	 */
	public static boolean checkBounds(double latitude, double longitude) {
		return (latitude <= 90) && (latitude >= -90) && (longitude <= 180)
				&& (longitude >= -180);
	}

	/**
	 * Returns the latitude of this position.
	 * 
	 * @return The latitude of this position.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Returns the longitude of this position.
	 * 
	 * @return The longitude of this position.
	 */
	public double getLongitude() {
		return longitude;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(this.getClass() == obj.getClass())) {
			return false;
		}
		return(equals((WorldPosition) obj));
	}
	
	protected boolean equals(WorldPosition other) {
		if (other == null) {
			return false;
		}
		return (other == this) || (Math.abs(other.latitude - this.latitude) < EPS
				&& Math.abs(other.longitude - this.longitude) < EPS);
	}
	
	/**
	 * Calculates the distance between the positions pos1 and pos2.
	 * @param pos1 The first position.
	 * @param pos2 The second position.
	 * @return The distance between pos1 and pos2 in meters.
	 */
	public static double calculateDistance(WorldPosition pos1,
			WorldPosition pos2) {
		double lat1 = Math.toRadians(pos1.getLatitude());
		double lat2 = Math.toRadians(pos2.getLatitude());
		double lon1 = Math.toRadians(pos1.getLongitude());
		double lon2 = Math.toRadians(pos2.getLongitude());
		double result = 6370000 * Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
		return result;
	}

}
