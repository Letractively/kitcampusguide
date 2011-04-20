package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import java.util.HashMap;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Represents the dijkstra strategy for routing calculation. Singleton.
 * @author Fred
 *
 */
class DijkstraRouting {
	/** Stores the only instance of DijkstraRouting.*/
	private static DijkstraRouting instance;
	/** Stores the HashMap for caching.*/
	private HashMap<Integer, DijkstraRoutingCalculation> fromMap;
	/** The logger for this class*/
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Private constructor.
	 */
	private DijkstraRouting() {
		fromMap = new HashMap<Integer, DijkstraRoutingCalculation>();
	}
	
	/**
	 * Calculates a route from <code>from</code> to <code>to</code>.
	 * If there is no route between those positions, <code>null</code> will be returned
	 * @param from {@link MapPosition Position} the route starts at.
	 * @param to {@link MapPosition Position} the route ends at.
	 * @return The {@link Route route} between <code>from</code> and <code>to</code> or <code>null</code>.
	 */
	public Route calculateRoute(MapPosition from, MapPosition to) {
		logger.debug("Route calculation started");
		Route result = null;
		DijkstraRoutingCalculation calculation;
		RoutingGraph graph = RoutingGraph.getInstance();
		if ((!fromMap.containsKey(new Integer(graph.getNearestVertice(from))))) {
			fromMap.put(new Integer(graph.getNearestVertice(from)), new DijkstraRoutingCalculation(from));
		}
		calculation = fromMap.get(new Integer(graph.getNearestVertice(from)));
		result = calculation.constructRoute(to);
		return result;
	}
	
	/**
	 * Returns the only instance or creates a new one if none exists.
	 * @return The only instance or creates a new one if none exists.
	 */
	public static DijkstraRouting getInstance() {
		if (instance == null) {
			instance = new DijkstraRouting();
		}
		return instance;
	}
}
