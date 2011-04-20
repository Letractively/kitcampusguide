package beans;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConstantData;
import edu.kit.cm.kitcampusguide.model.Graph;

/**
 * This class tests the ManagedBean {@see ConstantData} of the KITCampusGuide. 
 * The tests assume that the data loaders of package data are working.
 * 
 * @author Kateryna Yurchenko
 */
public class ConstantDataTest {
	
	/**
	 * Tests the constructor of ConstantData.
	 */
	@Test
	public void testConstantData() {
		ConstantData c = new ConstantData();
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.getCategories());
		Assert.assertNotNull(c.getCategoriesName());
		Assert.assertNotNull(c.getAllPOI());
		Assert.assertNotNull(c.getAllPOIName());
		Assert.assertFalse(c.getCategories().size() == 0);
		Assert.assertFalse(c.getCategoriesName().size() == 0);
		Assert.assertFalse(c.getAllPOI().size() == 0);
		Assert.assertFalse(c.getAllPOIName().size() == 0);
	}

	/**
	 * Tests the getGraph() Method of ConstantData.
	 */
	@Test
	public void testGetGraph() {
		Graph g = ConstantData.getGraph();
		Assert.assertNotNull(g);
		Assert.assertSame(g, ConstantData.getGraph());
	}
}
