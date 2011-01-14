package edu.kit.cm.kitcampusguide.model;

/**
 * This class specifies one single Point and it's geographic information.
 * 
 * @author Tobias Zündorf
 *
 */
public class Point {

	/* The longitude of this Point */
	private final double x;
	
	/* The latitude of this Point */
	private final double y;
	
	/**
	 * Creates a new Point with the specified coordinates.
	 * 
	 * @param x the longitude for the new Point
	 * @param y the latitude for the new point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the longitude of the current Point.
	 * 
	 * @return the longitude of the current Point
	 */
	public double getX() {
		return x;
	}


	/**
	 * Returns the latitude of the current Point.
	 * 
	 * @return the latitude of the current Point
	 */
	public double getY() {
		return y;
	}
	
}
