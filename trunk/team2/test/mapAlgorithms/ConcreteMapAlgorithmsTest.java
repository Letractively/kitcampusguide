package mapAlgorithms;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * 
 * @author Monica
 * 
 */
public class ConcreteMapAlgorithmsTest {
	/*
	 * @BeforeClass public void beforeClass() {
	 * 
	 * }
	 * 
	 * @Before public void before() {
	 * 
	 * }
	 */
	@Test
	public void getSuggestionsTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertEquals(3, concreteMapAlgorithms.getSuggestions("H").size());
		Assert.assertEquals(4, concreteMapAlgorithms.getSuggestions("H").get(0)
				.getId());
		Assert.assertEquals(3, concreteMapAlgorithms.getSuggestions("H").get(1)
				.getId());
		Assert.assertEquals(1, concreteMapAlgorithms.getSuggestions("H").get(2)
				.getId());
	}

	@Test
	public void getSuggestionsWithEmptyStringTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertEquals(0, concreteMapAlgorithms.getSuggestions("").size());
	}

	@Test
	public void searchPOIWithIncorrectStringTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertNull(concreteMapAlgorithms.searchPOI("ABC"));
	}

	@Test
	public void searchPOITest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertEquals(1, concreteMapAlgorithms.searchPOI("Ger").getId());
	}

	@Test
	public void calculateRouteForSavedPOISTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		MapLoader mapLoader = new ConcreteMapLoader();
		Point pointFrom = mapLoader.getGraph().getNode(5);
		Point pointTo = mapLoader.getGraph().getNode(10);
		Route route = concreteMapAlgorithms.calculateRoute(pointFrom, pointTo);
		Assert.assertNotNull(route);
		Assert.assertEquals(5, route.getRoute().size());
		Assert.assertEquals(pointFrom, route.getRoute().get(0));
		Assert.assertEquals(mapLoader.getGraph().getNode(7), route.getRoute()
				.get(1));
		Assert.assertEquals(mapLoader.getGraph().getNode(8), route.getRoute()
				.get(2));
		Assert.assertEquals(mapLoader.getGraph().getNode(9), route.getRoute()
				.get(3));
		Assert.assertEquals(pointTo, route.getRoute().get(4));

	}
	@Test
	public void calculateRouteForNotSavedPoisTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		MapLoader mapLoader = new ConcreteMapLoader();
		
		Point pointFrom = new Point(8.41600, 49.01250);
		Point pointTo = new Point(8.4170, 49.01240);
		Route route = concreteMapAlgorithms.calculateRoute(pointFrom, pointTo);
		Assert.assertNotNull(route);
	
		Assert.assertEquals(6, route.getRoute().size());
		Assert.assertEquals(pointFrom, route.getRoute().get(0));
		Assert.assertEquals(mapLoader.getGraph().getNode(2), route.getRoute()
				.get(1));
		Assert.assertEquals(mapLoader.getGraph().getNode(4), route.getRoute()
				.get(2));
		Assert.assertEquals(mapLoader.getGraph().getNode(5), route.getRoute()
				.get(3));
		Assert.assertEquals(mapLoader.getGraph().getNode(7), route.getRoute()
				.get(4));
		Assert.assertEquals(pointTo, route.getRoute().get(5));

	}

	
	/*
	 * 
	 * 
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
