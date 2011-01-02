package edu.kit.cm.kitcampusguide.ApplicationLogic.Routing;
import java.util.Collections;
import java.util.List;

import edu.kit.cm.kitcampusguide.standardtypes.*;

class DijkstraRouting {
	private static DijkstraRouting instance;
	
	private RoutingGraph routingGraph;
	/**
	 * Private constructor.
	 */
	private DijkstraRouting() {
		routingGraph = RoutingGraph.getInstance();
	}
	
	Route calculateRoute(MapPosition from, MapPosition to) {
		int fromVertice = routingGraph.getNearestVertice(from);
		int toVertice = routingGraph.getNearestVertice(to);
		int currentVertice = fromVertice;
		int visitedVerticesCount = 0;
		Integer[] previous = new Integer[routingGraph.getVerticesCount()];
		for (int i = 0; i < routingGraph.getVerticesCount(); i++) {
			previous[i] = null;
		}
		double[] distance = new double[routingGraph.getVerticesCount()];
		for (int i = 0; i < routingGraph.getVerticesCount(); i++) {
			distance[i] = Double.POSITIVE_INFINITY;
		}
		distance[fromVertice] = 0;
		
		boolean[] visited = new boolean[routingGraph.getVerticesCount()];
		for (int i = 0; i < routingGraph.getVerticesCount(); i++) {
			visited[i] = false;
		}
		boolean finished = false;
		while (finished == false){
			int[] currentNodeNeighbours = routingGraph.getNeighbours(currentVertice);
			for (int vertice: currentNodeNeighbours) {
				double tentativeDistance = distance[currentVertice] + routingGraph.getWeight(currentVertice, vertice);
				if (tentativeDistance < distance[vertice]) {
					distance[vertice] = tentativeDistance;
					previous[vertice] = currentVertice;
				}
			}
			visited[currentVertice] = true;
			visitedVerticesCount++;
			
			if (visitedVerticesCount == routingGraph.getVerticesCount()) {
				finished = true;
			} else {
				for (int i = 0; i < routingGraph.getVerticesCount(); i++) {
					if (visited[i] == false) {
						currentVertice = i;
					}
				}
				for (int i = currentVertice; i < routingGraph.getVerticesCount(); i++) {
					if (distance[i] < distance[currentVertice]) {
						currentVertice = i;
					}
				}
			}
		}
		List<MapPosition> waypoints = Collections.emptyList();
		Integer tmp = toVertice;
		while(previous[tmp] != null) {
			waypoints.add(routingGraph.getPositionFromVertice(tmp));
			tmp = previous[tmp];
		}
		Collections.reverse(waypoints);
		return new Route(waypoints);
	}
	
	static DijkstraRouting getInstance() {
		if (instance == null) {
			instance = new DijkstraRouting();
		}
		return instance;
	}
}
