package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

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
	private RoutingGraph(String filename) {
		try {
			Document document = new SAXBuilder().build(filename);
			constructGraph(document);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void constructGraph(Document document) {
		Map main = new Map(0, "main");
		Element root = document.getRootElement();
		List<Element> wayList = root.getChildren("way");
		List<MapPosition> positionList = new ArrayList<MapPosition>();
		HashMap<Integer, Element> allNodes = new HashMap<Integer, Element>();
		List<Element> nodes = root.getChildren("node");
		for (Element e : nodes) {
			allNodes.put(new Integer(e.getAttributeValue("id")), e);
		}
		HashMap<Integer, Integer> nodeList = new HashMap<Integer, Integer>();
		int counter = 0;
		for (Element currentWay : wayList) {
			boolean isHighway = false;
			try {
				isHighway = currentWay.getChild("tag").getAttribute("k").getValue().equalsIgnoreCase("highway");
			} catch(NullPointerException e) {
				
			}
			if (isHighway) {
				List<Element> currentWayNodes = currentWay.getChildren("nd");
				for (Element node : currentWayNodes) {
					int nodeToBeChecked = Integer.parseInt(node.getAttributeValue("ref"));
					if (!nodeList.containsKey(nodeToBeChecked)) {
						nodeList.put(Integer.parseInt(node.getAttributeValue("ref")), new Integer(counter));
						Element tmp = allNodes.get(Integer.parseInt(node.getAttributeValue("ref")));
						double lon = Double.parseDouble(tmp.getAttributeValue("lon"));
						double lat = Double.parseDouble(tmp.getAttributeValue("lat"));
						positionList.add(new MapPosition(lon, lat, main));
						counter++;
					}
				}
			}
			
		}
		List<Integer>[] edgesLists = new ArrayList[nodeList.size()];
		for (int i = 0; i < nodeList.size(); i++) {
			edgesLists[i] = new ArrayList<Integer>();
		}
		for (Element currentWay : wayList) {
			boolean isHighway = false;
			try {
				isHighway = currentWay.getChild("tag").getAttribute("k").getValue().equalsIgnoreCase("highway");
			} catch(NullPointerException e) {
				
			}
			if (isHighway) {
				List<Element> nodeListOfCurrentWay = currentWay.getChildren("nd");
				for (int i = 0; i < nodeListOfCurrentWay.size() - 1; i++) {
					Integer from = new Integer(nodeListOfCurrentWay.get(i).getAttributeValue("ref")); 
					Integer to = new Integer(nodeListOfCurrentWay.get(i + 1).getAttributeValue("ref"));
					edgesLists[nodeList.get(from)].add(nodeList.get(to));
					edgesLists[nodeList.get(to)].add(nodeList.get(from));
				}
			}
		}
		List<Integer> verticesList = new ArrayList<Integer>();
		List<Integer> edgeList = new ArrayList<Integer>();
		for (int i = 0; i < edgesLists.length; i++) {
			verticesList.add(new Integer(edgeList.size()));
			edgeList.addAll(edgesLists[i]);
		}
		verticesList.add(edgeList.size());
		verticesArray = new int[verticesList.size()];
		for (int i = 0; i < verticesList.size(); i++) {
			verticesArray[i] = verticesList.get(i).intValue();
		}
		edgeArray = new int[edgeList.size()];
		for (int i = 0; i < edgeList.size(); i++) {
			edgeArray[i] = edgeList.get(i).intValue();
		}
		positionArray = new MapPosition[positionList.size()];
		for (int i = 0; i < positionArray.length; i++) {
			positionArray[i] = positionList.get(i);
		}
		
		constructWeights();
	}

	private void constructWeights() {
		weightArray = new double[edgeArray.length];
		for (int i = 0; i < getVerticesCount(); i++) {
			int[] neighbours = getNeighbours(i);
			for (int j = 0; j < neighbours.length; j++) {
				weightArray[verticesArray[i] + j] = calculateDistance(getPositionFromVertice(i), getPositionFromVertice(j));
			}
		}
		
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
	
	private double calculateDistance(MapPosition pos1, MapPosition pos2) {
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
		return instance;
	}
	
	static RoutingGraph initializeGraph(String filename) {
		if (instance == null) {
			instance = new RoutingGraph(filename);
		}
		return instance;
	}
}
