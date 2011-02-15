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
 * @author Tobias Z�ndorf
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
		
		for (Point node : nodes) {
			if (defaultGraph.getNodeIndex(node) == -1) {
				int neighbour = 0;
				double distance = getDistance(graphNodes[neighbour], node);
				for (int j = 1; j < graphNodes.length; j++) {
					double newDistance = getDistance(graphNodes[j], node);
					if (newDistance < distance) {
						neighbour = j;
						distance = newDistance;
					}
				}
				defaultGraph.addNode(node);
				defaultGraph.addEdge(defaultGraph.numberOfNodes() - 1, neighbour, distance);
				defaultGraph.addEdge(neighbour, defaultGraph.numberOfNodes() - 1, distance);
			}
			System.out.println(defaultGraph.numberOfNodes());
			System.out.println(defaultGraph.getNodeIndex(node));
			System.out.println("----------------------------------");
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
			System.out.print("    route> " + i + ": ");
			for (Point p : route) {
				System.out.print("(" + p.getX() + " | " + p.getY() + ") ");
			}
			System.out.println();
			for (int j = 0; j < route.size() - 1; j++) {
				distances[i] += streetGraph.getEdge(streetGraph.getNodeIndex(route.get(j)), streetGraph.getNodeIndex(route.get(j + 1)));
			}
		}
		for (int i = 0; i < distances.length; i++) {
			System.out.println("    > " + i + ": " + distances[i]);
		}
		RouteCalculatingUtility.MAP_LOADER.addLandmarkToDatabase(point, distances);
	}
	
	/*
	 * 
	 */
	private static double getLengthByPolarCoordinates(double radius) {
		return radius / 180 * Math.PI;
	}
	
	/**
	 * Returns the distance in meters between two points on the map with help of their geographical coordinates.
	 * @param pointFrom is the point, from which we calculate the distance.
	 * @param pointTo is the point, to which we calculate the distance.
	 * @return the distance between the two points in meters.
	 */
	public static double getDistance(Point pointFrom, Point pointTo){
		double widthFrom, widthTo, heightFrom, heightTo;
		heightFrom = getLengthByPolarCoordinates(pointFrom.getX());
		heightTo = getLengthByPolarCoordinates(pointTo.getX());
		widthFrom = getLengthByPolarCoordinates(pointFrom.getY());
		widthTo = getLengthByPolarCoordinates(pointTo.getY());
		Double e = Math.acos(Math.sin(widthFrom) * Math.sin(widthTo) + Math.cos(widthFrom) * Math.cos(widthTo) * Math.cos(heightTo - heightFrom));
		return e * 6378.137 * 1000;
	}
	
	public static void main(String[] args) {
		int[][] streets = {{0, 1}};
		MapLoader ml = new ConcreteMapLoader();
		Graph g = ml.getGraph();
		for (int[] street : streets) {
			ml.addStreetToDatabase(street[0], street[1], getDistance(g.getNode(street[0]), g.getNode(street[1])));
			ml.addStreetToDatabase(street[1], street[0], getDistance(g.getNode(street[0]), g.getNode(street[1])));
		}
	}
	
}
