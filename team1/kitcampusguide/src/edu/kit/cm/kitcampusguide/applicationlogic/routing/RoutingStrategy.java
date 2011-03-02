package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Describes a routing algorithm for calculating a route between to points on
 * the KITCampus.
 * 
 * @author Fred
 * 
 */
public interface RoutingStrategy {
	
	/**
	 * Calculates a route from <code>from</code> to <code>to</code>.
	 * This class delegates the actual calculation.
	 * If there is no route between those positions, <code>null</code> will be returned
	 * @param from {@link MapPosition Position} the route starts at.
	 * @param to {@link MapPosition Position} the route ends at.
	 * @return The {@link Route route} between <code>from</code> and <code>to</code> or <code>null</code>.
	 */
	public Route calculateRoute(MapPosition from, MapPosition to);
}