package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import edu.kit.cm.kitcampusguide.standardtypes.*;

class RoutingGraph {
	
	/** Stores the vertices and the corresponding edgeArray-positions. Required to be verticesCount + 1 dummy element*/
	public int[] verticesArray; //TODO: Change back to private (all)
	public int[] edgeArray;
	public double[] weightArray;
	public MapPosition[] positionArray;
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
		return verticesArray.length - 1;
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
	 * 
	 * @param pos
	 * @return
	 */
	int getNearestVertice(MapPosition pos) {
		int result = 0;
		for (int i = 0; i < getVerticesCount(); i++) {
			if (calculateDistance(positionArray[result], pos) > calculateDistance(positionArray[i], pos)) {
				result = i;
			}
		}
		return result;
	}
	
	MapPosition getPositionFromVertice(int vertice) {
		return positionArray[vertice];
	}
	
	public double calculateDistance(MapPosition pos1, MapPosition pos2) {
		double result = Double.POSITIVE_INFINITY;
		if (pos1.getMap().equals(pos2.getMap())) {
			result = sqr(pos1.getLatitude() - pos2.getLatitude());
			result += sqr(pos1.getLongitude() - pos2.getLongitude());
			result = Math.sqrt(result);
		}
		return result;
	}
	
	private double sqr(double number) {
		return number * number;
	}
	
	static RoutingGraph getInstance() {
		if (instance == null) {
			instance = new RoutingGraph();
		}
		return instance;
	}
}
