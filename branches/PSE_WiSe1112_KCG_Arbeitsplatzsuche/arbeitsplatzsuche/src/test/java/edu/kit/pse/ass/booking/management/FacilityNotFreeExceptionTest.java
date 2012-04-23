/**
 * 
 */
package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Oliver Schneider
 * 
 */
public class FacilityNotFreeExceptionTest {

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testFacilityNotFreeExceptionString() {
		FacilityNotFreeException e = new FacilityNotFreeException("aMessage");
		assertEquals("aMessage", e.getMessage());
	}

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testFacilityNotFreeExceptionThrowable() {
		Exception innerE = new Exception();
		FacilityNotFreeException e = new FacilityNotFreeException("aMessage", innerE);
		assertEquals("aMessage", e.getMessage());
		assertEquals(innerE, e.getCause());
	}

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testFacilityNotFreeExceptionStringThrowable() {
		Exception innerE = new Exception();
		FacilityNotFreeException e = new FacilityNotFreeException(innerE);
		assertEquals(innerE, e.getCause());
	}

}
