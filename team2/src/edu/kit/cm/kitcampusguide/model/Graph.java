package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class represents a directed, weighted Graph consisting edges and nodes to 
 * represent streets and intersections of a map.
 *  
 * @author Tobias Z�ndorf
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
	private ArrayList<Point> points;
	/*
	 * Holds the length of all edges within the Graph. The index of any edge in this 
	 * array is it's unique index.
	 */
	private ArrayList<Double> length;
	/*
	 * Holds for each node-index the edge-index of the first edge within this Graph that
	 * is incident to it. 
	 */
	private ArrayList<Integer> nodes;
	/*
	 * Holds for each edge-index the node-index of the endnode of the edge.
	 */
	private ArrayList<Integer> edges;
	
	
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
			this.points = (ArrayList<Point>) points.clone();
			this.length = (ArrayList<Double>) length.clone();
			this.nodes = (ArrayList<Integer>) nodes.clone();
			this.edges = (ArrayList<Integer>) edges.clone();
		}  else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Adds the specified Point as a node to the Graph. The index of the new node is
	 * .numberOfNodes(). After adding the node .numberOfNodes is increased by one.
	 * 
	 * @param point the Point to add
	 */
	// TODO return wert zu int �ndern und NodeIndex zur�ckgeben?
	public void addNode(Point point) {
		if (point == null) {
			throw new NullPointerException("point should not be null");
		}
		this.points.add(point);
		this.nodes.add(new Integer(this.nodes.get(this.nodes.size() - 1)));
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
	 * @param index the index of the searched Point
	 * @return the Point with the specified index
	 */
	public Point getNode(int index) {
		if (index < 0 || index >= this.points.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to .numberOfNodes() - 1");
		}
		return this.points.get(index);
	}
	
	/**
	 * Returns the degree of the node with the specified index.
	 * 
	 * @param index the index of the searched Point
	 * @return the degree of the node with the specified index
	 */
	// TODO zum Entwurf hinzuf�gen
	public int getNodeDegree(int index) {
		if (index < 0 || index >= this.points.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to .numberOfNodes() - 1");
		}
		return (this.nodes.get(index + 1) - this.nodes.get(index));
	}
	
	/**
	 * Returns the index of the specified Point if it is contained in this Graph, -1 otherwise.
	 * 
	 * @param node the Point to search for
	 * @return the index of the specified Point if it is contained in this Graph, -1 otherwise
	 */
	// TODO zum Entwurf hinzuf�gen
	public int getNodeIndex(Point node) {
		int index = -1;
		for (int i = 0; i < this.numberOfNodes() && index == -1; i++) {
			if (this.points.get(i).equals(node)) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Adds a new edge to the Graph. The edge connects the node with index from with
	 * node with index to and has the weight length.
	 * 
	 * @param from the startnode of the edge
	 * @param to the endnode of the edge
	 * @param length the weight of the edge
	 */
	// TODO entwurf/Graph/addEdge falsch 
	public void addEdge(int from, int to, double length) {
		if (from < 0 || from >= this.points.size()) {
			throw new IndexOutOfBoundsException("from has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (to < 0 || to >= this.points.size()) {
			throw new IndexOutOfBoundsException("to has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (length < 0) {
			throw new IllegalArgumentException("length has t be a positve number");
		}
		this.length.add(this.nodes.get(from), length);
		this.edges.add(this.nodes.get(from), to);
		for (int i = from + 1; i < this.nodes.size(); i++) {
			this.nodes.set(i, this.nodes.get(i).intValue() + 1);
		}
	}
	
	/**
	 * Changes the length of the specified edge to the specified length value, if the edge exists.
	 * 
	 * @param index the edge to change
	 * @param length the new length of the edge
	 */
	// TODO zum entwurf hinzuf�gen
	public void setEdge(int index, double length) {
		if (index < 0 || index >= this.length.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to number of edges - 1");
		}
		this.length.set(index, length);
	}
	
	/**
	 * Returns the length of the Edge identified by the specified index.
	 * 
	 * @param index the index of the searched edge
	 * @return the length of the Edge identified by the specified index
	 */
	// TODO zum entwurf hinzuf�gen
	public double getEdgeLength(int index) {
		if (index < 0 || index >= this.length.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to number of edges - 1");
		}
		return this.length.get(index);
	}
	
	/**
	 * Returns the endnode of the Edge identified by the specified index.
	 * 
	 * @param index the index of the searched edge
	 * @return the endnode of the Edge identified by the specified index
	 */
	// TODO Entwurf
	public int getEdgeNode(int index) {
		if (index < 0 || index >= this.edges.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to number of edges - 1");
		}
		return this.edges.get(index);
	}
	
	/**
	 * Returns the length of the Edge identified by it's startnode and endnode.
	 * 
	 * @param from the startnode of the searcjed edge
	 * @param to the endnode of the searched edge
	 * @return the length of the Edge identified by it's startnode and endnode
	 */
	public double getEdge(int from, int to) {
		if (from < 0 || from >= this.points.size()) {
			throw new IndexOutOfBoundsException("from has to be in range of 0 to .numberOfNodes() - 1");
		}
		if (to < 0 || to >= this.points.size()) {
			throw new IndexOutOfBoundsException("to has to be in range of 0 to .numberOfNodes() - 1");
		}
		for (int i = this.nodes.get(from); i < this.nodes.get(from + 1); i++) {
			if (this.edges.get(i).intValue() == to) {
				return this.length.get(i);
			}
		}
		return Double.NaN;
	}
	
	/**
	 * Returns an Iterable containing all edges that can be reached from the specified node. 
	 * 
	 * @param index 
	 * @return an Iterable containing all edges that can be reached from the specified node
	 */
	// TODO im Entwurf erg�nzen
	public Iterable<Integer> getEdges(int index) {
		return new EdgeIterator(this.nodes.get(index + 1), this.nodes.get(index) - 1);
	}
	
	/**
	 * Returns an array containing the indices of all neighbours of the specified node.
	 * 
	 * @param index
	 * @return an array containing the indices of all neighbours of the specified node.
	 */
	public int[] getNeighbours(int index) {
		if (index < 0 || index >= this.points.size()) {
			throw new IndexOutOfBoundsException("index has to be in range of 0 to .numberOfNodes() - 1");
		}
		int[] neighbours = new int[this.nodes.get(index + 1) - this.nodes.get(index)];
		int offset = this.nodes.get(index);
		for (int i = offset; i < this.nodes.get(index + 1); i++) {
			neighbours[i - offset] = this.edges.get(i);
		}
		return neighbours;
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
	private static boolean valid(ArrayList<Point> points, ArrayList<Double> length, ArrayList<Integer> nodes, ArrayList<Integer> edges) {
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
	 * A private helper class, to support easy iteration over all edges of one node.
	 */
	private class EdgeIterator implements Iterator<Integer>, Iterable<Integer> {

		/*
		 * Holds the edge that is currently viewed by the Iterator
		 */
		int active;
		
		/*
		 * Holds the last edge that can be viewed by this Iterator
		 */
		int last;
		
		/*
		 * Creates a new EdgeIterator that iterates over all edges between from and to.
		 */
		private EdgeIterator(int from, int to) {
			this.active = from - 1;
			this.last = to;
		}
		
		@Override
		public Iterator<Integer> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return this.active < this.last;
		}

		@Override
		public Integer next() {
			return (++this.active);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	/*
	 * Simple Graph test
	 */
	@Test
	public void graphTest() {
		Graph testGraph = new Graph();
		Point[] nodes = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)};
		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);
		Assert.assertTrue(testGraph.numberOfNodes() == 4);
		Assert.assertArrayEquals(testGraph.getNodes(),nodes);
		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 3, 5);
		testGraph.addEdge(2, 3, 8);
		testGraph.addEdge(1, 3, 6);
		testGraph.addEdge(0, 2, 4);
		testGraph.addEdge(3, 2, 7);
		Assert.assertTrue(testGraph.numberOfNodes() == 4);
		Assert.assertArrayEquals(testGraph.getNodes(),nodes);
		Assert.assertTrue(testGraph.getNode(2) == nodes[2]);
		Assert.assertArrayEquals(testGraph.getNeighbours(0), new int[] {0, 1, 2, 3});
		Assert.assertArrayEquals(testGraph.getNeighbours(1), new int[] {0, 3});
		Assert.assertArrayEquals(testGraph.getNeighbours(2), new int[] {3});
		Assert.assertArrayEquals(testGraph.getNeighbours(3), new int[] {2});
		Assert.assertTrue(testGraph.getEdge(0, 3) == 5);
		Assert.assertTrue(testGraph.getEdge(1, 3) == 6);
		Assert.assertTrue(testGraph.getEdge(3, 2) == 7);
	}

}
