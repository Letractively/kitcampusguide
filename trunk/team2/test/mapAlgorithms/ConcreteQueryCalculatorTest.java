package mapAlgorithms;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteQueryCalculator;
import edu.kit.cm.kitcampusguide.mapAlgorithms.QueryCalculator;
import edu.kit.cm.kitcampusguide.model.POI;

/**
 * 
 * @author Monica
 * 
 */
public class ConcreteQueryCalculatorTest {
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
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("Hö");
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals(3, suggestions.get(0).getId());
		Assert.assertEquals(4, suggestions.get(1).getId());
		Assert.assertEquals(1, suggestions.get(2).getId());
	}

	@Test
	public void getSuggestionsWithEmptyListTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("Y");
		Assert.assertEquals(0, suggestions.size());

	}

	@Test
	public void getSuggestionsWithOneLetter() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("G");
		Assert.assertEquals(1, suggestions.size());
		Assert.assertEquals(1, suggestions.get(0).getId());
	}
	
	@Test
	public void getSuggestionsWithEmptyString() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("");
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(0, suggestions.size());
	
	}

	
	@Test
	public void getSuggestionsNamesTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<String> suggestions = queryCalculator.getSuggestionsNames("Hö");

		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals("Audimax", suggestions.get(0));
		Assert.assertEquals("Hörsaal am Fasanengarten", suggestions.get(1));
		Assert.assertEquals("Gerthsen Hörsaal", suggestions.get(2));
	}

	@Test
	public void searchPOITest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		Assert.assertEquals(3, queryCalculator.searchPOI("Audi").getId());
	}

	@Test
	public void searchPOIWithEmptyStringTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();

		Assert.assertNull(queryCalculator.searchPOI(""));
	}

	/*
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
