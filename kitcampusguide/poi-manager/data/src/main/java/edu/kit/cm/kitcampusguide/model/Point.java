package edu.kit.cm.kitcampusguide.model;

/**
 * This class specifies one single Point and it's geographic information.
 * 
 * @author Tobias Zündorf
 * 
 */
public class Point extends AEntity {

	/* The longitude of this Point */
	private double longitude;

	/* The latitude of this Point */
	private double latitude;

	/**
	 * Creates a new Point with the specified coordinates.
	 * 
	 * @param x
	 *            the longitude for the new Point
	 * @param y
	 *            the latitude for the new point
	 */
	public Point(double x, double y) {
		this.longitude = x;
		this.latitude = y;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double x) {
		this.longitude = x;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double y) {
		this.latitude = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			Point other = (Point) obj;
			return equal(this.longitude, other.longitude) && equal(this.latitude, other.latitude);
		}
	}

	private boolean equal(double x, double y) {
		return Math.abs(x - y) <= 0E-5;
	}
}
