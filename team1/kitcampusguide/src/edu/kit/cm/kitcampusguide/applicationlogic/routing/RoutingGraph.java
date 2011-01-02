package edu.kit.cm.kitcampusguide.ApplicationLogic.Routing;
import edu.kit.cm.kitcampusguide.standardtypes.*;

class RoutingGraph {
	
	/** Stores the vertices and the corresponding edgeArray-positions. Required to be verticesCount + 1 dummy element*/
	private int[] verticesArray;
	private int[] edgeArray;
	private double[] weightArray;
	private MapPosition[] positionArray;
	private static RoutingGraph instance;
	
	/**
	 * CURRENTLY UNIMPLEMENTED; needs to extract the routing graph from a file.
	 */
	private RoutingGraph() {
		
	}
	
	int[] getNeighbours(int center) {
		int[] result = new int[verticesArray[center+1] - verticesArray[center]]; 
		for (int i = verticesArray[center]; i < verticesArray[center + 1]; i++) {
			result[i - verticesArray[center]] = edgeArray[i];
		}
		return result;
	}
	
	int getVerticesCount() {
		return verticesArray.length;
	}
	
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
	 * CURRENTLY UNIMPLEMENTED
	 * @param pos
	 * @return
	 */
	int getNearestVertice(MapPosition pos) {
		return 0;
	}
	
	MapPosition getPositionFromVertice(int vertice) {
		return positionArray[vertice];
	}
	
	static RoutingGraph getInstance() {
		if (instance == null) {
			instance = new RoutingGraph();
		}
		return instance;
	}
}
