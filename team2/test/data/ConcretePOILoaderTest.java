package data;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

/**
 * 
 * @author Michael Hauber
 *
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
	 * getPOI(Integer id)
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
