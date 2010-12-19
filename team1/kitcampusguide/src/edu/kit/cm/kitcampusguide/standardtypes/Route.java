package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.List;

/**
 * Represents a route.
 * Saves the waypoints defining the route and a bounding box.
 * @author fred
 *
 */
public class Route {
	
	/** Saves the position the route starts at.*/
	private final MapPosition start;
	
	/** Saves the position the route ends at.*/
	private final MapPosition end;
	
	/** Saves the waypoints defining the route*/
	private final List<MapPosition> waypoints;
	
	/** Saves the bounding box the route lies in.*/
	private final MapSection boundingBox;
	
	/**
	 * Constructs a new route.
	 * @param waypoints The MapPositions defining the route.
	 */
	public Route(List<MapPosition> waypoints) {
		this.waypoints = waypoints;
		this.start = waypoints.get(0);
		this.end = waypoints.get(waypoints.size() - 1);
		this.boundingBox = calculateBoundingBox();
	}
	
	/**
	 * Returns the MapPosition at which the route begins.
	 * @return The MapPosition at which the route begins.
	 */
	public MapPosition getStart() {
		return start;
	}
	
	/**
	 * Returns the MapPosition at which the route ends.
	 * @return The MapPosition at which the route ends.
	 */
	public MapPosition getEnd() {
		return end;
	}
	
	/**
	 * Returns a list of MapPositions defining the route.
	 * @return A list of MapPositions defining the route.
	 */
	public List<MapPosition> getWaypoints() {
		return waypoints;
	}
	
	/**
	 * Returns the bounding box of the route.
	 * @return The bounding box of the route.
	 */
	public MapSection getBoundingBox() {
		return boundingBox;
	}

	/**
	 * Constructs a bounding box from the waypoints already saved in <code>waypoints</code>.
	 * The whole route lies in it.
	 * If the route traverses the 180° meridian, the bounding box is wrong.
	 * @return A bounding box built from <code>waypoints</code>.
	 */
	private MapSection calculateBoundingBox() {
		double northMax = waypoints.get(0).getLatitude();
		double southMax = waypoints.get(0).getLatitude();
		double eastMax = waypoints.get(0).getLongitude();
		double westMax = waypoints.get(0).getLongitude();
		for (int i = 1; i < waypoints.size(); i++) {
			if (waypoints.get(i).getLatitude() < northMax) {
				northMax = waypoints.get(i).getLatitude();
			}
			if (waypoints.get(i).getLatitude() > southMax) {
				southMax = waypoints.get(i).getLatitude();
			}
			if (waypoints.get(i).getLongitude() < westMax) {
				westMax = waypoints.get(i).getLongitude();
			}
			if (waypoints.get(i).getLongitude() > eastMax) {
				eastMax = waypoints.get(i).getLongitude();
			}
		}
		return new MapSection(new WorldPosition(westMax, northMax), new WorldPosition(eastMax, southMax));
	}
}
