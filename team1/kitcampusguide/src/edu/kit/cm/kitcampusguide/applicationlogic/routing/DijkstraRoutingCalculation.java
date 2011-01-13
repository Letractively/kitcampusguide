package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.kit.cm.kitcampusguide.standardtypes.*;

/**
 * Represents a calculation of the dijkstra algorithm from a single position.
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
		logger.trace("Constructor begins");
		graph = RoutingGraph.getInstance();
		this.fromVertice = graph.getNearestVertice(from);
		logger.trace("fromVertice set to " + fromVertice);
		calculateRoutes();
		logger.trace("Constructor ends");
	}
	
	/**
	 * Calculates the routes from the vertice stored in <code>fromVertice</code>.
	 */
	private void calculateRoutes() {
		logger.trace("calculateRoute begins");
		setDistanceToInfinity();
		logger.trace("setDistanceToInfinity");
		setParentsToNull();
		logger.trace("setParentsToNULL");
		parent[fromVertice] = fromVertice;
		logger.trace("parent of " + fromVertice + "set to " + fromVertice);
		DijkstraPriorityQueue Queue= new DijkstraPriorityQueue(graph.getVerticesCount());
		distance[fromVertice] = 0;
		logger.trace("distance of " + fromVertice + "set to 0");
		Queue.insert(fromVertice, distance[fromVertice]);
		logger.trace("fromVertice ( " + fromVertice + ") inserted in Queue");
		while(!Queue.isEmpty()) {
			logger.trace("while-loop begins");
			Integer currentCenter = Queue.deleteMin();
			logger.trace("currentCenter " + currentCenter);
			logger.trace("Neighbours: count is " + graph.getNeighbours(currentCenter).length);
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
		logger.trace("while-loop ends");
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
		return result;
	}

}
