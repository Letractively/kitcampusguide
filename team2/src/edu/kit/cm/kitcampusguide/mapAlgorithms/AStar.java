package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This class implements the RouteCalculator Interface using A-star algorithm.
 * The heuristic for the A-star algorithm is calculated using landmarks.
 * 
 * @author Tobias Z�ndorf
 *
 */
public class AStar implements RouteCalculator {
	
	/*
	 * Keeps the only instance of this class.
	 */
	private static AStar singleton;
	
	/*
	 * Private constructor to ensure only one instantiation of this class.
	 */
	private AStar() {
		assert singleton == null;
	}
	
	/**
	 * Returns the only instantiation of this class. If this is the first invocation
	 * of this method the class is instantiated at first. 
	 * 
	 * @return the only instantiation of this class
	 */
	public static AStar getSingleton() {
		if (singleton == null) {
			singleton = new AStar();
		}
		return singleton;
	}

	@Override
	public Route calculateRoute(Point from, Point to, Graph mapGraph) {
		Point[] landmarks = RouteCalculatingUtility.MAP_LOADER.getLandmarks();
		
		Point routeVector = new Point(to.getX() - from.getX(), to.getY() - from.getY());
		Point landmarkVector;
		int landmark = -1;
		double correspondence = 0.7;
		for (int i = 0; i < landmarks.length; i++) {
			landmarkVector = new Point(landmarks[i].getX() - from.getX(), landmarks[i].getY() - from.getY());
			if (correspondence(routeVector, landmarkVector) > correspondence) {
				landmark = i;
				correspondence = correspondence(routeVector, landmarkVector);
			}
		}

		if (landmark != -1) {
			double[][] distances = RouteCalculatingUtility.MAP_LOADER.getLandmarkDistances();
			for (int node = 0; node < mapGraph.numberOfNodes(); node++) {
				for (int edge : mapGraph.getEdges(node)) {
					mapGraph.setEdge(edge, mapGraph.getEdgeLength(edge) + distances[mapGraph.getEdgeNode(edge)][landmark] - distances[node][landmark]);
				}
			}
		}
		
		return Dijkstra.getSingleton().calculateRoute(from, to, mapGraph);
	}
	
	/**
	 * Calculates all necessary information to save the specified point as landmark for A-star algorithm. After that 
	 * the new landmark is saved in the database.
	 * Throws a IllegalArgumentException if the specified point is not part of the street graph.
	 * 
	 * @param point the point to become a landmark
	 */
	public void generateLandmark(Point point) {
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
	
	/*
	 * Compares the two specified Points and returns a number between -1 and 1. Where 1 means that they are located 
	 * in the same direction from origin.
	 */
	private double correspondence(Point pOne, Point pTwo) {
		return scalarProduct(pOne, pTwo) / Math.sqrt(scalarProduct(pOne, pOne) * scalarProduct(pTwo, pTwo));
	}
	
	/*
	 * Calculates the scalar product of the two specified Points.
	 */
	private double scalarProduct(Point pOne, Point pTwo) {
		return (pOne.getX() * pTwo.getX()) + (pOne.getY() * pTwo.getY());
	}

}