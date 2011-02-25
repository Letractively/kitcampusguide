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
	public void AStarRouteTest() {
		Point from = new Point(0,0);
		Point to = new Point(1,1);
		Graph graph = new Graph();
		Point[] nodes = {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)};
		graph.addNode(nodes[0]);
		graph.addNode(nodes[1]);
		graph.addNode(nodes[2]);
		graph.addNode(nodes[3]);
		Route testRoute = AStar.getSingleton().calculateRoute(from, to, graph);
		Assert.assertNotNull(testRoute);
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
