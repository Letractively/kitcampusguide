package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This class implements the RouteCalculator Interface using Dijkstra-algorithm.
 * 
 * @author Tobias Zündorf
 *
 */
public class Dijkstra implements RouteCalculator {
	
	/*
	 * Keeps the only instance of this class.
	 */
	private static Dijkstra singleton;
	
	/*
	 * Private constructor to ensure only one instantiation of this class.
	 */
	private Dijkstra() {
		assert singleton == null;
	}
	
	/**
	 * Returns the only instantiation of this class. If this is the first invocation
	 * of this method the class is instantiated at first. 
	 * 
	 * @return the only instantiation of this class
	 */
	public static Dijkstra getSingleton() {
		if (singleton == null) {
			singleton = new Dijkstra();
		}
		return singleton;
	}

	@Override
	public Route calculateRoute(Point from, Point to) {
		LinkedList<Point> route = new LinkedList<Point>();
		Graph mapGraph = RouteCalculatingUtility.calculateStreetGraph(new Point[] {from, to});
		Node[] mapNodes = extractNodes(mapGraph);
		PriorityQueue<Dijkstra.Node> nodeQueue = new PriorityQueue<Dijkstra.Node>();
		
		nodeQueue.add(mapNodes[mapGraph.getNodeIndex(from)]);
		nodeQueue.peek().distance = 0;
		while (!nodeQueue.isEmpty() && !nodeQueue.peek().equals(to)) {
			Node activeNode = nodeQueue.poll();
			for (Integer edge : mapGraph.getEdges(activeNode.index)) {
				if (activeNode.distance + mapGraph.getEdgeLength(edge) < mapNodes[mapGraph.getEdgeNode(edge)].distance) {
					nodeQueue.remove(mapNodes[mapGraph.getEdgeNode(edge)]);
					mapNodes[mapGraph.getEdgeNode(edge)].distance = activeNode.distance + mapGraph.getEdgeLength(edge);
					mapNodes[mapGraph.getEdgeNode(edge)].parent = activeNode;
					nodeQueue.add(mapNodes[mapGraph.getEdgeNode(edge)]);
				}
			}
		}
		
		if (nodeQueue.peek().equals(to)) {
			Node activeNode = nodeQueue.poll();
			while (activeNode != null) {
				route.addFirst(activeNode.point);
				activeNode = activeNode.parent;
			}
		}
		return new Route(route);
	}
	
	/*
	 * Creates and returns an Array containing all nodes of the specified Graph. Additional each node
	 * can save extra information needed for the Dijkstra-algorithm.
	 */
	private Node[] extractNodes(Graph graph) {
		Node[] nodes = new Node[graph.numberOfNodes()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i, graph.getNode(i), null, Double.MAX_VALUE);
		}
		return nodes;
	}
	
	/* 
	 * A private helper class to encapsulate additional information for one Node that are
	 * needed for the Dijkstra-algorithm.
	 */
	private class Node implements Comparable<Node> {
		
		/*
		 * Holds the node index
		 */
		int index;
		
		/*
		 * Holds the point on the map that is associated with this node
		 */
		Point point;
		
		/*
		 * Holds the previous node on an shortest route calculated by the Dijkstra-algorithm
		 */
		Node parent;
		
		/*
		 * Holds the distance between this node and the start node
		 */
		double distance;
		
		/*
		 * Creates a new Node and with specified additional information.
		 */
		private Node(int index, Point point, Node parent, double distance) {
			this.index = index;
			this.point = point;
			this.parent = parent;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node other) {
			return (int) Math.signum(this.distance - other.distance);
		}
		
	}

}
