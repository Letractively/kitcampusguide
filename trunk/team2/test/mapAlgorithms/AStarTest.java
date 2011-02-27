package mapAlgorithms;


import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.AStar;
import edu.kit.cm.kitcampusguide.mapAlgorithms.RouteCalculatingUtility;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * 
 * @author Monica
 *
 */
public class AStarTest {
	/*
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void before() {
		
	}
	*/
	@Test
	public void AStarRouteWithMapTest() {
		AStar aStar = AStar.getSingleton();
		Graph graph= RouteCalculatingUtility.MAP_LOADER.getGraph();
		Point pointFrom = graph.getNode(8);
		Point pointTo = graph.getNode(12);
		
		Route testRoute = aStar.calculateRoute(pointFrom, pointTo, graph);
		Assert.assertNotNull(testRoute);
		Assert.assertEquals(5, testRoute.getRoute().size());
		Assert.assertEquals(graph.getNode(8), testRoute.getRoute().get(0));
		Assert.assertEquals(graph.getNode(9), testRoute.getRoute().get(1));
		Assert.assertEquals(graph.getNode(10), testRoute.getRoute().get(2));
		Assert.assertEquals(graph.getNode(11), testRoute.getRoute().get(3));
		Assert.assertEquals(graph.getNode(12), testRoute.getRoute().get(4));
	}
	
	@Test
	public void AStarCalculateRouteTest() {
		AStar aStar = AStar.getSingleton();
		Graph mapGraph = new Graph();

		Point[] nodes = { new Point(0, 0), new Point(1, 0), new Point(0, 1),
				new Point(1, 1) };
		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);
		mapGraph.addEdge(0, 1, 2);
		mapGraph.addEdge(1, 0, 3);
		mapGraph.addEdge(1, 2, 5);
		mapGraph.addEdge(2, 1, 5);
		mapGraph.addEdge(2, 3, 3);
		mapGraph.addEdge(3, 2, 3);
		mapGraph.addEdge(0, 2, 5);
		mapGraph.addEdge(2, 0, 5);

		Route route=aStar.calculateRoute(nodes[0], nodes[3], mapGraph);
		Assert.assertEquals(3, route.getRoute().size());
		Assert.assertEquals(mapGraph.getNode(0), route.getRoute().get(0));
		Assert.assertEquals(mapGraph.getNode(2), route.getRoute().get(1));
		Assert.assertEquals(mapGraph.getNode(3), route.getRoute().get(2));
		
	}

	//TODO für pointFrom nicht im Graphen enthalten
	@Test
	public void AStarPointFromNotInGraphTest() {
		AStar aStar = AStar.getSingleton();
		Graph mapGraph = new Graph();

		Point[] nodes = { new Point(0, 0), new Point(1, 0), new Point(0, 1),
				new Point(1, 1) };
		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);
		mapGraph.addEdge(0, 1, 2);
		mapGraph.addEdge(1, 0, 3);
		mapGraph.addEdge(1, 2, 5);
		mapGraph.addEdge(2, 1, 5);
		mapGraph.addEdge(2, 3, 3);
		mapGraph.addEdge(3, 2, 3);
		mapGraph.addEdge(0, 2, 5);
		mapGraph.addEdge(2, 0, 5);

	//	aStar.calculateRoute(new Point(3,3), nodes[1], mapGraph);
	}
	
	
	
	/*
	@Test
	public void edgeTest() {
		
	}
	
	@After
	public void after() {
		
	}
	
	@AfterClass
	public void afterClass() {
		
	}
	*/
	
}
