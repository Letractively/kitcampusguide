package edu.kit.cm.kitcampusguide.model;

/**
 * This class specifies one single Point and it's geographic information.
 * 
 * @author Tobias Zündorf
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class Point extends AbstractEntity implements Entity {

	/* The longitude of this Point */
	private Double longitude;

	/* The latitude of this Point */
	private Double latitude;

	/**
	 * Creates a new Point with the specified coordinates.
	 * 
	 * @param longitude
	 *            the longitude for the new Point
	 * @param latitude
	 *            the latitude for the new point
	 */
	public Point(Double longitude, Double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double x) {
		this.longitude = x;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double y) {
		this.latitude = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			Point other = (Point) obj;
			return equal(this.longitude, other.longitude)
					&& equal(this.latitude, other.latitude);
		}
	}

	private boolean equal(Double x, Double y) {
		return Math.abs(x - y) <= 0E-5;
	}
}
