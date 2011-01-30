package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Represents a route.
 * Stores the {@link MapPosition waypoints} defining the route and a {@link MapSection bounding box.}
 * @author fred
 *
 */
public class Route implements Serializable {
	/** Stores the {@link MapPosition position} the route starts at.*/
	private final MapPosition start;
	
	/** Stores the {@link MapPosition position} the route ends at.*/
	private final MapPosition end;
	
	/** Stores the {@link MapPosition waypoints} defining the route*/
	private final List<MapPosition> waypoints;
	
	/** Stores the {@link MapSection bounding box} the route lies in.*/
	private final MapSection boundingBox;
	
	/**
	 * Constructs a new route.
	 * @param waypoints The {@link MapPosition MapPositions} defining the route. If it contains only one, a second waypoint will be constructed.
	 * 
	 * @throws NullPointerException If <code>waypoints</code> is null.
	 * @throws IllegalArgumentException If <code>waypoints</code> has less than two elements.
	 */
	public Route(List<MapPosition> waypoints) throws NullPointerException, IllegalArgumentException{
		if (waypoints == null) {
			throw new NullPointerException("Waypoints is null.");
		}
		if (waypoints.size() < 1) {
			throw new IllegalArgumentException("Waypoints required to have a length of at least 1.");
		}
		List<MapPosition> waypointsTmp = waypoints;
		if (waypoints.size() == 1) {
			waypointsTmp.add(waypointsTmp.get(0));
		}
		this.waypoints = Collections.unmodifiableList(waypointsTmp);
		this.start = waypoints.get(0);
		this.end = waypoints.get(waypoints.size() - 1);
		this.boundingBox = calculateBoundingBox();
	}
	
	/**
	 * Returns the {@link MapPosition} at which the route begins.
	 * @return The {@link MapPosition} at which the route begins.
	 */
	public MapPosition getStart() {
		return start;
	}
	
	/**
	 * Returns the {@link MapPosition} at which the route ends.
	 * @return The {@link MapPosition} at which the route ends.
	 */
	public MapPosition getEnd() {
		return end;
	}
	
	/**
	 * Returns a list of {@link MapPosition MapPositions} defining the route.
	 * @return A list of {@link MapPosition MapPositions} defining the route.
	 */
	public List<MapPosition> getWaypoints() {
		return Collections.unmodifiableList(waypoints);
	}
	
	/**
	 * Returns the {@link MapSection bounding box} of the route.
	 * @return The {@link MapSection bounding box} of the route.
	 */
	public MapSection getBoundingBox() {
		return boundingBox;
	}

	/**
	 * Constructs a {@link MapSection bounding box} from the {@link MapPosition waypoints} already saved in <code>waypoints</code>.
	 * The whole route lies in it.
	 * If the route traverses the 180� meridian, the bounding box is wrong.
	 * @return A {@link MapPosition bounding box} built from <code>waypoints</code>.
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
		return new MapSection(new WorldPosition(northMax, westMax), new WorldPosition(southMax, eastMax));
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (obj == null || obj.getClass() != this.getClass()) {
			result = false;
		} else {
			List<MapPosition> waypoints2 = ((Route) obj).getWaypoints();
			if ( waypoints2 != null && waypoints2.size() == waypoints.size()) {
				for (int i = 0; i < waypoints.size(); i++) {
					if (!waypoints2.get(i).equals(waypoints.get(i))) {
						result = false;
						break;
					}
				}
			}
			
		
		}
			return result;
	}
}
