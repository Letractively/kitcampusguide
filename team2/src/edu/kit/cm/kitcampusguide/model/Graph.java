package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;

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
	
	
	//TODO Konstruktor (mit welchen Parameter soll ein graph erzeugt werden)
	public Graph() {
		this.points = new ArrayList<Point>();
		this.length = new ArrayList<Double>();
		this.nodes = new ArrayList<Integer>();
		this.edges = new ArrayList<Integer>();
		this.nodes.add(0);
	}
	
	/**
	 * Adds the specified Point as a node to the Graph. The index of the new node is
	 * .numberOfNodes(). After adding the node .numberOfNodes is increased by one.
	 * 
	 * @param point the Point to add
	 */
	// TODO return wert zu int ändern und NodeIndex zurückgeben?
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
			throw new IllegalArgumentException("index has to be in range of 0 to .numberOfNodes(");
		}
		return this.points.get(index);
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
		if (from < 0 || to < 0 || length < 0 || from >= this.points.size() || to >= this.points.size()) {
			throw new IllegalArgumentException();
		}
		this.length.add(this.nodes.get(from), length);
		this.edges.add(this.nodes.get(from), to);
		for (int i = from + 1; i < this.nodes.size(); i++) {
			this.nodes.set(i, this.nodes.get(i).intValue() + 1);
		}
	}

}
