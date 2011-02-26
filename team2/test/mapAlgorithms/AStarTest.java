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
	public void AStarRouteTest() {
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
