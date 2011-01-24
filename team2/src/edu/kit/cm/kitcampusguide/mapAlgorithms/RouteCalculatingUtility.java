package edu.kit.cm.kitcampusguide.mapAlgorithms;

import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * This utility class provides several methods used for calculating Routes on a Graph.
 * 
 * @author Tobias Zündorf
 *
 */
public class RouteCalculatingUtility {
	
	/**
	 * The MapLoader Implementation used by this Class
	 */
	// TODO set mapLoader
	public static final MapLoader MAP_LOADER = null;

	/*
	 * Private constructor to prevent object instantiation
	 */
	private RouteCalculatingUtility() {
		assert false;
	}
	
	/**
	 * The method <code>calculateStreetGraph</code> requests a Graph representing the map from its mapLoader.
	 * Afterwards the specified Points <code>nodes</code> are added to the Graph and connected with the other nodes
	 * within the Graph. 
	 * 
	 * @param nodes nodes that should be contained in the requested Graph 
	 * @return a Graph containing the map and all Points specified in <code>nodes</code>
	 */
	public static Graph calculateStreetGraph(Point... nodes) {
		Graph defaultGraph = MAP_LOADER.getGraph();
		Point[] graphNodes = defaultGraph.getNodes();
		
		for (int i = 0; i < nodes.length; i++) {
			int neighbour = 0;
			double distance = distance(graphNodes[neighbour], nodes[i]);
			for (int j = 1; j < graphNodes.length; j++) {
				double newDistance = distance(graphNodes[j], nodes[i]);
				if (newDistance < distance) {
					neighbour = j;
					distance = newDistance;
				}
			}
			if (distance > 0) {
				defaultGraph.addNode(nodes[i]);
				defaultGraph.addEdge(defaultGraph.numberOfNodes() - 1, neighbour, distance);
				defaultGraph.addEdge(neighbour, defaultGraph.numberOfNodes() - 1, distance);
			}
		}
		
		return defaultGraph;
	}
	
	/**
	 * Calculates and returns the mathematics distance between two specified Points. 
	 * 
	 * @param pOne the first Point
	 * @param pTwo the second Point
	 * @return the mathematics distance between two specified Points
	 */
	// TODO im Entwurf eintragen
	public static double distance(Point pOne, Point pTwo) {
		double distanceX = pOne.getX() - pTwo.getX();
		double distanceY = pOne.getY() - pTwo.getY();
		return Math.sqrt((distanceX * distanceX) + (distanceY + distanceY));
	}
}
