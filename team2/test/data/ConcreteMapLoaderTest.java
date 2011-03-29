package data;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * This class tests the ConcreteMapLoader.
 * 
 * @author Michael Hauber
 */
public class ConcreteMapLoaderTest {

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
	
	/**
	 * This test tests the method getGraph() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetGraph() {
		Point[] expectedNodes = { new Point(8.41518, 49.01252), 
				new Point(8.41555, 49.0125), 
				new Point(8.41599, 49.01249), 
				new Point(8.41597, 49.01229), 
				new Point(8.41617, 49.01247), 
				new Point(8.41618, 49.01255), 
				new Point(8.41621, 49.01282), 
				new Point(8.41701, 49.01251), 
				new Point(8.41718, 49.01258), 
				new Point(8.41729, 49.01256), 
				new Point(8.4174, 49.01243), 
				new Point(8.41754, 49.01241), 
				new Point(8.41784, 49.01234), 
				new Point(8.41803, 49.01233), 
				new Point(8.41782, 49.01212), 
				new Point(8.41692, 49.01215), 
				new Point(8.41619, 49.01218), 
				new Point(8.41615, 49.01226), 
				new Point(8.41663, 49.0115), 
				new Point(8.41663, 49.01108), 
				new Point(8.41698, 49.01108), 
				new Point(8.41681, 49.0115), 
				new Point(8.41688, 49.01184), 
				new Point(8.41697, 49.0115), 
				new Point(8.41697, 49.01125), 
				new Point(8.41735, 49.01125), 
				new Point(8.41735, 49.01105), 
				new Point(8.41735, 49.01082), 
				new Point(8.41791, 49.01124), 
				new Point(8.4178, 49.0108), 
				new Point(8.41807, 49.01116), 
				new Point(8.416, 49.01086), 
				new Point(8.4156, 49.01087), 
				new Point(8.41508, 49.0109), 
				new Point(8.41482, 49.01092), 
				new Point(8.4193, 49.011), 
				new Point(8.4152, 49.011), 
				new Point(8.41548, 49.01103), 
				new Point(8.41571, 49.0111), 
				new Point(8.41592, 49.01121), 
				new Point(8.41605, 49.01135), 
				new Point(8.41578, 49.01161), 
				new Point(8.41578, 49.01149), 
				new Point(8.41569, 49.01136), 
				new Point(8.41552, 49.01128), 
				new Point(8.4152, 49.01127), 
				new Point(8.41483, 49.01126), 
				new Point(8.41479, 49.0116), 
				new Point(8.41502, 49.0116), 
				new Point(8.41506, 49.01178), 
				new Point(8.41515, 49.01191), 
				new Point(8.41532, 49.012), 
				new Point(8.4156, 49.01198), 
				new Point(8.41576, 49.01185), 
				new Point(8.41487, 49.012), 
				new Point(8.41488, 49.01225), 
				new Point(8.41492, 49.01258), 
				new Point(8.41609, 49.01156), 
				new Point(8.41151, 49.0124), 
				new Point(8.41142, 49.01174), 
				new Point(8.41186, 49.01157), 
				new Point(8.41408, 49.01129), 
				new Point(8.41404, 49.01096), 
				new Point(8.41263, 49.01159), 
				new Point(8.41283, 49.01165), 
				new Point(8.41288, 49.01213), 
			    new Point(8.41323, 49.01212), 
				new Point(8.41276, 49.01127), 
				new Point(8.41251, 49.01133), 
				new Point(8.41318, 49.01112), 
				new Point(8.4132, 49.01167), 
				new Point(8.41348, 49.01168), 
				new Point(8.41351, 49.01212), 
				new Point(8.41368, 49.01098), 
				new Point(8.41452, 49.01094), 
				new Point(8.41195, 49.01236), 
				new Point(8.41625, 49.01318), 
				new Point(8.41628, 49.0135), 
				new Point(8.41662, 49.01349), 
				new Point(8.41738, 49.01365), 
				new Point(8.41777, 49.01344), 
				new Point(8.41831, 49.01342), 
				new Point(8.41842, 49.01351), 
				new Point(8.41891, 49.0135), 
				new Point(8.41914, 49.01358), 
				new Point(8.41936, 49.01369), 
				new Point(8.41948, 49.01385), 
				new Point(8.4198, 49.01356), 
				new Point(8.42008, 49.01343), 
				new Point(8.42043, 49.0134), 
				new Point(8.42045, 49.01353), 
				new Point(8.42042, 49.01382), 
				new Point(8.42041, 49.01415), 
				new Point(8.41991, 49.01416), 
				new Point(8.41995, 49.0145), 
				new Point(8.4204, 49.01447), 
				new Point(8.41369, 49.01116), 
				new Point(8.41406, 49.01116), 
				new Point(8.41381, 49.01214), 
				new Point(8.41385, 49.01261), 
				new Point(8.41415, 49.01227), 
				new Point(8.41414, 49.01214), 
				new Point(8.41415, 49.01203), 
				new Point(8.41441, 49.0126), 
				new Point(8.41443, 49.01304), 
				new Point(8.4149, 49.01282), 
				new Point(8.41498, 49.01293), 
				new Point(8.41514, 49.01302), 
				new Point(8.41526, 49.0132), 
				new Point(8.41583, 49.01319), 
				new Point(8.41575, 49.01301), 
				new Point(8.41584, 49.01285), 
				new Point(8.41523, 49.01302), 
				new Point(8.412, 49.01256), 
				new Point(8.41204, 49.01301), 
				new Point(8.41244, 49.01299), 
				new Point(8.41356, 49.01295), 
				new Point(8.41372, 49.01297), 
				new Point(8.41386, 49.01302), 
				new Point(8.41392, 49.01359), 
				new Point(8.41411, 49.01359), 
				new Point(8.41446, 49.01358), 
				new Point(8.41352, 49.01254) };

		MapLoader maploader = new ConcreteMapLoader();
		
		Graph testGraph = maploader.getGraph();
		Point[] testNodes = testGraph.getNodes();
		
		Assert.assertEquals(expectedNodes.length, testNodes.length);
		
		for (int i = 0; i < expectedNodes.length; i++) {
			Assert.assertEquals(expectedNodes[i].getX(), testNodes[i].getX(), 0.0001);
			Assert.assertEquals(expectedNodes[i].getY(), testNodes[i].getY(), 0.0001);
		}
	}

}
