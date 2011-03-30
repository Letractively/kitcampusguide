package mapAlgorithms;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.AStar;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * 
 * @author Monica
 * 
 *         This class test the component AStar. This algorithm should calculate
 *         a short distance between two nodes. The result is saved in the object
 *         Route.
 * 
 */
public class AStarTest {
	/*
	 * @BeforeClass public void beforeClass() {
	 * 
	 * }
	 * 
	 * @Before public void before() {
	 * 
	 * }
	 */
	/**
	 * This class checks if the singleton-object AStar is really created.
	 */
	@Test
	public void AStarObjectNotNullTest() {
		AStar aStar = AStar.getSingleton();
		Assert.assertNotNull(aStar);
		AStar newAStar = AStar.getSingleton();
		Assert.assertNotNull(newAStar);
		Assert.assertEquals(aStar, newAStar);
	}

	/**
	 * This method tests if by a small graph the algorithm is able so calculate
	 * a short path.
	 */
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

		Route route = aStar.calculateRoute(nodes[0], nodes[3], mapGraph);
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());
		Assert.assertEquals(3, route.getRoute().size());
		Assert.assertEquals(mapGraph.getNode(0), route.getRoute().get(0));
		Assert.assertEquals(mapGraph.getNode(2), route.getRoute().get(1));
		Assert.assertEquals(mapGraph.getNode(3), route.getRoute().get(2));

	}

	/**
	 * This method checks if the class can calculate the distance between points
	 * which have negative coordinates.
	 */
	@Test
	public void AStarWithNegativeCoordinatesTest() {
		AStar aStar = AStar.getSingleton();
		Graph mapGraph = new Graph();

		Point[] nodes = { new Point(5, 5), new Point(1, 0), new Point(-1, -1),
				new Point(1, 1) };

		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);

		mapGraph.addEdge(0, 1, 5.6);
		mapGraph.addEdge(1, 0, 5.6);

		mapGraph.addEdge(0, 3, 6.5);
		mapGraph.addEdge(3, 0, 6.5);

		mapGraph.addEdge(3, 1, 1);
		mapGraph.addEdge(1, 3, 1);

		mapGraph.addEdge(2, 1, 1.4);
		mapGraph.addEdge(1, 2, 1.4);

		Route route = aStar.calculateRoute(nodes[0], nodes[2], mapGraph);
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());
		Assert.assertEquals(3, route.getRoute().size());
		Assert.assertEquals(nodes[0], route.getRoute().get(0));
		Assert.assertEquals(nodes[1], route.getRoute().get(1));
		Assert.assertEquals(nodes[2], route.getRoute().get(2));
	}

	/**
	 * If the method calculateRoute of AStar gets <code>null</code> as
	 * parameters it should throw an exception.
	 */
	@Test
	public void AStarNullAsParametersTest() {
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

		boolean exceptionThrown = false;
		try {
			aStar.calculateRoute(null, null, mapGraph);
		} catch (NullPointerException e) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

	/**
	 * This method tests if an exceptions is thrown when the AStar should search
	 * a path between two nodes which don't exist in the graph.
	 */
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

		boolean exceptionThrown = false;
		try {
			aStar.calculateRoute(new Point(3, 3), nodes[1], mapGraph);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

	/**
	 * This method tests if a graph contains a branch, one smaller and one
	 * greater, but the path with the greater branch is shorter, the algorithm
	 * is able to calculate the right path.
	 */
	@Test
	public void AStarWithABranchTest() {
		AStar aStar = AStar.getSingleton();
		Graph mapGraph = new Graph();

		Point[] nodes = { new Point(0, 2), new Point(2, 2), new Point(1, 1),
				new Point(3, 4), new Point(0, 0) };

		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);
		mapGraph.addNode(nodes[4]);

		mapGraph.addEdge(0, 1, 1);
		mapGraph.addEdge(1, 0, 1);

		mapGraph.addEdge(2, 1, 2);
		mapGraph.addEdge(1, 2, 2);

		mapGraph.addEdge(0, 2, 2);
		mapGraph.addEdge(2, 0, 2);

		mapGraph.addEdge(3, 1, 2);
		mapGraph.addEdge(1, 3, 2);

		mapGraph.addEdge(2, 4, 3);
		mapGraph.addEdge(4, 2, 3);

		Route route = aStar.calculateRoute(nodes[3], nodes[4], mapGraph);
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());
		Assert.assertEquals(4, route.getRoute().size());
		Assert.assertEquals(nodes[3], route.getRoute().get(0));
		Assert.assertEquals(nodes[1], route.getRoute().get(1));
		Assert.assertEquals(nodes[2], route.getRoute().get(2));
		Assert.assertEquals(nodes[4], route.getRoute().get(3));
	}

	/**
	 * This method tests if a graph contains cycles the algorithm is able to
	 * calculate path.
	 */
	@Test
	public void AStarWithACycleTest() {
		AStar aStar = AStar.getSingleton();
		Graph mapGraph = new Graph();

		Point[] nodes = { new Point(0, 2), new Point(2, 2), new Point(1, 1),
				new Point(3, 4), new Point(0, 0) };

		mapGraph.addNode(nodes[0]);
		mapGraph.addNode(nodes[1]);
		mapGraph.addNode(nodes[2]);
		mapGraph.addNode(nodes[3]);
		mapGraph.addNode(nodes[4]);

		mapGraph.addEdge(0, 1, 1);
		mapGraph.addEdge(1, 0, 1);

		mapGraph.addEdge(2, 1, 3);
		mapGraph.addEdge(1, 2, 3);

		mapGraph.addEdge(0, 2, 1);
		mapGraph.addEdge(2, 0, 1);

		mapGraph.addEdge(3, 1, 2);
		mapGraph.addEdge(1, 3, 2);

		mapGraph.addEdge(2, 4, 3);
		mapGraph.addEdge(4, 2, 3);

		Route route = aStar.calculateRoute(nodes[3], nodes[4], mapGraph);
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());
		Assert.assertEquals(5, route.getRoute().size());
		Assert.assertEquals(nodes[3], route.getRoute().get(0));
		Assert.assertEquals(nodes[1], route.getRoute().get(1));
		Assert.assertEquals(nodes[0], route.getRoute().get(2));
		Assert.assertEquals(nodes[2], route.getRoute().get(3));
		Assert.assertEquals(nodes[4], route.getRoute().get(4));

	}
	/*
	 * @Test public void edgeTest() {
	 * 
	 * }
	 * 
	 * @After public void after() {
	 * 
	 * }
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */

}
