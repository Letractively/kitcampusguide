package mapAlgorithms;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;

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
		Assert.assertEquals(4, concreteMapAlgorithms.getSuggestions("H").get(0).getId());
		Assert.assertEquals(3, concreteMapAlgorithms.getSuggestions("H").get(1).getId());
		Assert.assertEquals(1, concreteMapAlgorithms.getSuggestions("H").get(2).getId());
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
//TODO
	public void calculateRouteTest() {
	MapAlgorithms concreteMapAlgorithms = new ConcreteMapAlgorithms();
	}
	
	/*
	 * 
	 * 
	 * }
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
