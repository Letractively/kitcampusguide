package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyTest.
 */
public class PropertyTest {

	/** The prop. */
	Property prop;

	/** The TES t_ name. */
	private static final String TEST_NAME = "WLAN";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		prop = new Property(TEST_NAME);
	}

	/**
	 * Test get name.
	 */
	@Test
	public void testGetName() {
		assertEquals(TEST_NAME, prop.getName());
	}

}
