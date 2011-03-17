package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.LinkedList;

import edu.kit.cm.kitcampusguide.model.AddressableRadixHeap;
import edu.kit.cm.kitcampusguide.model.GenericArray;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route calculateRoute(Point from, Point to, Graph mapGraph) {
		int fromIndex = mapGraph.getNodeIndex(from);
		
		AddressableRadixHeap<Node> nodeQueue = new AddressableRadixHeap<Node>((int) maxEdgeLength(mapGraph));
		GenericArray<AddressableRadixHeap<Node>.Handle> handles = new GenericArray<AddressableRadixHeap<Node>.Handle>(mapGraph.numberOfNodes());
		handles.set(fromIndex, nodeQueue.insert(new Node(fromIndex, null), 0));
		
		while (!nodeQueue.isEmpty() && !mapGraph.getNode(nodeQueue.min().getElement().index).equals(to)) {
			AddressableRadixHeap<Node>.Handle activeHandle = nodeQueue.min();
			for (int neighbour : mapGraph.NeighboursOf(activeHandle.getElement().index)) {
				int distance = activeHandle.getKey() + (int) (mapGraph.getEdge(activeHandle.getElement().index, neighbour));
				if (handles.get(neighbour) == null) {
					handles.set(neighbour, nodeQueue.insert(new Node(neighbour, activeHandle.getElement()), distance));
				} else if (handles.get(neighbour).getKey() > distance) {
					handles.get(neighbour).getElement().parent = activeHandle.getElement();
					nodeQueue.decreaseKey(handles.get(neighbour), distance);
				}
			}
			nodeQueue.deleteMin();
		}
		
		LinkedList<Point> route = new LinkedList<Point>();
		if (!nodeQueue.isEmpty() && mapGraph.getNode(nodeQueue.min().getElement().index).equals(to)) {
			Node routeNode = nodeQueue.min().getElement();
			while (routeNode != null) {
				route.addFirst(mapGraph.getNode(routeNode.index));
				routeNode = routeNode.parent;
			}
		}
		
		return new Route(route);
	}
	
	/*
	 * Returns the length of the longest edge within the specified graph.
	 */
	private double maxEdgeLength(Graph graph) {
		double maxLength = -1;
		for (int i = 0; i < graph.numberOfNodes(); i++) {
			for (int neighbour : graph.NeighboursOf(i)) {
				if (graph.getEdge(i, neighbour) > maxLength) {
					maxLength = graph.getEdge(i, neighbour);
				}
			}
		}
		return maxLength;
	}
	
	/* 
	 * A private helper class to encapsulate additional information for one Node that are
	 * needed for the Dijkstra-algorithm.
	 */
	private class Node {
		
		/*
		 * Holds the node index
		 */
		int index;
		
		/*
		 * Holds the previous node on an shortest route calculated by the Dijkstra-algorithm
		 */
		Node parent;
		
		/*
		 * Creates a new Node and with specified additional information.
		 */
		private Node(int index, Node parent) {
			this.index = index;
			this.parent = parent;
		}
		
	}

}
