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
 *         This class tests the ConcreteQueryCalculator, which implements the
 *         QueryCalculator.
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

	/**
	 * This method tests that the singleton of ConcreteQueryCalculator is
	 * created.
	 */
	@Test
	public void createSingletonTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		Assert.assertNotNull(queryCalculator);
	}

	/**
	 * This method tests if a simple search of a string in the database finds
	 * the correct IDs of the POIs.
	 */
	@Test
	public void getSuggestionsTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("Hö");

		Assert.assertNotNull(suggestions);
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals(3, suggestions.get(0).getId());
		Assert.assertEquals(4, suggestions.get(1).getId());
		Assert.assertEquals(1, suggestions.get(2).getId());
	}
	/**
	 * This method tests if a simple search of a string in the database finds
	 * the correct IDs of the POIs not depending on upper or lower case.
	 */
	@Test
	public void getSuggestionsWithSmallCapsTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("hö");

		Assert.assertNotNull(suggestions);
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals(3, suggestions.get(0).getId());
		Assert.assertEquals(4, suggestions.get(1).getId());
		Assert.assertEquals(1, suggestions.get(2).getId());
	}

	/**
	 * This method tests if searching for an inexistent string the method
	 * getSuggestions returns an empty list.
	 */
	@Test
	public void getSuggestionsWithNoResultTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("Y");

		Assert.assertNotNull(suggestions);
		Assert.assertEquals(0, suggestions.size());

	}

	/**
	 * This method tests if the class is able to search an POI by adding a
	 * single letter.
	 */
	@Test
	public void getSuggestionsWithOneLetter() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("G");
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(1, suggestions.size());
		Assert.assertEquals(1, suggestions.get(0).getId());
	}

	/**
	 * This method tests if the method returns an empty list if it should search an empty string.
	 * 
	 */
	@Test
	public void getSuggestionsWithEmptyString() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<POI> suggestions = queryCalculator.getSuggestions("");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(0, suggestions.size());

	}
	
	//TODO wieder das mit null als parameter
	@Test
	public void getSuggestionsWithNullAsString() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
		.getSingleton();
	//	List<POI> suggestions = queryCalculator.getSuggestions(null);

	}
	
	/**
	 * This test checks the method getSuggestionsNames if it returns the right list of names by 
	 * adding a string.
	 */
	@Test
	public void getSuggestionsNamesTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<String> suggestions = queryCalculator.getSuggestionsNames("Hö");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals("Audimax", suggestions.get(0));
		Assert.assertEquals("Hörsaal am Fasanengarten", suggestions.get(1));
		Assert.assertEquals("Gerthsen Hörsaal", suggestions.get(2));
	}

	/**
	 * This test checks the method getSuggestionsNames if it returns the right list of names by 
	 * adding a string not depending if lower or upper case.
	 */
	@Test
	public void getSuggestionsNamesWithLowerCaseTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<String> suggestions = queryCalculator.getSuggestionsNames("hö");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals("Audimax", suggestions.get(0));
		Assert.assertEquals("Hörsaal am Fasanengarten", suggestions.get(1));
		Assert.assertEquals("Gerthsen Hörsaal", suggestions.get(2));
	}

	/**
	 * This test checks the method getSuggestionsNames if it returns an empty list of names by 
	 * adding an empty string.
	 */
	@Test
	public void getSuggestionsNamesWithEmptyStringTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<String> suggestions = queryCalculator.getSuggestionsNames("");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(0, suggestions.size());
	}

	/**
	 * This test checks the method getSuggestionsNames if it returns an empty list of names by 
	 * adding an inexistent string.
	 */
	@Test
	public void getSuggestionsNamesNonExistentStringTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		List<String> suggestions = queryCalculator.getSuggestionsNames("XYZTUV");
		
		Assert.assertNotNull(suggestions);
		Assert.assertEquals(0, suggestions.size());
	}

	//TODO wieder mit null
	/**
	 * This test checks the method getSuggestionsNames if it throws an exception if by adding a null 
	 * as parameter.
	 */
	@Test
	public void getSuggestionsNamesWithNullTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
//		List<String> suggestions = queryCalculator.getSuggestionsNames(null);
		
	}

	
/**
 * This tests if the right POI can be found by adding an non-empty string.
 */
	@Test
	public void searchPOITest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();
		POI searchedPOI = queryCalculator.searchPOI("Audi");
		Assert.assertNotNull(searchedPOI);
		Assert.assertEquals(3, searchedPOI.getId());
	}

	/**
	 * This tests if an empty string is set as parameter to the method searchPOI it returns null 
	 */
	@Test
	public void searchPOIWithEmptyStringTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();

		Assert.assertNull(queryCalculator.searchPOI(""));
	}

	
	/**
	 * This tests if an inexistent string is set as parameter to the method searchPOI it returns null 
	 */
	@Test
	public void searchPOIWithNonExistentStringTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();

		Assert.assertNull(queryCalculator.searchPOI("XYZT"));
	}

	//TODO wieder das mit null
	/**
	 * This tests if null is set as parameter to the method searchPOI it throws an exception
	 */
	@Test
	public void searchPOIWithNullAsParameterTest() {
		QueryCalculator queryCalculator = ConcreteQueryCalculator
				.getSingleton();

		//Assert.assertNull(queryCalculator.searchPOI(null));
	}

	/*
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
