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
public class BookingNotAllowedExceptionTest {

	/**
	 * Test method for BookingNotAllowedException.
	 */
	@Test
	public void testBookingNotAllowedException() {
		BookingNotAllowedException e = new BookingNotAllowedException("aUser", "aMessage");
		assertEquals("aUser", e.getUserID());
		assertEquals("aMesage", e.getMessage());
	}

}
