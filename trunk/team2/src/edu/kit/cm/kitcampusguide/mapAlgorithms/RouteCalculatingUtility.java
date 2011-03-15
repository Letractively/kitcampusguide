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
		RouteCalculatingUtility.MAP_LOADER.addLandmarkToDatabase(point, distances);
	}
	
	/*
	 * 
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
	
	/**
	 * Generates the Streets for the database.
	 * 
	 * @param args Command-line arguments
	 */
	 // use this method to fill the database
	public static void main(String[] args) {
		int[][] streets = {{0, 1}, {1, 2}, {1, 3}, {2, 3}, {2, 4}, {3, 17}, {4, 17}, {4, 5}, 
		{5, 6}, {5, 7}, {16, 17}, {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12}, {12, 13}, {12, 14}, {13, 30}, 
		{14, 28}, {30, 28}, {25, 28}, {25, 26}, {26, 27}, {27, 30}, {26, 29}, {27, 29}, {28, 29}, 
		{29, 30}, {24, 25}, {24, 26}, {23, 24}, {21, 23}, {18, 21}, {18, 19}, {19, 20}, {21, 22}, {22, 15}, {14, 15}, {18, 57}, 
		{15, 16}, {16, 57}, {40, 57}, {31, 40}, {31, 27}, {31, 32}, {33, 32}, {33, 34}, {34, 35}, {35, 36}, {36, 37}, {37, 38}, 
		{38, 39}, {32, 37}, {39, 40}, {36, 45}, {44, 45}, {43, 44}, {43, 42}, {41, 42}, {41, 50}, {41, 53}, {53, 52}, {51, 52}, {51, 50}, {49, 50}, 
		{48, 49}, {47, 48}, {46, 47}, {45, 46}, {47, 54}, {50, 54}, {54, 55}, {55, 56}, {0, 56}, {41, 57}, 
		{46, 61}, {34, 74}, {62, 74}, {61, 97}, {97,62}, {73, 62}, {69, 73}, {67, 69}, {67, 68}, {60, 68}, {59, 60}, {58, 59}, {75, 60}, {58, 75}
		, {60, 63}, {63, 64}, {64, 67}, {64, 65}, {65, 66}, {69, 70}, {71, 70}, {71, 72}, {66, 70}, {66, 72}, 
		{6, 76}, {76, 77}, {77, 78}, {78, 80}, {78, 79}, {79, 80}, {80, 81}, {81, 82}, {83, 82}, {83, 84}
		, {84, 85}, {85, 86}, {87, 86}, {87, 88}, {88, 89}, {87, 90}, {89, 90}, {91, 90}, {91, 92}
		, {86, 93}, {93, 92}, {93, 95}, {94, 95}, {93, 94}, {95, 92}, {96, 73}, {69, 96}, {96, 98}, {98, 72}
		, {101, 98}, {101, 102}, {101, 100}, {54, 102}, {100, 55}, {99, 98}, {99, 103}, {103, 56}
		, {103, 104}, {104, 56}, {56, 105}, {105, 106}, {106, 107}, {107, 112}, {112, 108}, {110, 112}, {111, 110}, {111, 6}, {110, 109}
		, {109, 76}, {108, 109}, {104, 107}, {81,13}, {75, 113}, {113, 114}, {113, 122}, {116, 122}, {114, 115}, {115, 116}, {116, 117}
		, {117, 118}, {118, 119}, {119, 120}, {120, 121}, {104, 121}, {77, 121}, {122, 99}, {72, 122}};
		MapLoader ml = new ConcreteMapLoader();
		Graph g = ml.getGraph();
		for (int[] street : streets) {
			ml.addStreetToDatabase(street[0], street[1], getDistance(g.getNode(street[0]), g.getNode(street[1])));
			ml.addStreetToDatabase(street[1], street[0], getDistance(g.getNode(street[0]), g.getNode(street[1])));
		}
	}
}
