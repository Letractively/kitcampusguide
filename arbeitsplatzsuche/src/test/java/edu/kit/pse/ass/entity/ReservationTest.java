package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ReservationTest {

	private Reservation reservation;
	private static final String[] FACILITY_IDs = { "f1", "f2", "f3" };
	private static final String USER_ID = "u1";

	@Before
	public void setUp() throws Exception {
		reservation = new Reservation();
	}

	@Test
	public void testSetBookingUserId() {
		reservation.setBookingUserId(USER_ID);
		assertEquals(USER_ID, reservation.getBookingUserId());
	}

	@Test
	public void testAddBookedFacilityId() {
		for (String facilityID : FACILITY_IDs) {
			reservation.addBookedFacilityId(facilityID);
		}
		assertEquals(FACILITY_IDs.length, reservation.getBookedFacilityIds()
				.size());
	}

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