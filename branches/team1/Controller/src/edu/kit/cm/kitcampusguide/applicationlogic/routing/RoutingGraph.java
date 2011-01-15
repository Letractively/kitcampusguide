package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.*;

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
	 * Extracts the RoutingGraph from a file with the osm-properties using the standard Map <code>map</code>.
	 */
	private RoutingGraph(String filename, Map map) {
		try {
			logger.info("Constructing routing graph from " + filename + " with standard map ID " + map.getID());
			Document document = new SAXBuilder().build(filename);
			constructGraph(document, map);
			logger.info("Constructed routing graph. " + getVerticesCount() + " vertices. " + edgeArray.length + " edges.");
		} catch (JDOMException e) {
			logger.fatal("Construction of routing graph failed.");
			e.printStackTrace();
		} catch (IOException e) {
			logger.fatal("Construction of routing graph failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs the graph from the Document <code>document</code>, uses the standard-map <code>map</code>.
	 * @param document The Document the graph is being constructed from. 
	 * @param map The Map used for a vertice if no other is specified.
	 */
	private void constructGraph(Document document, Map map) {
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
						Map poiMap = map;
						if (tmp.getAttribute("mapID") != null) {
							poiMap = Map.getMapByID(Integer.parseInt(tmp.getAttributeValue("mapID")));
						}
						positionList.add(new MapPosition(lat, lon, poiMap));
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

	/**
	 * Constructs the weights for all edges.
	 */
	private void constructWeights() {
		weightArray = new double[edgeArray.length];
		for (int i = 0; i < getVerticesCount(); i++) {
			int[] neighbours = getNeighbours(i);
			for (int j = 0; j < neighbours.length; j++) {
				weightArray[verticesArray[i] + j] = calculateDistance(getPositionFromVertice(i), getPositionFromVertice(j));
			}
		}
		
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
		for (int i = 0; i < getVerticesCount(); i++) {
			if (positionArray[i].getMap() == pos.getMap()) {
				if (calculateDistance(positionArray[result], pos) > calculateDistance(positionArray[i], pos)) {
					result = i;
				}
			}
			
		}
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
	
	/**
	 * Calculates the distance between the positions pos1 and pos2.
	 * @param pos1 The first position.
	 * @param pos2 The second position.
	 * @return The distance between pos1 and pos2.
	 */
	//TODO: CHANGE TO PRIVATE
	public double calculateDistance(MapPosition pos1, MapPosition pos2) {
		double result = Double.POSITIVE_INFINITY;
		if (pos1.getMap().equals(pos2.getMap())) {
			result = sqr(pos1.getLatitude() - pos2.getLatitude());
			result += sqr(pos1.getLongitude() - pos2.getLongitude());
		}
		return result;
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
	 * Initializes the RoutingGraph from the file <code>file</code>, if no RoutingGraph exists.
	 * If a node has no <code>mapID</code>-attribute in the xml-file, the Map <code>map</code> will be used.
	 * All maps referenced in the xml-file must exist already. 
	 * The file referenced by <code>filename</code> has to be in the osm-format.
	 * @param filename The path to the xml-file.
	 * @param map The standard map.
	 * @return The new initialized RoutingGraph or the already existing.
	 */
	static RoutingGraph initializeGraph(String filename, Map map) {
		if (instance == null) {
			instance = new RoutingGraph(filename, map);
		}
		return instance;
	}
}
