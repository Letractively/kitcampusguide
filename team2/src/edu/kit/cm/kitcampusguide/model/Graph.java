package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a directed, weighted Graph consisting edges and nodes to 
 * represent streets and intersections of a map.
 *  
 * @author Tobias Zündorf
 *
 */
/*
 * A Graph is represented using adjacency-field. For that each node within the Graph 
 * is dedicated with an unique index. Similarly each Edge is dedicated with an unique
 * Index.
 * All edges that are incident to one node have continuous indices. 
 * 
 * One Graph instance will be created when the application is started. This graph is used to calculate routes whenever
 * a route request occurs.
 */
public class Graph {

	/* 
	 * Holds all nodes, which a represented by Points, of this Graph. The index of any
	 * Point in this array is it's unique index.
	 */
	private List<Point> points;
	/*
	 * Holds the length of all edges within the Graph. The index of any edge in this 
	 * array is it's unique index.
	 */
	private List<Double> length;
	/*
	 * Holds for each node-index the edge-index of the first edge within this Graph that
	 * is incident to it. 
	 */
	private List<Integer> nodes;
	/*
	 * Holds for each edge-index the node-index of the endnode of the edge.
	 */
	private List<Integer> edges;
	
	/**
	 * Creates a new Graph instance that uses the attributes of the given Graph
	 * 
	 * @param graph the Graph to use its attributes
	 */
	protected Graph(Graph graph) {
		this.points = graph.points;
		this.length = graph.length;
		this.nodes = graph.nodes;
		this.edges = graph.edges;
	}
	
	
	/**
	 * Creates a new but empty Graph.
	 */
	public Graph() {
		this.points = new ArrayList<Point>();
		this.length = new ArrayList<Double>();
		this.nodes = new ArrayList<Integer>();
		this.edges = new ArrayList<Integer>();
		this.nodes.add(0);
	}
	
