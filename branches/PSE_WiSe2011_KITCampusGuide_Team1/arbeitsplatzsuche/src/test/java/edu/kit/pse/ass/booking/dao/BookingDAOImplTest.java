package edu.kit.pse.ass.booking.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.Reservation;

/**
 * @author Lennart
 * 
 */
public class BookingDAOImplTest {

	private static final String USERID = "uzzzz@student.kit.edu";
	private static final String FACILITYID = "#SOME_FACILITY_ID#";
	private static final String PERSISTED_RESERVATIONID = "#SOME_RESERVATION_ID#";

	/**
	 * Setup a BookingDAO with a dummy reservation
	 * */
	BookingDAO bm = new BookingDAOImpl();
	Date start = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();
	Date end = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();
	Reservation testReservation = new Reservation(start, end, USERID);
	Collection<Reservation> testReservationCol = new ArrayList<Reservation>();

	@Before
	public void setUp() throws Exception {
		testReservation.addBookedFacilityId(FACILITYID);
		testReservation.setId(PERSISTED_RESERVATIONID);
		testReservationCol.add(testReservation);
		assertNotNull("No bookingDAO initialized", bm);
		bm.insertReservation(testReservation);
	}

	@Test
	public void testGetReservationsOfUser() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfUser(null, from, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(USERID, null, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(USERID, from, null));
			// test if returned reservations belong all to the user
			for (Reservation resv : bm
					.getReservationsOfUser(USERID, start, end)) {
				assertEquals(USERID, resv.getBookingUserId());
			}
			for (Reservation resv : bm.getReservationsOfUser(USERID, from, to)) {
				assertEquals(USERID, resv.getBookingUserId());
			}
			// test if returned reservations are the right
			assertTrue(bm.getReservationsOfUser(USERID, start, end)
					.containsAll(testReservationCol));
			assertTrue(bm.getReservationsOfUser(USERID, from, to).containsAll(
					testReservationCol));
			assertTrue(bm.getReservationsOfUser(USERID, start, to).containsAll(
					testReservationCol));
			assertFalse(bm.getReservationsOfUser(USERID, end, to).containsAll(
					testReservationCol));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	@Test
	public void testGetReservationsOfFacility() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(null, from, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(FACILITYID, null, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(FACILITYID, from, null));
			// test if returned reservations belong all to the facility
			for (Reservation resv : bm.getReservationsOfFacility(FACILITYID,
					from, to)) {
				assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
			}
			for (Reservation resv : bm.getReservationsOfFacility(FACILITYID,
					start, end)) {
				assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
			}
			// test if returned reservations are the right
			assertTrue(bm.getReservationsOfFacility(FACILITYID, from, to)
					.containsAll(testReservationCol));
			assertTrue(bm.getReservationsOfFacility(FACILITYID, start, end)
					.containsAll(testReservationCol));
			assertTrue(bm.getReservationsOfFacility(FACILITYID, start, to)
					.containsAll(testReservationCol));
			assertFalse(bm.getReservationsOfFacility(FACILITYID, end, to)
					.containsAll(testReservationCol));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	@Test
	public void testGetReservation() {
		Reservation resv = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", bm.getReservation(null));
			resv = bm.getReservation(PERSISTED_RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull("Didn't return reservation", resv);
		// ensure the returned reservation is correct
		assertEquals(PERSISTED_RESERVATIONID, resv.getId());
		assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals(start, resv.getStartTime());
		assertEquals(end, resv.getEndTime());
		assertEquals(USERID, resv.getBookingUserId());
	}

	@Test
	public void testInsertReservation() {
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", bm.insertReservation(null));
			// returned id must equal PERSISTED_RESERVATIONID
			assertEquals(bm.insertReservation(testReservation),
					PERSISTED_RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// ensure reservation was inserted correct
		Reservation resv = bm.getReservation(PERSISTED_RESERVATIONID);
		assertEquals(PERSISTED_RESERVATIONID, resv.getId());
		assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals(start, resv.getStartTime());
		assertEquals(end, resv.getEndTime());
		assertEquals(USERID, resv.getBookingUserId());
	}

	@Test
	public void testUpdateReservation() {
		Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		testReservation.setEndTime(newEnd);
		try {
			// throw error or return null if parameter is null
			bm.updateReservation(null);
			bm.updateReservation(testReservation);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// ensure reservation was updated correct
		Reservation resv = bm.getReservation(PERSISTED_RESERVATIONID);
		assertEquals(PERSISTED_RESERVATIONID, resv.getId());
		assertEquals(newEnd, resv.getEndTime());
		assertEquals(start, resv.getStartTime());
		assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals(USERID, resv.getBookingUserId());
	}

	@Test
	public void testDeleteReservation() {
		try {
			// throw error or return null if parameter is null
			bm.deleteReservation(null);
			bm.deleteReservation(PERSISTED_RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		/*
		 * TODO wait for return of getReservation if id not used
		 */
		assertNull(bm.getReservation(PERSISTED_RESERVATIONID));
	}
}