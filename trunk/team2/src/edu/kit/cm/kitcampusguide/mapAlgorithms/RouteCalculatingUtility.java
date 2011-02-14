package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.ConstantData;
import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
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
	public static final MapLoader MAP_LOADER = new ConcreteMapLoader();

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
		Graph defaultGraph = ConstantData.getGraph();//MAP_LOADER.getGraph();
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
	
	/**
	 * Calculates all necessary information to save the specified point as landmark for A-star algorithm. After that 
	 * the new landmark is saved in the database.
	 * Throws a IllegalArgumentException if the specified point is not part of the street graph.
	 * 
	 * @param point the point to become a landmark
	 */
	public static void generateLandmark(Point point) {
		Graph streetGraph = RouteCalculatingUtility.calculateStreetGraph();
		if (streetGraph.getNodeIndex(point) == -1) {
			throw new IllegalArgumentException();
		}
		double[] distances = new double[streetGraph.numberOfNodes()];
		for (int i = 0; i < distances.length; i++) {
			List<Point> route = Dijkstra.getSingleton().calculateRoute(streetGraph.getNode(i), point, streetGraph).getRoute();
			for (int j = 0; j < route.size() - 1; j++) {
				distances[i] += streetGraph.getEdge(streetGraph.getNodeIndex(route.get(j)), streetGraph.getNodeIndex(route.get(j + 1)));
			}
		}
		RouteCalculatingUtility.MAP_LOADER.addLandmarkToDatabase(point, distances);
	}
}
