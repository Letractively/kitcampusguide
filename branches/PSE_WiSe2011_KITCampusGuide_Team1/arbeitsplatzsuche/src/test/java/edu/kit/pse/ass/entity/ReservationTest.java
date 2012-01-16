package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

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
	 * Test set booking user id.
	 */
	@Test
	public void testSetBookingUserId() {
		reservation.setBookingUserId(USER_ID);
		assertEquals(USER_ID, reservation.getBookingUserId());
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