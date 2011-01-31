package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * 
 * @author Fabian
 *
 */
public class RoutingGraphTest {
	
	private static RoutingGraph testGraph;
	private static int verticesCount;

	/**
	 * Creates an instance of {@link RoutingGraph} to run the tests.
	 * The Graph has the following edges (start -> end|weight):
	 * 0 -> 2|1;
	 * 1 -> 0|2;
	 * 1 -> 2|3;
	 * 1 -> 3|1.2;
	 * 1 -> 4|1.5;
	 * 2 -> 0|1;
	 * 2 -> 1|3;
	 * 2 -> 3|1;
	 * 3 -> 1|1.2;
	 * 3 -> 2|1;
	 * 4 -> 1|1.5;
	 * 4 -> 3|2.5;
	 * 
	 * This concludes the following arrays:
	 * vertices: {0, 1, 5, 8, 10, 12, 12}
	 * edges: {2, 0, 2, 3, 4, 0, 1, 3, 1, 2, 1, 3}
	 * weights: {1, 2, 3, 1.2, 1.5, 1, 3, 1, 1.2, 1, 1.5, 2.5}
	 * 
	 * MapPositions: { (1|0|1), (1|2|1), (0|0|1), (0|2|1), (1.5|2.5|1), (1.5|2.5|2) }
	 * (longitude | latitude | MapID)
	 * Note: There is no dependency between MapPositions and weights.
	 */
	@BeforeClass
	public static void setUpTestData() {
		int[] verticesArray = {0, 1, 5, 8, 10, 12, 12};
		verticesCount = verticesArray.length - 1;
		int[] edgeArray = {2, 0, 2, 3, 4, 0, 1, 3, 1, 2, 1, 3};
		double[] weightArray = {1, 2, 3, 1.2, 1.5, 1, 3, 1, 1.2, 1, 1.5, 2.5};
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = new WorldPosition(3, 3);
		MapSection boundingBox = new MapSection(pos1, pos2);
		Map rootmap = new Map(1, "rootmap", boundingBox , "null", 0, 0);
		Map map2 = new Map(2, "map", boundingBox , "null", 0, 0);
		MapPosition[] positionArray = {	new MapPosition(1, 0, rootmap), 
										new MapPosition(1, 2, rootmap), 
										new MapPosition(0, 0, rootmap),
										new MapPosition(0, 2, rootmap),
										new MapPosition(1.5, 2.5, rootmap),
										new MapPosition(1.5, 2.5, map2)};
		RoutingGraph.initializeGraph(verticesArray, edgeArray, weightArray, positionArray);
		testGraph = RoutingGraph.getInstance();
	}

	/**
	 * Tests if there reference is the same.
	 */
	@Test
	public void singletonTest() {
		Assert.assertSame(testGraph, RoutingGraph.getInstance());
	}
	
	/**
	 * Tests if {@link RoutingGraph#getNeighbours(int)} returns the correct array of adjacent nodes
	 */
	@Test
	public void getNeighbours() {
		Assert.assertArrayEquals("Neighbours of node 1 incorrect", new int[] {0, 2, 3, 4}, testGraph.getNeighbours(1));
		Assert.assertArrayEquals("Neighbours of node 0 incorrect", new int[] {2}, testGraph.getNeighbours(0));
	}
	
	/**
	 * Tests if {@link RoutingGraph#getVerticesCount()} works as expected.
	 */
	@Test
	public void getVerticesCount() {
		Assert.assertEquals(verticesCount, testGraph.getVerticesCount());
	}
	
	/**
	 * Tests if {@link RoutingGraph#getWeight(int, int)} works as expected.
	 */
	@Test
	public void getWeight() {
		double delta = 0.000000001;
		Assert.assertTrue(Double.isInfinite(testGraph.getWeight(0, 4)));
		Assert.assertEquals(1.5, testGraph.getWeight(1, 4), delta);
		Assert.assertEquals(2, testGraph.getWeight(1, 0), delta);
		Assert.assertTrue(Double.isInfinite(testGraph.getWeight(0, 1)));
	}
	
	/**
	 * Tests if {@link RoutingGraph#getNearestVertice(MapPosition)} works as expected when
	 * the map of {@link MapPosition} is shared with at least one MapPosition in the
	 * positionArray of RoutingGraph.
	 */
	@Test
	public void getNearestVerticeOnMap() {
		Map map = Map.getMapByID(1);
		MapPosition pos1 = new MapPosition(1.5, 2.5, map);
		Assert.assertEquals(4, testGraph.getNearestVertice(pos1));
		MapPosition pos2 = new MapPosition(0.5, 1, map);
		Assert.assertEquals(0, testGraph.getNearestVertice(pos2));
		Map map2 = Map.getMapByID(2);
		MapPosition pos3 = new MapPosition(1.5, 2.5, map2);
		Assert.assertEquals(5, testGraph.getNearestVertice(pos3));
	}
	
	/**
	 * Tests if {@link RoutingGraph#getNearestVertice(MapPosition)} works as expected when
	 * the map of {@link MapPosition} is shared with no MapPosition in the
	 * positionArray of RoutingGraph. 
	 */
	@Test
		public void getNearestVerticeOffMap() {
		Map map0 = new Map(0,"0",new MapSection(new WorldPosition(1, 1), new WorldPosition(2, 2)), "0", 1, 1);
		MapPosition pos3 = new MapPosition(0.5, 1, map0);
		Assert.assertEquals(0, testGraph.getNearestVertice(pos3));
	}
	
	/**
	 * Tests if {@link RoutingGraph#getPositionFromVertice(int)} works as expected.
	 */
	@Test
	public void getPositionFromVertice() {
		Assert.assertEquals(new MapPosition(1.5, 2.5, Map.getMapByID(1)), testGraph.getPositionFromVertice(4));
	}
}
