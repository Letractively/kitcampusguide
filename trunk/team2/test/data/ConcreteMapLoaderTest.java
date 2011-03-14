package data;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * 
 * @author Michael Hauber
 *
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
	 * TODO JavaDoc
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
