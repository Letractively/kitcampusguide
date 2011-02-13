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
 * Specifically initializes the routing graph and makes sure all components have been initialized.
 * @author Fred
 *
 */
public class RoutingInitializer {
	/** The logger for this class.*/
	private Logger logger = Logger.getLogger(getClass()); 
	
	/** The arrays necessary to construct a routing graph*/
	private int[] verticesArray;
	private int[] edgeArray;
	private MapPosition[] positionArray;
	private double[] weightArray;
	
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
			FacesContext context = FacesContext.getCurrentInstance();
			Element e = document.getRootElement().getChild("RoutingGraph");
			initializeRoutingGraph(context.getExternalContext().getResourceAsStream(e.getAttributeValue("filename")), Map.getMapByID(Integer.parseInt(e.getAttributeValue("standardMapID"))));
			PreCalculatedRouting.getInstance();
			RoutingStrategyImpl.getInstance();
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
			//if the way is acceptable, all elements (= nodes) will be adressed separately
			if (isAcceptableWay(currentWay)) {
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
			//by looking if the way is acceptable
			if (isAcceptableWay(currentWay)) {
				if (isArea(currentWay)) {
					//If the way represents an area, edges are added between all vertices of this way
					List<Element> nodeListOfCurrentWay = currentWay.getChildren("nd");
					for (int i = 0; i < nodeListOfCurrentWay.size(); i++) {
						Integer from = new Integer(nodeListOfCurrentWay.get(i).getAttributeValue("ref"));
						for (int j = 0; j < nodeListOfCurrentWay.size(); j++) {
							Integer to = new Integer(nodeListOfCurrentWay.get(j).getAttributeValue("ref"));
							edgesLists[nodeList.get(from)].add(nodeList.get(to));
						}
					}
				} else {
					//If the way is a normal way, only the adjacent vertices share an edge.
					List<Element> nodeListOfCurrentWay = currentWay.getChildren("nd");
					for (int i = 0; i < nodeListOfCurrentWay.size() - 1; i++) {
						Integer from = new Integer(nodeListOfCurrentWay.get(i).getAttributeValue("ref")); 
						Integer to = new Integer(nodeListOfCurrentWay.get(i + 1).getAttributeValue("ref"));
						edgesLists[nodeList.get(from)].add(nodeList.get(to));
						edgesLists[nodeList.get(to)].add(nodeList.get(from));
					}
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
				weightArray[verticesArray[i] + j] = MapPosition
						.calculateDistance(positionArray[i], positionArray[neighbours[j]]);
			}
		}
		//And initializing the RoutingGraph object.
		RoutingGraph.initializeGraph(verticesArray, edgeArray, weightArray, positionArray);
	}
	
	private boolean isArea(Element e) {
		boolean result = false;
		try {
			List<Element> tags = e.getChildren("tag");
			for (Element tag : tags) {
				if (tag.getAttributeValue("k").equalsIgnoreCase("area") && tag.getAttributeValue("v").equalsIgnoreCase("yes")) {
					logger.info("area found");
					result = true;
					break;
				}
			}
		} catch(NullPointerException e1) {
			
		}
		return result;
	}

	/**
	 * Returns the integer-represented vertices connected to <code>center</code>.
	 * @param center The integer-represented vertice all returned vertices are connected to.
	 * @return The integer-represented vertices connected to center.
	 */
	private int[] getNeighbours(int center) {
		int[] result = new int[verticesArray[center+1] - verticesArray[center]]; 
		for (int i = verticesArray[center]; i < verticesArray[center + 1]; i++) {
			result[i - verticesArray[center]] = edgeArray[i];
		}
		return result;
	}
	
	private boolean isAcceptableWay(Element e) {
		boolean result = false;
		try {
			List<Element> tags = e.getChildren("tag");
			for (Element tag : tags) {
				String tagV = tag.getAttributeValue("v");
				if ((tagV.equalsIgnoreCase("footway")) || (tagV.equalsIgnoreCase("path")) || (tagV.equalsIgnoreCase("cycleway")) || (tagV.equalsIgnoreCase("steps")) || (tagV.equalsIgnoreCase("pedestrian"))) {
					result = true;
					break;
				}
			}
		} catch(NullPointerException e1) {
			
		}
		return result;
	}
	
	/**
	 * Initializes the routing sub system if no such initialization has been attempted before.
	 * No other initialization needs to be completed before the call of this function.
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
