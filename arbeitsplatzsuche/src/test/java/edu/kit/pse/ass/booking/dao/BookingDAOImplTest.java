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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Reservation;

/**
 * @author Lennart
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingDAOImplTest {

	private static final String USERID = "uzzzz@student.kit.edu";
	private static final String FACILITYID = "#SOME_FACILITY_ID#";
	private static final String PERSISTED_RESERVATIONID = "#SOME_RESERVATION_ID#";

	/**
	 * Setup a BookingDAO with a dummy reservation
	 * */
	@Autowired
	private BookingDAO bm;

	Date start = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();
	Date end = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();
	Reservation testReservation = new Reservation(start, end, USERID);
	Collection<Reservation> testReservationCol = new ArrayList<Reservation>();

	@Before
	public void setUp() throws Exception {
		testReservation.addBookedFacilityId(FACILITYID);
		testReservation.setId(PERSISTED_RESERVATIONID);
		testReservationCol.add(testReservation);

		bm.updateReservation(testReservation);
		/*
		 * try { bm.updateReservation(testReservation);
		 * bm.bookingFillWithDummies(); } catch (Exception e) {
		 * System.out.println("Error: " + e.getMessage()); }
		 */
	}

	@Test
	public void testGetReservationsOfUser() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		Collection<Reservation> result = null;
		try {
			// throw error or return null if parameter is null

			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfUser(null, from, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfUser(USERID, null, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfUser(USERID, from, null));

			// test if returned reservations belong all to the user
			result = bm.getReservationsOfUser(USERID, start, end);
			assertNotNull("Didn't return result", result);
			assertFalse("Empty list returned", result.size() == 0);
			assertTrue("Wrong amount of reservations returned",
					result.size() == testReservationCol.size());
			for (Reservation resv : result) {
				assertEquals(USERID, resv.getBookingUserId());
			}
			result = null;
			result = bm.getReservationsOfUser(USERID, from, to);
			assertNotNull("Didn't return result", result);
			for (Reservation resv : result) {
				assertEquals(USERID, resv.getBookingUserId());
			}
			// test if returned reservations are the right
			assertTrue(
					"Contains not all reservations",
					bm.getReservationsOfUser(USERID, start, end).containsAll(
							testReservationCol));
			assertTrue(bm.getReservationsOfUser(USERID, from, to).containsAll(
					testReservationCol));
			assertTrue(bm.getReservationsOfUser(USERID, start, to).containsAll(
					testReservationCol));
			assertFalse(bm.getReservationsOfUser(USERID, end, to).containsAll(
					testReservationCol));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Test
	public void testGetReservationsOfFacility() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		Collection<Reservation> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(null, from, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(FACILITYID, null, to));
			assertNull("Accepted wrong parameters.",
					bm.getReservationsOfFacility(FACILITYID, from, null));
			// test if returned reservations belong all to the facility
			result = bm.getReservationsOfFacility(FACILITYID, from, to);
			assertNotNull("Didn't return result", result);
			assertFalse("Empty list returned", result.size() == 0);
			assertTrue("Wrong amount of reservations returned",
					result.size() == testReservationCol.size());
			for (Reservation resv : result) {
				assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
			}
			result = bm.getReservationsOfFacility(FACILITYID, start, end);
			for (Reservation resv : result) {
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
		// throw error or return null if parameter is null

		try {
			assertNull("Accepted wrong parameters.", bm.getReservation(null));
			resv = bm.getReservation(PERSISTED_RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
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
		Reservation newResv = new Reservation(start, end, USERID);
		newResv.addBookedFacilityId(FACILITYID);
		String newResvId = null;
		// throw error or return null if parameter is null

		try {
			assertNull("Accepted wrong parameters.", bm.insertReservation(null));
			// TODO id is not returned correctly
			newResvId = bm.insertReservation(newResv);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// ensure reservation was inserted correct
		Reservation resv = bm.getReservation(newResvId);
		assertNotNull("Didn't return reservation", resv);
		assertEquals(newResvId, resv.getId());
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
			System.out.println("Error: " + e.getMessage());
		}
		// ensure reservation was updated correct
		Reservation resv = bm.getReservation(PERSISTED_RESERVATIONID);
		assertNotNull("Didn't return reservation", resv);
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
			System.out.println("Error: " + e.getMessage());
		}
		/*
		 * TODO wait for return of getReservation if id not used
		 */
		assertNull(bm.getReservation(PERSISTED_RESERVATIONID));
	}
}