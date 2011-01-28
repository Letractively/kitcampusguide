package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.kit.cm.kitcampusguide.standardtypes.*;

/**
 * Represents a calculation of the dijkstra algorithm from a single position to every other position.
 * @author Fred
 *
 */
public class DijkstraRoutingCalculation {
	/** Stores the distance of a vertice from the starting vertice. <code>distance[i]</code> is the distance to vertice i.*/
	private double[] distance;
	/** Stores the routing graph.*/
	private RoutingGraph graph;
	/** Stores the parent of a vertice to the starting vertice. <code>parent[i]</code> is the parent for vertice i.*/
	private Integer[] parent;
	/** Stores the vertice the calculation starts at.*/
	private int fromVertice;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Constructs the dijkstra calculation from the {@link MapPosition} <code>from.</code>
	 * @param from The {@link MapPosition} the calculation starts at.
	 */
	public DijkstraRoutingCalculation(MapPosition from) {
		graph = RoutingGraph.getInstance();
		this.fromVertice = graph.getNearestVertice(from);
		logger.trace("Beginning the routing calculation from the vertice " + fromVertice);
		calculateRoutes();
		logger.trace("Ending routing calculation.");
	}
	
	/**
	 * Calculates the routes from the vertice stored in <code>fromVertice</code> to every other vertice.
	 */
	private void calculateRoutes() {
		setDistanceToInfinity();
		setParentsToNull();
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
	}

	/**
	 * Initializes the <code>distance</code> array and sets all of its entries to <code>Double.POSITIVE_INFINITY</code>. 
	 */
	private void setDistanceToInfinity() {
		distance = new double[graph.getVerticesCount()];
		for (int i = 0; i < graph.getVerticesCount(); i++) {
			distance[i] = Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * Initializes the <code>parent</code> array and sets all of its entries to <code>null</code>.
	 */
	private void setParentsToNull() {
		parent = new Integer[graph.getVerticesCount()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = null;
		}
		
	}
	
	/**
	 * Constructs and returns a route between the vertice stored in <code>fromVertice</code> and <code>to</code>.
	 * If no such route exists, <code>null</code> is returned. 
	 * @param to The vertice the route will end at.
	 * @return The route between <code>fromVertice</code> and <code>to</code> or <code>null</code>..
	 */
	public Route constructRoute(MapPosition to) {
		Route result = null;
		int toVertice = graph.getNearestVertice(to);
		List<MapPosition> waypoints = new ArrayList<MapPosition>();
		Integer tmp = toVertice;
		while((parent[tmp] != null) && (parent[tmp] != fromVertice)) {
			waypoints.add(graph.getPositionFromVertice(tmp));
			tmp = parent[tmp];
		}
		if (parent[tmp] != null) {
			waypoints.add(graph.getPositionFromVertice(fromVertice));
			Collections.reverse(waypoints);
			result = new Route(waypoints);
		}
		logger.debug("Route constructed from " + fromVertice + " to " + toVertice + " with a length of " + result.getWaypoints().size());
		return result;
	}

}
