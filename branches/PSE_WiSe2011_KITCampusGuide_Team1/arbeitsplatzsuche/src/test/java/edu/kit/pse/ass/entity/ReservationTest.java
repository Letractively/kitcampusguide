package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservationTest.
 */
public class ReservationTest {

	/** The reservation. */
	private Reservation reservation;

	/** The Constant FACILITY_IDs. */
	private static final String[] FACILITY_ID_ARRAY = { "f1", "f2", "f3" };

	/** The Constant USER_ID. */
	private static final String USER_ID = "u1";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		reservation = new Reservation(new Date(), new Date(), USER_ID);
	}
	
	/**
	 * Test set id.
	 */
	@Test
	public void testSetID() {
		Reservation r1 = new Reservation();
		r1.setId("TEST");
		assertTrue(r1.getId().equals("TEST"));
	}
	
	/**
	 * Test set start time.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetStartTime() {
		Reservation r1 = new Reservation();
		r1.setStartTime(null);
	}


	/**
	 * Test set start time.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetStartTime3() {
		Reservation r1 = new Reservation();
		Date endTime = new GregorianCalendar(2011, 1, 1, 8, 0).getTime();
		r1.setEndTime(endTime);
		r1.setStartTime(new Date());
	}
	

	/**
	 * Test set end time.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetEndTime() {
		Reservation r1 = new Reservation();
		r1.setEndTime(null);
	}
	

	/**
	 * Test set end time.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetEndTime3() {
		Reservation r1 = new Reservation();
		Date startTime = new GregorianCalendar(2013, 1, 1, 8, 0).getTime();
		r1.setStartTime(startTime);
		r1.setEndTime(new Date());
	}
	
	/**
	 * Test set booking user id.
	 */
	@Test
	public void testSetBookingUserId() {
		reservation.setBookingUserId(USER_ID);
		assertEquals(USER_ID, reservation.getBookingUserId());
	}
	
	/**
	 * Test set booking user id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetBookingUserId2() {
		reservation.setBookingUserId(null);
	}
	
	/**
	 * Test set booking user id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetBookingUserId3() {
		reservation.setBookingUserId("");
	}
	
	/**
	 * Test set booked facility ids.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetBookedFacilityIDs() {
		reservation.setBookedFacilityIDs(null);
	}

	/**
	 * Test add booked facility id.
	 */
	@Test
	public void testAddBookedFacilityId() {
		for (String facilityID : FACILITY_ID_ARRAY) {
			reservation.addBookedFacilityId(facilityID);
		}
		assertEquals(FACILITY_ID_ARRAY.length, reservation.getBookedFacilityIds().size());
	}
	
	/**
	 * Test add booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddBookedFacilityId2() {
		Reservation r1 = new Reservation();
		r1.addBookedFacilityId(null);
	}
	
	/**
	 * Test add booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddBookedFacilityId3() {
		Reservation r1 = new Reservation();
		r1.addBookedFacilityId("");
	}
	
	/**
	 * Test add booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddBookedFacilityId4() {
		Reservation r1 = new Reservation();
		r1.addBookedFacilityId("TEST");
		r1.addBookedFacilityId("TEST");
	}
	
	/**
	 * Test remove booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBookedFacilityId() {
		Reservation r1 = new Reservation();
		r1.removeBookedFacilityId(null);
	}
	
	/**
	 * Test remove booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBookedFacilityId2() {
		Reservation r1 = new Reservation();
		r1.removeBookedFacilityId("");
	}
	
	/**
	 * Test remove booked facility id.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBookedFacilityId3() {
		Reservation r1 = new Reservation();
		r1.removeBookedFacilityId("IS_NOT_BOOKED");
	}

	/**
	 * Test set end and start time.
	 */
	@Test
	public void testSetEndAndStartTime() {
		Calendar cal = Calendar.getInstance();
		// Clear all fields
		cal.clear();

		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);

		Date startDate = cal.getTime();

		cal.set(Calendar.HOUR, 9);
		Date endDate = cal.getTime();

		// Set start and end
		reservation.setStartTime(startDate);
		reservation.setEndTime(endDate);

		// Check start and end
		assertEquals(startDate, reservation.getStartTime());
		assertEquals(endDate, reservation.getEndTime());
	}

}