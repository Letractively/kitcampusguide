package mapAlgorithms;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * 
 * @author Monica
 * 
 *         This class tests the component ConcreteMapAlgorithms, which
 *         implements MapAlgorithm.
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

	/**
	 * This method tests a simple search in the database with one letter.
	 */
	@Test
	public void getSuggestionsTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		List<POI> suggestions = concreteMapAlgorithms.getSuggestions("H");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals(4, suggestions.get(0).getId());
		Assert.assertEquals(3, suggestions.get(1).getId());
		Assert.assertEquals(1, suggestions.get(2).getId());
	}

	/**
	 * This method tests if an empty string is set as parameter.
	 */
	@Test
	public void getSuggestionsWithEmptyStringTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
	
		List<POI> suggestions = concreteMapAlgorithms.getSuggestions("");
		
		Assert.assertEquals(4, suggestions.size());

		Assert.assertEquals(2, suggestions.get(0).getId());
		Assert.assertEquals(1, suggestions.get(1).getId());
		Assert.assertEquals(4, suggestions.get(2).getId());
		Assert.assertEquals(3, suggestions.get(3).getId());

	}

	/**
	 * This method tests if the string isn't in the database, there aren't
	 * suggestions returned.
	 */
	@Test
	public void getSuggestionsWithInexistantStringTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertEquals(0, concreteMapAlgorithms.getSuggestions("ABC")
				.size());
	}

	/**
	 * This method tests if the string isn't in the database, there isn't a POI
	 * returned.
	 */
	@Test
	public void searchPOIWithInexistantStringTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		Assert.assertNull(concreteMapAlgorithms.searchPOI("ABC"));
	}

	/**
	 * This method searches simple a POI with a specific string. It should find a single POI.
	 */
	@Test
	public void searchPOITest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		POI searchedPOI=concreteMapAlgorithms.searchPOI("Ger");
		Assert.assertNotNull(searchedPOI);
		Assert.assertEquals(1, searchedPOI.getId());
	}

	/**
	 * This method tests if by setting two points as parameters in the method calculateRoute() 
	 * it returns a correct route between the 2 points. Both points are included in the database.
	 */
	@Test
	public void calculateRouteForSavedPOISTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		MapLoader mapLoader = new ConcreteMapLoader();
		Point pointFrom = mapLoader.getGraph().getNode(5);
		Point pointTo = mapLoader.getGraph().getNode(10);
		Route route = concreteMapAlgorithms.calculateRoute(pointFrom, pointTo);

		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());

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

	/**
	 * This method tests if by setting two points as parameters in the method calculateRoute() 
	 * it returns a correct route between the 2 points. Neither of the points are included in the database.
	 */
	
	@Test
	public void calculateRouteForNotSavedPoisTest() {
		MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
		MapLoader mapLoader = new ConcreteMapLoader();

		Point pointFrom = new Point(8.41600, 49.01250);
		Point pointTo = new Point(8.4170, 49.01240);
		Route route = concreteMapAlgorithms.calculateRoute(pointFrom, pointTo);

		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getRoute());

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
