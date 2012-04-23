package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Calculates the routes, specifically by pre-calculating all of them via
 * Dijkstras algorithm and constructing them when necessary.
 * @author Fred
 *
 */
class PreCalculatedRouting {
	/**Stores the only instance of PreCalculatedRoute*/
	private static PreCalculatedRouting instance;
	/**Stores the parentArray necessary to construct the routes. More specifically, 
	 * the value at parentArray[i][j] is representing the parent of j on the way to i.*/
	private Integer[][] parentArray;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Calculates all routes. Required only once.
	 */
	private PreCalculatedRouting() {
		calculateAllRoutes();
	}
	
	/**
	 * Calculates all routes from the only instance of RoutingGraph.
	 * It therefore uses Dijkstras Algorithm as described in "Algorithms and Datastructures"
	 */
	private void calculateAllRoutes() {
		RoutingGraph graph = RoutingGraph.getInstance();
		parentArray = new Integer[graph.getVerticesCount()][graph.getVerticesCount()];
		logger.info("Calculating all routes from and to " + graph.getVerticesCount() + " vertices.");
		for (int fromVertice = 0; fromVertice < graph.getVerticesCount(); fromVertice++) {
			parentArray[fromVertice] = preCalculateSingleRoute(fromVertice);
		}
		logger.info("Routes calculated");
	}
	
	private Integer[] preCalculateSingleRoute(int fromVertice) {
		RoutingGraph graph = RoutingGraph.getInstance();
		double[] distance = new double[graph.getVerticesCount()];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = Double.POSITIVE_INFINITY;
		}
		Integer[] parent = new Integer[graph.getVerticesCount()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = null;
		}
		parent[fromVertice] = fromVertice;
		DijkstraPriorityQueue Queue= new DijkstraPriorityQueue(graph.getVerticesCount());
		distance[fromVertice] = 0;
		Queue.insert(fromVertice, distance[fromVertice]);
		while(!Queue.isEmpty()) {
			Integer currentCenter = Queue.deleteMin();
			for (Integer currentVertice : graph.getNeighbours(currentCenter)) {
				if (distance[currentCenter] + graph.getWeight(currentCenter, currentVertice) < distance[currentVertice]) {
					distance[currentVertice] = distance[currentCenter] + graph.getWeight(currentCenter, currentVertice);
					parent[currentVertice] = currentCenter;
					if (Queue.contains(currentVertice)) {
						Queue.decreaseKey(currentVertice, distance[currentVertice]);
					} else {
						Queue.insert(currentVertice, distance[currentVertice]);
					}
				}
			}
		}
		return parent;
	}

	/**
	 * The actual construction of a route. Travels along the parentArray, constructing
	 * the route backwards and afterwards reverses it.
	 * @param from The position from which the route should start.
	 * @param to The position at which the route should end.
	 * @return The (shortest) route between from and to.
	 */
	public Route calculateRoute(MapPosition from, MapPosition to) {
		RoutingGraph graph = RoutingGraph.getInstance();
		Route result = null;
		int fromVertice = graph.getNearestVertice(from);
		int toVertice = graph.getNearestVertice(to);
		List<MapPosition> waypoints = new ArrayList<MapPosition>();
		Integer tmp = toVertice;
		while((parentArray[fromVertice][tmp] != null) && (tmp != fromVertice)) {
			waypoints.add(graph.getPositionFromVertice(tmp));
			tmp = parentArray[fromVertice][tmp];
		}
		if (parentArray[fromVertice][tmp] != null) {
			waypoints.add(graph.getPositionFromVertice(fromVertice));
			Collections.reverse(waypoints);
			result = new Route(waypoints);
		}
		if (result != null) {
			logger.debug("Route constructed from " + fromVertice + " to " + toVertice + " with a length of " + result.getWaypoints().size());
		} else {
			logger.debug("Did not find a route from " + fromVertice + " to " + toVertice + ".");
		}
		return result;

	}
	
	/**
	 * Returns the only instance of PreCalculatedRoute and constructs it if necessary.
	 * Warning: First calling this function can cause a huge load and may require a long time, and is
	 * therefore best done while initializing.
	 * @return The only instance of PreCalculatedRoute
	 */
	public static PreCalculatedRouting getInstance() {
		if (instance == null) {
			instance = new PreCalculatedRouting();
		}
		return instance;
	}
}
