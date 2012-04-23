package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import java.util.HashMap;
import edu.kit.cm.kitcampusguide.standardtypes.*;

/**
 * Represents the dijkstra strategy for routing calculation. 
 * @author Fred
 *
 */
class DijkstraRouting {
	/** Stores the only instance of DijkstraRouting.*/
	private static DijkstraRouting instance;
	/** Stores the HashMap for caching.*/
	private HashMap<Integer, DijkstraRoutingCalculation> fromMap;
	/** Stores the routingGraph.*/
	private RoutingGraph routingGraph;
	
	/**
	 * Private constructor.
	 */
	private DijkstraRouting() {
		routingGraph = RoutingGraph.getInstance();
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
		Route result = null;
		DijkstraRoutingCalculation calculation;
		if ((!fromMap.containsKey(new Integer(routingGraph.getNearestVertice(from))))) {
			fromMap.put(new Integer(routingGraph.getNearestVertice(from)), new DijkstraRoutingCalculation(from));
		}
		calculation = fromMap.get(new Integer(routingGraph.getNearestVertice(from)));
		result = calculation.constructRoute(to);
		return result;
		
		
		/*Route result = null;
		DijkstraRoutingCalculation calculation = new DijkstraRoutingCalculation(from);
		result = calculation.constructRoute(to);
		return result;
		*/
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
