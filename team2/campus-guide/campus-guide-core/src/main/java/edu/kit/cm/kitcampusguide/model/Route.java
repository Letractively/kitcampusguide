package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class contains a set of points on the map, which describe a path from
 * one point to the other.
 * 
 * @author Monica Haurilet
 * 
 */
public class Route {

	private final List<Point> routePoints;

	/**
	 * This constructor creates a new route consisting of the specified points.
	 * The route starts at the point with index 0 and ends with the index
	 * size-1.
	 * 
	 * @param routePoints
	 *            is a set of points which describe a route.
	 */
	public Route(Collection<Point> routePoints) {
		this.routePoints = new ArrayList<Point>(routePoints);
	}

	/**
	 * 
	 * Returns a set of points which describe a route on the map.
	 * 
	 * @return the route from one point to another on the map.
	 */
	public List<Point> getRoute() {
		return this.routePoints;
	}

}
