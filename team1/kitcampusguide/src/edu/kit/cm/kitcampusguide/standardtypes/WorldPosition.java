package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;

/**
 * Represents a position in the world. Saves the <code>longitude</code> and
 * <code>latitude</code>.
 * 
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
	 *             out of bounds.
	 */
	public WorldPosition(double latitude, double longitude)
			throws IllegalArgumentException {
		if ((latitude > 90) || (latitude < -90) || (longitude > 180)
				|| (longitude < -180)) {
			throw new IllegalArgumentException(
					"Latitude or Langitude out of bounds.");
		}
		this.longitude = longitude;
		this.latitude = latitude;
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
	 * @return The distance between pos1 and pos2.
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
