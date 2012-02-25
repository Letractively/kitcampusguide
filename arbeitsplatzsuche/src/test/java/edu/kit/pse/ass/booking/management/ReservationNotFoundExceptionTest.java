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
public class ReservationNotFoundExceptionTest {

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testReservationNotFoundExceptionString() {
		ReservationNotFoundException e = new ReservationNotFoundException("aMessage");
		assertEquals("aMessage", e.getMessage());
	}

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testReservationNotFoundExceptionThrowable() {
		Exception innerE = new Exception();
		ReservationNotFoundException e = new ReservationNotFoundException("aMessage", innerE);
		assertEquals("aMessage", e.getMessage());
		assertEquals(innerE, e.getCause());
	}

	/**
	 * Test method for FacilityNotFreeException
	 * */
	@Test
	public void testReservationNotFoundExceptionStringThrowable() {
		Exception innerE = new Exception();
		ReservationNotFoundException e = new ReservationNotFoundException(innerE);
		assertEquals(innerE, e.getCause());
	}

}
