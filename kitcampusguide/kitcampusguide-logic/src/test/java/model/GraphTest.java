package model;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * 
 * @author Monica
 * @author Tobias
 * 
 *         This class tests the object Graph.
 */
public class GraphTest {

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
	 * This class tests if the graph is created correctly by trying to add a new
	 * graph without parameters, but by adding with help of the methods addNode
	 * and addEdge
	 */
	@Test
	public void graphBuildingTest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0),
				new Point(1.0, 1.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);

		Assert.assertNotNull(testGraph);
		Assert.assertEquals(4, testGraph.numberOfNodes());
		Assert.assertArrayEquals(testGraph.getNodes(), nodes);

		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 3, 5);
		testGraph.addEdge(2, 3, 8);
		testGraph.addEdge(1, 3, 6);
		testGraph.addEdge(0, 2, 4);
		testGraph.addEdge(3, 2, 7);
		Assert.assertEquals(4, testGraph.numberOfNodes());

		Assert.assertArrayEquals(testGraph.getNodes(), nodes);

		Assert.assertEquals(nodes[2], testGraph.getNode(2));

		Assert.assertTrue(testGraph.getEdge(0, 3) == 5);
		Assert.assertTrue(testGraph.getEdge(1, 3) == 6);
		Assert.assertTrue(testGraph.getEdge(3, 2) == 7);
	}

	/**
	 * This method tests if by adding negative weights to an edge, the addEdge
	 * method throws an exception.
	 */
	@Test
	public void addToGraphNegativeWeights() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);

		boolean illegalArgumentExcThrown = false;
		try {
			testGraph.addEdge(0, 1, -1);
		} catch (IllegalArgumentException e) {
			illegalArgumentExcThrown = true;
		}
		Assert.assertTrue(illegalArgumentExcThrown);
	}

	/**
	 * This tests if it is possible and works correctly if you add a point with
	 * negative and double type coordinates.
	 */
	@Test
	public void negativeAndNonIntegerCoordinatestest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(-1.0, 1.0), new Point(29.1, 0.0),
				new Point(0.0, 1.0), new Point(1.0, 1.0) };
		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);

		Assert.assertEquals(4, testGraph.numberOfNodes());
		Assert.assertArrayEquals(testGraph.getNodes(), nodes);
	}

	/**
	 * This method tests if the getEdge works correctly.
	 */
	@Test
	public void getEdgeMethodTest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0),
				new Point(1.0, 1.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);

		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 3, 5);
		testGraph.addEdge(2, 3, 8);
		testGraph.addEdge(1, 3, 6);
		testGraph.addEdge(0, 2, 4);
		testGraph.addEdge(3, 2, 7);

		Assert.assertTrue(1 == testGraph.getEdge(0, 0));
		Assert.assertTrue(2 == testGraph.getEdge(0, 1));
		Assert.assertTrue(3 == testGraph.getEdge(1, 0));
		Assert.assertTrue(5 == testGraph.getEdge(0, 3));
		Assert.assertTrue(8 == testGraph.getEdge(2, 3));
		Assert.assertTrue(6 == testGraph.getEdge(1, 3));
		Assert.assertTrue(4 == testGraph.getEdge(0, 2));
		Assert.assertTrue(7 == testGraph.getEdge(3, 2));

		Assert.assertTrue(Double.isNaN(testGraph.getEdge(3, 0)));

		boolean exceptionWasThrownFromNode = false;

		try {
			testGraph.getEdge(-1, 2);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrownFromNode = true;
		}
		Assert.assertTrue(exceptionWasThrownFromNode);

		boolean exceptionWasThrownToNode = false;

		try {
			testGraph.getEdge(1, -1);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrownToNode = true;
		}
		Assert.assertTrue(exceptionWasThrownToNode);

		boolean exceptionWasThrownFromNode2 = false;

		try {
			testGraph.getEdge(5, 2);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrownFromNode2 = true;
		}
		Assert.assertTrue(exceptionWasThrownFromNode2);

		boolean exceptionWasThrownToNode2 = false;

		try {
			testGraph.getEdge(0, 5);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrownToNode2 = true;
		}
		Assert.assertTrue(exceptionWasThrownToNode2);
	}

	/**
	 * This method checks if the method getNode() correct works.
	 */
	@Test
	public void getNodeMethodTest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0),
				new Point(1.0, 1.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);

		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(1, 0, 3);

		Assert.assertEquals(nodes[1], testGraph.getNode(1));

		boolean exceptionWasThrown1 = false;

		try {
			testGraph.getNode(-1);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrown1 = true;
		}
		Assert.assertTrue(exceptionWasThrown1);

		boolean exceptionWasThrown2 = false;

		try {
			testGraph.getNode(4);
		} catch (IndexOutOfBoundsException e) {
			exceptionWasThrown2 = true;
		}
		Assert.assertTrue(exceptionWasThrown2);
	}

	/**
	 * This tests the method getNodeDegree()
	 */
	@Test
	public void getNodeDegreeTest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);

		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 2, 4);

		Assert.assertEquals(3, testGraph.getNodeDegree(0));
		Assert.assertEquals(1, testGraph.getNodeDegree(1));
		Assert.assertEquals(0, testGraph.getNodeDegree(2));

		boolean excThrown1 = false;
		try {
			testGraph.getNodeDegree(-1);
		} catch (IndexOutOfBoundsException e) {
			excThrown1 = true;
		}
		Assert.assertTrue(excThrown1);

		boolean excThrown2 = false;
		try {
			testGraph.getNodeDegree(3);
		} catch (IndexOutOfBoundsException e) {
			excThrown2 = true;
		}
		Assert.assertTrue(excThrown2);
	}

	/**
	 * This tests the method neighboursOf.
	 */
	@Test
	public void neighboursOfMethodTest() {
		Graph testGraph = new Graph();
		Point[] nodes = { new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0) };

		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);

		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 2, 4);
		Integer[] neighboursOfZeroNode = { 2, 1, 0 };
		int i = 0;
		for (Integer currentNeighbour : testGraph.neighboursOf(0)) {
			Assert.assertEquals(neighboursOfZeroNode[i], currentNeighbour);
			i++;
		}

		boolean exceptionThrown1 = false;
		try {
			testGraph.neighboursOf(-1);
		} catch (IllegalArgumentException e) {
			exceptionThrown1 = true;
		}
		Assert.assertTrue(exceptionThrown1);

		boolean exceptionThrown2 = false;

		try {
			testGraph.neighboursOf(3);
		} catch (IllegalArgumentException e) {
			exceptionThrown2 = true;
		}
		Assert.assertTrue(exceptionThrown2);
	}

	/**
	 * This tests if building a graph by using as parameters a list of points,
	 * nodes, edges and length of edges.
	 */
	@Test
	public void graphBuildingWithListsTest() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0), new Point(1.0, 0.0),
				new Point(1.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);
		pointList.add(points[2]);
		pointList.add(points[3]);

		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);
		lengthList.add(6.0);
		lengthList.add(4.0);
		lengthList.add(8.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		edgeList.add(1);
		edgeList.add(2);
		edgeList.add(3);
		edgeList.add(3);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(0);
		nodeList.add(1);
		nodeList.add(3);
		nodeList.add(4);
		nodeList.add(4);

		Graph graph = new Graph(pointList, lengthList, nodeList, edgeList);

		Assert.assertArrayEquals(points, graph.getNodes());
		Assert.assertTrue(graph.getEdge(0, 1) == 2.0);
		Assert.assertTrue(graph.getEdge(1, 2) == 6.0);
		Assert.assertTrue(graph.getEdge(1, 3) == 4.0);
		Assert.assertTrue(graph.getEdge(2, 3) == 8.0);
	}

	/**
	 * This tests if the edge-list and length-list not the same length having, the 
	 * constructor throws an exception.
	 */
	@Test
	public void lengthAndEdgeListSizeTest() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(0);
		nodeList.add(1);
		nodeList.add(1);

		
		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();

		boolean lengthAndEdgeListsExcThrown = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown);
	}
	
	/**
	 * This checks if the length of nodes and pointLists + 1 not equal is, it throws an exception.
	 */
	@Test
	public void lengthOfNodeAndPointList() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(1);
		nodeList.add(1);

		
		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		edgeList.add(1);

		boolean lengthAndEdgeListsExcThrown = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown);
	}
	
	/**
	 * This checks if the last value of the last node of the list incorrect is, a graph 
	 * isn't built, but throws an exception.
	 */
	@Test
	public void incorrectNodeListTest() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(0);
		nodeList.add(1);
		nodeList.add(3);

		
		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		edgeList.add(1);

		boolean lengthAndEdgeListsExcThrown = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown);
	}
	

	/**
	 * If the edge list contains invalid values an exception should be thrown.
	 */
	@Test
	public void edgeListWithNonExistingNode() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(0);
		nodeList.add(1);
		nodeList.add(1);

		
		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		edgeList.add(5);

		ArrayList<Integer> edgeList2 = new ArrayList<Integer>();
		edgeList.add(-1);

		boolean lengthAndEdgeListsExcThrown = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown);
	
		boolean lengthAndEdgeListsExcThrown2 = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList2);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown2 = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown2);
	
	}

	/**
	 * 
	 */
	@Test
	public void nodeListWithIncorrectValuesTest() {

		Point[] points = { new Point(0.0, 0.0), new Point(0.0, 1.0) };
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(points[0]);
		pointList.add(points[1]);

		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		nodeList.add(3);
		nodeList.add(1);
		nodeList.add(1);

		ArrayList<Integer> nodeList2 = new ArrayList<Integer>();
		nodeList2.add(-1);
		nodeList2.add(1);
		nodeList2.add(1);

		
		ArrayList<Double> lengthList = new ArrayList<Double>();
		lengthList.add(2.0);

		ArrayList<Integer> edgeList = new ArrayList<Integer>();
		edgeList.add(1);

		boolean lengthAndEdgeListsExcThrown = false;
		try {
			new Graph(pointList, lengthList, nodeList, edgeList);
		} catch (IllegalArgumentException e) {
			lengthAndEdgeListsExcThrown = true;
		}
		Assert.assertTrue(lengthAndEdgeListsExcThrown);
	
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