	/**
	 * Tries to setup a new Graph with specified nodes and edges. Throws a IllegalArgumentException if the specified 
	 * lists of nodes and edges don not represent a consistent Graph.
	 * 
	 * @param points a list containing Points, representing the geographic of each node 
	 * @param length a list containing the length of each edge represented by there index 
	 * @param nodes a list that defines a mapping from node indices to edge indices
	 * @param edges a list containing the end node for eage edge, represented by there index
	 */
	@SuppressWarnings("unchecked")
	public Graph(ArrayList<Point> points, ArrayList<Double> length, ArrayList<Integer> nodes, ArrayList<Integer> edges) {
		if (valid(points, length, nodes, edges)) {
			this.points = (List<Point>) points.clone();
			this.length = (List<Double>) length.clone();
			this.nodes = (List<Integer>) nodes.clone();
			this.edges = (List<Integer>) edges.clone();
		}  else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Tries to setup a new Graph with specified nodes and edges. Throws a IllegalArgumentException if the specified 
	 * lists of nodes and edges don not represent a consistent Graph.
	 * 
	 * @param points a list containing Points, representing the geographic of each node 
	 * @param length a list containing the length of each edge represented by there index 
	 * @param nodes a list that defines a mapping from node indices to edge indices
	 * @param edges a list containing the end node for eage edge, represented by there index
	 */
	public Graph(Point[] points, Double[] length, Integer[] nodes, Integer[] edges) {
		 this.points = copy(points);
		 this.length = copy(length);
		 this.nodes = copy(nodes);
		 this.edges = copy(edges);
		 if (!valid(this.points, this.length, this.nodes, this.edges)) {
			 throw new IllegalArgumentException();
		 }
	}
	
	/**
	 * Adds the specified Point as a node to the Graph. The index of the new node is
	 * .numberOfNodes(). After adding the node .numberOfNodes is increased by one.
	 * 
	 * @param point the Point to add
	 */
	public void addNode(Point point) {
		if (point == null) {
			throw new NullPointerException("point should not be null");
		}
		this.points.add(point);
		this.nodes.add(new Integer(this.nodes.get(this.nodes.size() - 1)));
	}
	
	/**
	 * Adds a new edge to the Graph. The edge connects the node with index from with
	 * node with index to and has the weight length.
	 * 
	 * @param indexFrom the startnode of the edge
	 * @param indexTo the endnode of the edge
	 * @param length the weight of the edge
	 */
	public void addEdge(int indexFrom, int indexTo, double length) {
		if (indexFrom < 0 || indexFrom >= this.points.size()) {
			throw new IndexOutOfBoundsException("from has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (indexTo < 0 || indexTo >= this.points.size()) {
			throw new IndexOutOfBoundsException("to has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (length < 0) {
			throw new IllegalArgumentException("length has to be a positve number");
		}
		this.length.add(this.nodes.get(indexFrom), length);
		this.edges.add(this.nodes.get(indexFrom), indexTo);
		for (int i = indexFrom + 1; i < this.nodes.size(); i++) {
			this.nodes.set(i, this.nodes.get(i).intValue() + 1);
		}
	}
	
	/**
	 * Returns an array containing all Points within this Graph placed at the index of 
	 * their unique node-index.
	 * 
	 * @return an array containing all Points within this Graph
	 */
	public Point[] getNodes() {
		return this.points.toArray(new Point[this.points.size()]);
	}
	
	/**
	 * Returns the Point of the node with the specified index.
	 * 
	 * @param nodeIndex the index of the searched Point
	 * @return the Point with the specified index
	 */
	public Point getNode(int nodeIndex) {
		if (nodeIndex < 0 || nodeIndex >= this.points.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to .numberOfNodes() - 1");
		}
		return this.points.get(nodeIndex);
	}
	
	/**
	 * Returns the degree of the node with the specified index.
	 * 
	 * @param nodeIndex the index of the searched Point
	 * @return the degree of the node with the specified index
	 */
	public int getNodeDegree(int nodeIndex) {
		if (nodeIndex < 0 || nodeIndex >= this.points.size()) {
			throw new IndexOutOfBoundsException("nodeIndex has to be in range of 0 to .numberOfNodes() - 1");
		}
		return (this.nodes.get(nodeIndex + 1) - this.nodes.get(nodeIndex));
	}
	
	/**
	 * This method gives the Possibility to iterate over all neighbours of the specified node.
	 * 
	 * @param nodeIndex the node, who's neighbours shall be iterated
	 * @return An iterable instance for the neighbours of the specified node
	 */
	public Iterable<Integer> NeighboursOf(final int nodeIndex) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					int index = nodes.get(nodeIndex) - 1;
					int last = nodes.get(nodeIndex + 1) - 1;

					@Override
					public boolean hasNext() {
						return index < last;
					}

					@Override
					public Integer next() {
						return edges.get(++index);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	/**
	 * Returns the index of the specified Point if it is contained in this Graph, -1 otherwise.
	 * 
	 * @param node the Point to search for
	 * @return the index of the specified Point if it is contained in this Graph, -1 otherwise
	 */
	public int getNodeIndex(Point node) {
		int index = -1;
		for (int i = 0; i < this.numberOfNodes() && index == -1; i++) {
			if (this.points.get(i).equals(node)) {
				index = i;
			}
		}
		if (index == -1) {
			throw new IllegalArgumentException("The given Point is not a Graph node");
		}
		return index;
	}
	
	/**
	 * Returns the length of the Edge identified by it's startnode and endnode.
	 * 
	 * @param indexFrom the startnode of the searcjed edge
	 * @param indexTo the endnode of the searched edge
	 * @return the length of the Edge identified by it's startnode and endnode
	 */
	public double getEdge(int indexFrom, int indexTo) {
		if (indexFrom < 0 || indexFrom >= this.points.size()) {
			throw new IndexOutOfBoundsException("from has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (indexTo < 0 || indexTo >= this.points.size()) {
			throw new IndexOutOfBoundsException("to has to be in range of 0 to .numberOfNodes() - 1");
		}
		for (int i = this.nodes.get(indexFrom); i < this.nodes.get(indexFrom + 1); i++) {
			if (this.edges.get(i).intValue() == indexTo) {
				return this.length.get(i);
			}
		}
		return Double.NaN;
	}
	
	/**
	 * Returns the amount of nodes currently contained in this Graph.
	 * 
	 * @return the amount of nodes currently contained in this Graph
	 */
	public int numberOfNodes() {
		return this.points.size();
	}
	
	/*
	 * This method checks if the specified lists could represent a consistent Graph.
	 */
	private static boolean valid(List<Point> points, List<Double> length, List<Integer> nodes, List<Integer> edges) {
		boolean valid = (points != null && length != null && nodes != null && edges != null);
		valid &= points.size() == nodes.size() - 1 && length.size() == edges.size();
		valid &= nodes.get(points.size()) == length.size();
		for (int i = 0; i < points.size(); i++) {
			valid &= nodes.get(i) <= nodes.get(i + 1);
		}
		for (int i = 0; i < edges.size(); i++) {
			valid &= edges.get(i) < points.size();
		}
		return valid;
	}
	
	/*
	 * This method copies the content of an specified Array into a List. 
	 */
	private static <T> List<T> copy(T[] source) {
		ArrayList<T> result = new ArrayList<T>();
		for (T element : source) {
			result.add(element);
		}
		return result;
	}

}
