package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Represents the data structure required for DijkstraRouting and the mechanisms required to extract itself from a file.
 * @author Fred
 *
 */
class RoutingGraph {
	
	private Logger logger = Logger.getLogger(getClass());
	/** Stores the vertices and the corresponding edgeArray-positions. Required to be verticesCount + 1 dummy element*/
	private int[] verticesArray;
	/** Stores the edges of the represented graph in the adjacency array format.*/
	private int[] edgeArray;
	/** The element at position i stores the weight of the edge at position i of <code>edgeArray</code>*/
	private double[] weightArray;
	/** The element at position i stores the MapPosition of the element at position i of <code>verticesArray</code>*/
	private MapPosition[] positionArray;
	/**Stores the only instance of RoutingGraph*/
	private static RoutingGraph instance;

	/**
	 * Constructs a new adjacency array from the parameters.
	 * @param verticesArray The array of vertices.
	 * @param edgeArray The array of edges.
	 * @param weightArray The array representing the weight of an edge. weightArray[i] is the weight of the edge represented by edgeArray[i].
	 * @param positionArray The position of a vertice. positionArray[i] is the position of the vertice represented by verticesArray[i].
	 */
	private RoutingGraph(int[] verticesArray, int[] edgeArray,
			double[] weightArray, MapPosition[] positionArray) {
		this.verticesArray = verticesArray;
		this.edgeArray = edgeArray;
		this.weightArray = weightArray;
		this.positionArray = positionArray;
		logger.info("Created a routing graph with " + getVerticesCount() + " vertices and " + edgeArray.length + " edges.");
	}

	/**
	 * Returns the integer-represented vertices connected to <code>center</code>.
	 * @param center The integer-represented vertice all returned vertices are connected to.
	 * @return The integer-represented vertices connected to center.
	 */
	int[] getNeighbours(int center) {
		int[] result = new int[verticesArray[center+1] - verticesArray[center]]; 
		for (int i = verticesArray[center]; i < verticesArray[center + 1]; i++) {
			result[i - verticesArray[center]] = edgeArray[i];
		}
		return result;
	}
	
	/**
	 * Returns the number of vertices.
	 * @return The number of vertices.
	 */
	int getVerticesCount() {
		return verticesArray.length - 1;
	}
	
	/**
	 * Returns the weight for the edge between v1 and v2.
	 * If no such edge exists, <code>Double.POSITIVE_INFINITY</code> is returned.
	 * @param v1 The first vertice.
	 * @param v2 The second vertice.
	 * @return The weight for the edge between v1 and v2.
	 */
	double getWeight(int v1, int v2) {
		double result = Double.POSITIVE_INFINITY;
		for (int i = verticesArray[v1]; i < verticesArray[v1 + 1]; i++) {
			if (edgeArray[i] == v2) {
				result = weightArray[i];
			}
		}
		return result;
	}
	
	/**
	 * Returns the integer-representation of the vertice next to the MapPosition <code>pos</code>.
	 * The resulting vertice lies on the same map as <code>pos</code>.
	 * @param pos The position the result should lie near.
	 * @return The integer-representation of the nearest vertice.
	 */
	int getNearestVertice(MapPosition pos) {
		int result = 0;
		boolean resultFound = false;
		for (int i = 0; i < getVerticesCount(); i++) {
			if (MapPosition.calculateDistance(positionArray[result], pos) > MapPosition
					.calculateDistance(positionArray[i], pos)) {
				result = i;
				resultFound = true;
			}
			
		}
		if (!resultFound) {
			logger.trace("result war -1");
			for (int i = 0; i < getVerticesCount(); i++) {
				if (WorldPosition.calculateDistance(positionArray[result], pos) > WorldPosition
						.calculateDistance(positionArray[i], pos)) {
					result = i;
				}				
			}
		}
		logger.trace("result " + result);
		return result;
	}
	
	/**
	 * Returns the position of the integer-represented vertice <code>vertice</code>.
	 * @param vertice The integer-representated vertice.
	 * @return The MapPosition of the vertice.
	 */
	MapPosition getPositionFromVertice(int vertice) {
		return positionArray[vertice];
	}
	
	private double sqr(double d) {
		return d*d;
	}

	/**
	 * Returns the only instance of RoutingGraph.
	 * @return The only instance of RoutingGraph.
	 */
	static RoutingGraph getInstance() {
		return instance;
	}
	
	/**
	 * Constructs a new adjacency array from the parameters.
	 * @param verticesArray The array of vertices.
	 * @param edgeArray The array of edges.
	 * @param weightArray The array representing the weight of an edge. weightArray[i] is the weight of the edge represented by edgeArray[i].
	 * @param positionArray The position of a vertice. positionArray[i] is the position of the vertice represented by verticesArray[i].
	 * @return The newly initialized RoutingGraph or null.
	 */
	static RoutingGraph initializeGraph(int[] verticesArray, int[] edgeArray, double[] weightArray, MapPosition[] positionArray) {
		if (instance == null) {
			instance = new RoutingGraph(verticesArray, edgeArray, weightArray, positionArray);
		}
		return instance;
	}
}
