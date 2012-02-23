package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	/**
	 * Test set name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetName() {
		Property p1 = new Property();
		p1.setName(null);
	}
	
	/**
	 * Test set name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetName2() {
		Property p1 = new Property();
		p1.setName("");
	}
	
	/**
	 * Test hash code.
	 */
	@Test
	public void testHashCode() {
		Property p1 = new Property();
		assertTrue(p1.hashCode() == 0);
	}
	
	/**
	 * Test hash code.
	 */
	@Test
	public void testHashCode2() {
		Property p1 = new Property("TEST");
		assertTrue(p1.hashCode() != 0);
	}
	
	/**
	 * Test equals.
	 */
	@Test
	public void testEquals() {
		Property p1 = new Property("WLAN");
		Property p2 = new Property("WLAN");
		assertTrue(p1.equals(p2));
	}
	
	/**
	 * Test equals.
	 */
	@Test
	public void testEquals2() {
		Property p1 = new Property("WLAN");
		Property p2 = new Property("LAN");
		assertFalse(p1.equals(p2));
	}
	
	/**
	 * Test equals.
	 */
	@Test
	public void testEquals3() {
		Property p1 = new Property("WLAN");
		assertFalse(p1.equals(null));
	}
	
	/**
	 * Test equals.
	 */
	@Test
	public void testEquals4() {
		Property p1 = new Property();
		Property p2 = new Property("WLAN");
		assertFalse(p1.equals(p2));
	}
	
	/**
	 * Test to String.
	 */
	@Test
	public void testToString() {
		Property p1 = new Property("WLAN");
		assertTrue(p1.toString().equals("WLAN"));
	}

}
