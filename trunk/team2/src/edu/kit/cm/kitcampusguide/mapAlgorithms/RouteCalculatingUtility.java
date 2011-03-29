package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LAND;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

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
	private static MapLoader mapLoader = new ConcreteMapLoader();

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
		Graph defaultGraph = mapLoader.getGraph();
		Point[] graphNodes = defaultGraph.getNodes();
		
		for (Point node : nodes) {
			if (defaultGraph.getNodeIndex(node) == -1) {
				defaultGraph.addNode(node);
				double[] distances = new double[graphNodes.length];
				int min = 0;
				for (int j = 0; j < graphNodes.length; j++) {
					distances[j] =  getDistance(graphNodes[j], node);
					if (distances[j] < distances[min]) {
						min = j;
					}
				}
				for (int j = 0; j < graphNodes.length; j++) {
					if (distances[j] <= distances[min] * 1) {
						defaultGraph.addEdge(defaultGraph.numberOfNodes() - 1, j, distances[j]);
						defaultGraph.addEdge(j, defaultGraph.numberOfNodes() - 1, distances[j]);
					}
				}
			}
		}
		
		return defaultGraph;
	}
	
	/*
	 * Calculates all necessary information to save the specified point as landmark for A-star algorithm. After that 
	 * the new landmark is saved in the database.
	 * Throws a IllegalArgumentException if the specified point is not part of the street graph.
	 * 
	 * @param point the point to become a landmark 
	 */
	@SuppressWarnings("unused") // use this method to fill the database
	private static void generateLandmark(Point point) {
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
		RouteCalculatingUtility.mapLoader.addLandmarkToDatabase(point, distances);
	}

	/**
	 * Returns the MapLoader used for all functionality offered by this class.
	 * 
	 * @return the MapLoader used for all functionality offered by this class
	 */
	public static MapLoader getMapLoader() {
		return mapLoader;
	}

	/**
	 * Sets the MapLoader used for all functionality offered by this class.
	 * 
	 * @param mapLoader the MapLoader this class shuld use
	 */
	public static void setMapLoader(MapLoader mapLoader) {
		RouteCalculatingUtility.mapLoader = mapLoader;
	}

	/*
	 * Transform the the given radius into kartesian coordinates
	 */
	private static double getLengthByPolarCoordinates(double radius) {
		return radius / 180 * Math.PI;
	}
	
	/*
	 * Returns the distance in meters between two points on the map with help of their geographical coordinates.
	 * @param pointFrom is the point, from which we calculate the distance.
	 * @param pointTo is the point, to which we calculate the distance.
	 * @return the distance between the two points in meters.
	 */
	private static double getDistance(Point pointFrom, Point pointTo){
		double heightFrom = getLengthByPolarCoordinates(pointFrom.getX());
		double heightTo = getLengthByPolarCoordinates(pointTo.getX());
		double widthFrom = getLengthByPolarCoordinates(pointFrom.getY());
		double widthTo = getLengthByPolarCoordinates(pointTo.getY());
		double e = Math.acos(Math.sin(widthFrom) * Math.sin(widthTo) + Math.cos(widthFrom) * Math.cos(widthTo) * Math.cos(heightTo - heightFrom));
		return e * 6378.137 * 1000;
	}
	
}
