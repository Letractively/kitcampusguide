package data;

import static org.junit.Assert.fail;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;

/**
 * This class tests the ConcretePOILoader.
 * 
 * @author Michael Hauber
 */
public class ConcretePOILoaderTest {


	/*
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void before() {
		
	}
	*/
	
	
	/**
	 * This tests the getters of the ConcreteMapLoader for exceptions.
	 */
	@Test
	public void testExceptionsofGetter() {
		POILoader poiloader = new ConcretePOILoader();
		
	    try {
	        final int negativeId = -2;
	        poiloader.getPOI(negativeId);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        poiloader.getPOI(null);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
		
	    try {
	        final String emptyString = "";
	        poiloader.getPOIsByName(emptyString);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        poiloader.getPOIsByName(null);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
		
	    try {
	        final int negativeId = -2;
	        poiloader.getPOICategory(negativeId);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        poiloader.getPOICategory(null);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        final String emptyString = "";
	        poiloader.getPOICategoryByName(emptyString);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        poiloader.getPOICategoryByName(null);
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
