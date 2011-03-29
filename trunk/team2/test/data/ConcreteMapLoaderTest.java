package data;

import static org.junit.Assert.fail;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;

/**
 * This class tests the ConcreteMapLoader.
 * 
 * @author Michael Hauber
 */
public class ConcreteMapLoaderTest {
	
	
	/*
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void before() {
		
	}
	*/

	/**
	 * This tests the methods addStreetToDatabase(...) and addLandmarkToDatabase(...)
	 * with illegal parameters and tests the exceptions.
	 */
	@Test
	public void testExceptionsofaddStreet() {
        MapLoader maploader = new ConcreteMapLoader();
		
	    try {
	        final int negativeId = -2;
	        maploader.addStreetToDatabase(negativeId, 1, 1);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        final int negativeId = -2;
	        maploader.addStreetToDatabase(1, negativeId, 1);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
		
	    try {
	        final double negativeLength = -.04;
	        maploader.addStreetToDatabase(1, 1, negativeLength);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }

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
