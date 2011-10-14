package mapAlgorithms;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.mapAlgorithms.RouteCalculatingUtility;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * 
 * 
 * @author Monica
 * 
 *         This class tests if the RouteCalculatingUtility works correctly.
 */
public class RouteCalculatingUtilityTest {

	private static MapLoader mapLoader;

	/**
	 * In this method a mapLoader is initialized.
	 */
	@BeforeClass
	public static void beforeClass() {
		mapLoader = new MapLoader() {
			private Point[] points = { new Point(0.0, 0.0), new Point(1.0, 0.0),
					new Point(0.0, 1.0), new Point(1.0, 1.0), new Point(2.0, 3.0) };

			private Graph graph = null;

			@Override
			public Point[] getLandmarks() {
				return points;
			}

			@Override
			public double[][] getLandmarkDistances() {
				// doesn't need to be implemented
				return new double[0][0];
			}

			@Override
			public Graph getGraph() {
					graph = new Graph();
					graph.addNode(points[0]);
					graph.addNode(points[1]);
					graph.addNode(points[2]);
					graph.addNode(points[3]);
					graph.addNode(points[4]);

					graph.addEdge(0, 2, new Double(111319.49079326246));
					graph.addEdge(2, 3, new Double(111302.53586526687));
					graph.addEdge(3, 4, new Double(248885.09440349854));
					graph.addEdge(1, 4, new Double(352007.05038511823));
				return graph;
			}

			@Override
			public void addLandmarkToDatabase(Point landmark, double[] distances) {
				// doesn't need to be implemented
			}
		};
	}

	/*
	 * @Before public void before() {
	 * 
	 * }
	 */

	/**
	 * This tests if the method calculateStreetGraph with one point creates a
	 * correct graph.
	 */
	@Test
	public void calculateStreetGraphWithOnePoint() {

		RouteCalculatingUtility.setMapLoader(mapLoader);
		Graph graph = RouteCalculatingUtility.getMapLoader().getGraph();
		Graph streetGraph = RouteCalculatingUtility
				.calculateStreetGraph(new Point(5.0, 6.0));

		Assert.assertEquals(6, streetGraph.numberOfNodes());
		Assert.assertTrue(111319.49079326246 - streetGraph.getEdge(0, 2) == 0);

		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 1)));

		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(0)), 0);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(1)), 1);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(2)), 2);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(3)), 3);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(4)), 4);

		Assert.assertTrue(streetGraph.getEdge(4, 5) - 471533.69059003337 == 0);
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(1, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(2, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(3, 5)));

	}

	/**
	 * This tests if the method calculateStreetGraph with one point creates a
	 * correct graph.
	 */
	@Test
	public void calculateStreetGraphWithMorePoints() {

		RouteCalculatingUtility.setMapLoader(mapLoader);
		Graph graph = RouteCalculatingUtility.getMapLoader().getGraph();
		Graph streetGraph = RouteCalculatingUtility.calculateStreetGraph(
				new Point(5.0, 6.0), new Point(10.0, 10.0));

		Assert.assertEquals(7, streetGraph.numberOfNodes());
		Assert.assertTrue(111319.49079326246 - streetGraph.getEdge(0, 2) == 0);

		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 1)));

		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(0)), 0);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(1)), 1);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(2)), 2);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(3)), 3);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(4)), 4);

		Assert.assertTrue(streetGraph.getEdge(4, 5) - 471533.69059003337 == 0);
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(1, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(2, 5)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(3, 5)));

		Assert.assertTrue(streetGraph.getEdge(6, 4) - 1178603.8835872342 == 0);
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 6)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(1, 6)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(2, 6)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(3, 6)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(5, 6)));
	}

	/**
	 * This tests if the method calculateStreetGraph with one point, which is is
	 * already in the graph creates a correct street-graph.
	 */
	@Test
	public void calculateStreetGraphWithPointInGraph() {

		RouteCalculatingUtility.setMapLoader(mapLoader);
		Graph graph = RouteCalculatingUtility.getMapLoader().getGraph();
		Graph streetGraph = RouteCalculatingUtility.calculateStreetGraph(new Point(0.0, 0.0));

		Assert.assertEquals(5, streetGraph.numberOfNodes());
		Assert.assertTrue(111319.49079326246 - streetGraph.getEdge(0, 2) == 0);

		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(0)), 0);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(1)), 1);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(2)), 2);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(3)), 3);
		Assert.assertEquals(streetGraph.getNodeIndex(graph.getNode(4)), 4);

		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 1)));

		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 3)));
		Assert.assertTrue(Double.isNaN(streetGraph.getEdge(0, 4)));

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
