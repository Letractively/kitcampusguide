package mapAlgorithms;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteQueryCalculator;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
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
		QueryCalculator queryCalculator = ConcreteQueryCalculator.getSingleton();
		List<POI> suggestions=queryCalculator.getSuggestions("H�");
		Assert.assertEquals(3, suggestions.size());
		Assert.assertEquals(3, suggestions.get(0).getId());
		Assert.assertEquals(4, suggestions.get(1).getId());
		Assert.assertEquals(1, suggestions.get(2).getId());
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
