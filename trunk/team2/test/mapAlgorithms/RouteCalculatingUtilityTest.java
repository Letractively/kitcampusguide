package mapAlgorithms;

import org.junit.BeforeClass;
import org.junit.Test;

import sun.org.mozilla.javascript.internal.Node;

import edu.kit.cm.kitcampusguide.data.MapLoader;
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
public class RouteCalculatingUtilityTest {
	
	  @BeforeClass 
	  public void beforeClass() {
		  RouteCalculatingUtility.MAP_LOADER = new MapLoader() {
			
			@Override
			public Point[] getLandmarks() {
			Point[] landmarks;
				return null;
			}
			
			@Override
			public double[][] getLandmarkDistances() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Graph getGraph() {
				return null;
			}
			
			@Override
			public void addStreetToDatabase(int fromId, int toId, double length) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int addStreetNodeToDatabase(double latitude, double longitude) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void addLandmarkToDatabase(Point landmark, double[] distances) {
				// TODO Auto-generated method stub
				
			}
		};
	  }
	 
	/*
	 * @Before public void before() {
	 * 
	 * }
	 */

	@Test
	public void AStarRouteWithMapTest() {
		
		Graph graph = RouteCalculatingUtility.MAP_LOADER.getGraph();

		Point[] nodes = { graph.getNodes()[0], graph.getNodes()[1],
				graph.getNodes()[2], graph.getNodes()[3], graph.getNodes()[4],
				graph.getNodes()[5] };
		Graph solutionGraph = RouteCalculatingUtility
				.calculateStreetGraph(nodes);
		System.out.print(solutionGraph.getEdge(0, 1));
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
