package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

public class GraphTest {
	
	/*
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void before() {
		
	}
	*/
	@Test
	public void GraphBuildingTest() {
		Graph testGraph = new Graph();
		Point[] nodes = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)};
		testGraph.addNode(nodes[0]);
		testGraph.addNode(nodes[1]);
		testGraph.addNode(nodes[2]);
		testGraph.addNode(nodes[3]);
		Assert.assertTrue(testGraph.numberOfNodes() == 4);
		Assert.assertArrayEquals(testGraph.getNodes(),nodes);
		testGraph.addEdge(0, 0, 1);
		testGraph.addEdge(0, 1, 2);
		testGraph.addEdge(1, 0, 3);
		testGraph.addEdge(0, 3, 5);
		testGraph.addEdge(2, 3, 8);
		testGraph.addEdge(1, 3, 6);
		testGraph.addEdge(0, 2, 4);
		testGraph.addEdge(3, 2, 7);
		Assert.assertTrue(testGraph.numberOfNodes() == 4);
		Assert.assertArrayEquals(testGraph.getNodes(),nodes);
		Assert.assertTrue(testGraph.getNode(2) == nodes[2]);
		Assert.assertArrayEquals(testGraph.getNeighbours(0), new int[] {2, 3, 1, 0});
		Assert.assertArrayEquals(testGraph.getNeighbours(1), new int[] {3, 0});
		Assert.assertArrayEquals(testGraph.getNeighbours(2), new int[] {3});
		Assert.assertArrayEquals(testGraph.getNeighbours(3), new int[] {2});
		Assert.assertTrue(testGraph.getEdge(0, 3) == 5);
		Assert.assertTrue(testGraph.getEdge(1, 3) == 6);
		Assert.assertTrue(testGraph.getEdge(3, 2) == 7);
	}
	
	
	//TODO negative weights... exception
	
	
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
