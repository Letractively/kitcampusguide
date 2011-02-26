package mapAlgorithms;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.Dijkstra;
import edu.kit.cm.kitcampusguide.mapAlgorithms.RouteCalculator;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * 
 * @author Monica
 *
 */
public class DijkstraTest {
	
	/*
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void before() {
		
	}
	*/
	@Test
	public void routeCalculatingTest() {

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

		RouteCalculator dijkstra = Dijkstra.getSingleton();
		Assert.assertNotNull(dijkstra);
		List<Point> route = dijkstra.calculateRoute(nodes[0], nodes[3],
				mapGraph).getRoute();
		Assert.assertEquals(3, route.size());
		Assert.assertEquals(nodes[0], route.get(0));
		Assert.assertEquals(nodes[2], route.get(1));
		Assert.assertEquals(nodes[3], route.get(2));

	}

	@Test
	public void emptyRouteTest() {
		Graph mapGraph = new Graph();
		RouteCalculator dijkstra = Dijkstra.getSingleton();
		Assert.assertNotNull(dijkstra);
		List<Point> route = dijkstra.calculateRoute(null, null, mapGraph)
				.getRoute();
		Assert.assertEquals(0, route.size());

	}

	@Test
	public void onePointTest() {
		Graph mapGraph = new Graph();
		RouteCalculator dijkstra = Dijkstra.getSingleton();
		Assert.assertNotNull(dijkstra);
		Point point = new Point(0, 0);
		mapGraph.addNode(point);
		List<Point> route = dijkstra.calculateRoute(point, point, mapGraph)
				.getRoute();
		Assert.assertEquals(1, route.size());
	}

	@Test
	public void onePointButNotAddedInGraphTest() {
		Graph mapGraph = new Graph();
		RouteCalculator dijkstra = Dijkstra.getSingleton();
		Assert.assertNotNull(dijkstra);
		Point[] nodes = { new Point(0, 0), new Point(1, 0), new Point(0, 1),
				new Point(1, 1) };
		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);

		boolean wentInTry = false;
		
		try {
			List<Point> route = dijkstra.calculateRoute(new Point(2, 2),
					new Point(2, 3), mapGraph).getRoute();
		} catch (ArrayIndexOutOfBoundsException e) {
			wentInTry = true;
		}
		Assert.assertTrue(wentInTry);
	}
	
	@Test
	public void noRouteToNode() {
		Graph mapGraph = new Graph();
		RouteCalculator dijkstra = Dijkstra.getSingleton();
		Assert.assertNotNull(dijkstra);
		Point[] nodes = { new Point(0, 0), new Point(1, 0), new Point(0, 1),
				new Point(1, 1) };
		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);
		List<Point> route = dijkstra.calculateRoute(nodes[0], nodes[3],
				mapGraph).getRoute();
		Assert.assertEquals(0, route.size());

	}	

	/*
	 * 
	 * 
	 * }
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
