package data;

import static org.junit.Assert.fail;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.DatabaseUtility;

/**
 * This class tests the DatabaseUtility class.
 * @author Michael Hauber
 *
 */
public class DatabaseUtilityTest {
	/**
	 * This tests the methods addStreetToDatabase(...) and addLandmarkToDatabase(...)
	 * with illegal parameters and tests the exceptions.
	 */
	@Test
	public void testExceptionsofaddStreet() {
	    try {
	        final int negativeId = -2;
	        DatabaseUtility.addStreetToDatabase(negativeId, 1, 1);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
	    
	    try {
	        final int negativeId = -2;
	        DatabaseUtility.addStreetToDatabase(1, negativeId, 1);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }
		
	    try {
	        final double negativeLength = -.04;
	        DatabaseUtility.addStreetToDatabase(1, 1, negativeLength);
	        fail("Should have raised an IllegalArgumentException");
	    } catch (IllegalArgumentException expected) {
	    }

	}
}
