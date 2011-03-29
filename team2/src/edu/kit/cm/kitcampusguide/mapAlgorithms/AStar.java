package edu.kit.cm.kitcampusguide.mapAlgorithms;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This class implements the RouteCalculator Interface using A-star algorithm.
 * The heuristic for the A-star algorithm is calculated using landmarks.
 * 
 * @author Tobias Zündorf
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route calculateRoute(Point from, Point to, Graph mapGraph) {
		Point[] landmarks = RouteCalculatingUtility.getMapLoader().getLandmarks();
		
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
			double[][] distances = RouteCalculatingUtility.getMapLoader().getLandmarkDistances();
			mapGraph = new LandmarkedGraph(mapGraph, distances[landmark]);
		}
		
		return Dijkstra.getSingleton().calculateRoute(from, to, mapGraph);		
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
	
	private class LandmarkedGraph extends Graph {
		
		private double[] landmarkDistances;
		
		private LandmarkedGraph(Graph graph, double[] landmarkDistances) {
			super(graph);
			this.landmarkDistances = landmarkDistances;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getEdge(int indexFrom, int indexTo) {
			return super.getEdge(indexFrom, indexTo) + landmarkDistances[indexTo] - landmarkDistances[indexFrom];
		}
		
	}

}
