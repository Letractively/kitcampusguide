package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * Initializes the Routing sub system.
 * @author Fred
 *
 */
public class RoutingInitializer {
	/** The logger for this class.*/
	private Logger logger = Logger.getLogger(getClass()); 
	
	int[] verticesArray;
	int[] edgeArray;
	MapPosition[] positionArray;
	double[] weightArray;
	
	/** The only instance of RoutingInitializer*/
	private static RoutingInitializer instance = null;
	
	/**
	 * Initializes the subsystem Routing
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization.
	 */
	private RoutingInitializer(InputStream inputStream) throws InitializationException {
		try {
			logger.info("Initializing Routing from " + inputStream);
			Document document;
			document = new SAXBuilder().build(inputStream);
			RoutingStrategyImpl.getInstance();
			FacesContext context = FacesContext.getCurrentInstance();
			Element e = document.getRootElement().getChild("RoutingGraph");
			initializeRoutingGraph(context.getExternalContext().getResourceAsStream(e.getAttributeValue("filename")), Map.getMapByID(Integer.parseInt(e.getAttributeValue("standardMapID"))));
			DijkstraRouting.getInstance();
			logger.info("Routing initialized.");
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of routing failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of routing failed.", e);
		}
		
	}
	
	/**
	 * Initializes the RoutingGraph from the file <code>file</code>, if no RoutingGraph exists.
	 * If a node has no <code>mapID</code>-attribute in the xml-file, the Map <code>map</code> will be used.
	 * All maps referenced in the xml-file must exist already. 
	 * The file referenced by <code>filename</code> has to be in the osm-format.
	 * @param inputStream The path to the xml-file.
	 * @param map The standard map.
	 */
	private void initializeRoutingGraph(InputStream inputStream, Map map) {
		try {
			logger.info("Constructing routing graph from " + inputStream + " with standard map ID " + map.getID());
			Document document = new SAXBuilder().build(inputStream);
			constructGraph(document, map);
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
		//initializes the required local variables.
		Element root = document.getRootElement();
		List<Element> wayList = root.getChildren("way");
		List<MapPosition> positionList = new ArrayList<MapPosition>();
		HashMap<Integer, Element> allNodes = new HashMap<Integer, Element>();
		
		//Fills the HashMap allNodes with all node elements.
		List<Element> nodes = root.getChildren("node");
		for (Element e : nodes) {
			allNodes.put(new Integer(e.getAttributeValue("id")), e);
		}
		
		//Constructs the nodes via searching ways with the "highway" tag.
		//The Map nodeList maps the ref node-id of the osm representation to the counter which 
		//will later be equivalent to the node id inside the routing graph.
		HashMap<Integer, Integer> nodeList = new HashMap<Integer, Integer>();
		int counter = 0;
		for (Element currentWay : wayList) {
			//Tests if the currently watched way has the highway tag.
			boolean isHighway = false;
			try {
				List<Element> tags = currentWay.getChildren("tag");
				for (Element tag : tags) {
					if (tag.getAttribute("k").getValue().equalsIgnoreCase("highway")) {
						isHighway = true;
						break;
					}
				}
			} catch(NullPointerException e) {
				
			}
			//if it has the highway attribute, all elements (= nodes) will be adressed separately
			if (isHighway) {
				List<Element> currentWayNodes = currentWay.getChildren("nd");
				for (Element node : currentWayNodes) {
					//Checks the id of the node, if it is already contained in nodeList.
					int nodeToBeChecked = Integer.parseInt(node.getAttributeValue("ref"));
					if (!nodeList.containsKey(nodeToBeChecked)) {
						//If it isn't contained, it is put in nodeList, and the position is constructed. 
						nodeList.put(Integer.parseInt(node.getAttributeValue("ref")), new Integer(counter));
						Element tmp = allNodes.get(Integer.parseInt(node.getAttributeValue("ref")));
						double lon = Double.parseDouble(tmp.getAttributeValue("lon"));
						double lat = Double.parseDouble(tmp.getAttributeValue("lat"));
						//If the node has no "mapID" attribute (custom added, not part of osm), the standard map
						//given by the parameter is used.
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
		
		//The edges are constructed.
		List<Integer>[] edgesLists = new ArrayList[nodeList.size()];
		for (int i = 0; i < nodeList.size(); i++) {
			edgesLists[i] = new ArrayList<Integer>();
		}
		for (Element currentWay : wayList) {
			//by looking if the way has the tag highway
			boolean isHighway = false;
			try {
				List<Element> tags = currentWay.getChildren("tag");
				for (Element tag : tags) {
					if (tag.getAttribute("k").getValue().equalsIgnoreCase("highway")) {
						isHighway = true;
						break;
					}
				}
			} catch(NullPointerException e) {
				
			}
			if (isHighway) {
				//And if so, adding the respectively other node to the nodes from and which the way originates.
				List<Element> nodeListOfCurrentWay = currentWay.getChildren("nd");
				for (int i = 0; i < nodeListOfCurrentWay.size() - 1; i++) {
					Integer from = new Integer(nodeListOfCurrentWay.get(i).getAttributeValue("ref")); 
					Integer to = new Integer(nodeListOfCurrentWay.get(i + 1).getAttributeValue("ref"));
					edgesLists[nodeList.get(from)].add(nodeList.get(to));
					edgesLists[nodeList.get(to)].add(nodeList.get(from));
				}
			}
		}
		//The list of vertices and list of edges are constructed.
		List<Integer> verticesList = new ArrayList<Integer>();
		List<Integer> edgeList = new ArrayList<Integer>();
		for (int i = 0; i < edgesLists.length; i++) {
			//first by concatenating the edgeList and constructing the vertice list.
			verticesList.add(new Integer(edgeList.size()));
			edgeList.addAll(edgesLists[i]);
		}
		//and the dummy element.
		verticesList.add(edgeList.size());
		
		//And lastly converting all lists to arrays.
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
		
		//And constructing the weights, by calculating the distance between the two vertices.
		weightArray = new double[edgeArray.length];
		for (int i = 0; i < verticesArray.length - 1; i++) {
			int[] neighbours = getNeighbours(i);
			for (int j = 0; j < neighbours.length; j++) {
				weightArray[verticesArray[i] + j] = calculateDistance(positionArray[i], positionArray[j]);
			}
		}
		//And initializing the RoutingGraph object.
		RoutingGraph.initializeGraph(verticesArray, edgeArray, weightArray, positionArray);
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
	 * Calculates the distance between the positions pos1 and pos2.
	 * @param pos1 The first position.
	 * @param pos2 The second position.
	 * @return The distance between pos1 and pos2.
	 */
	private double calculateDistance(MapPosition pos1, MapPosition pos2) {
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
	 * Initializes the routing sub system if no such initialization has been attempted before.
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occurred during initialization. 
	 */
	public static void initialize(InputStream inputStream) throws InitializationException {
		if (instance == null) {
			instance = new RoutingInitializer(inputStream);
		}
	}
}
