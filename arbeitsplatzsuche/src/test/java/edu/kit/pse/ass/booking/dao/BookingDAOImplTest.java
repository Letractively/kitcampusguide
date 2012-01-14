package edu.kit.pse.ass.booking.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import edu.kit.pse.ass.testdata.TestData;
import edu.kit.pse.ass.testdata.TestData.DummyFacilities;
import edu.kit.pse.ass.testdata.TestData.DummyUsers;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingDAOImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingDAOImplTest {

	/** The Constant USERID. */
	private static final String USERID = "uzzzz@student.kit.edu";

	/** The Constant FACILITYID. */
	private static final String FACILITYID = "#SOME_FACILITY_ID#";

	/** The Constant PERSISTED_RESERVATIONID. */
	private static String PERSISTED_RESERVATIONID;

	/** Setup a BookingDAO with a dummy reservation. */
	@Autowired
	private BookingDAO bookingDAO;

	/** The start. */
	Date start = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();

	/** The end. */
	Date end = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();

	/** The test reservation. */
	Reservation testReservation = new Reservation(start, end, USERID);

	/** The test reservation col. */
	Collection<Reservation> testReservationCol = new ArrayList<Reservation>();

	/** The test data. */
	@Autowired
	TestData testData;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		testReservation.addBookedFacilityId(FACILITYID);
		testReservationCol.add(testReservation);

		bookingDAO.insertReservation(testReservation);
		assertNotNull(testReservation.getId());
		PERSISTED_RESERVATIONID = testReservation.getId();

		DummyFacilities dummyFacilities = testData.facilityFillWithDummies();
		DummyUsers dummyUsers = testData.userFillWithDummies();
		testData.bookingFillWithDummies(dummyFacilities, dummyUsers);
	}

	/**
	 * Test get reservations of user.
	 */
	@Test
	public void testGetReservationsOfUser() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		Collection<Reservation> result = null;
		try {
			// throw error or return null if parameter is null
			bookingDAO.getReservationsOfUser(null, from, to);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		try {
			bookingDAO.getReservationsOfUser(USERID, null, to);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		try {
			bookingDAO.getReservationsOfUser(USERID, from, null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}

		// test if returned reservations belong all to the user
		result = bookingDAO.getReservationsOfUser(USERID, start, end);
		assertNotNull("Didn't return result", result);
		assertFalse("Empty list returned", result.size() == 0);
		assertTrue("Wrong amount of reservations returned",
				result.size() == testReservationCol.size());
		for (Reservation resv : result) {
			assertEquals(USERID, resv.getBookingUserId());
		}
		result = null;
		result = bookingDAO.getReservationsOfUser(USERID, from, to);
		assertNotNull("Didn't return result", result);
		for (Reservation resv : result) {
			assertEquals(USERID, resv.getBookingUserId());
		}
		// test if returned reservations are the right
		assertTrue("Contains not all reservations",
				bookingDAO.getReservationsOfUser(USERID, start, end)
						.containsAll(testReservationCol));
		assertTrue(bookingDAO.getReservationsOfUser(USERID, from, to)
				.containsAll(testReservationCol));
		assertTrue(bookingDAO.getReservationsOfUser(USERID, start, to)
				.containsAll(testReservationCol));
		assertFalse(bookingDAO.getReservationsOfUser(USERID, end, to)
				.containsAll(testReservationCol));
	}

	/**
	 * Test get reservations of facility.
	 */
	@Test
	public void testGetReservationsOfFacility() {
		Date from = new GregorianCalendar(2012, 0, 2).getTime();
		Date to = new GregorianCalendar(2012, 0, 3).getTime();
		Collection<Reservation> result = null;
		try {
			// throw error or return null if parameter is null
			bookingDAO.getReservationsOfFacility(null, from, to);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		try {
			bookingDAO.getReservationsOfFacility(FACILITYID, null, to);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		try {
			bookingDAO.getReservationsOfFacility(FACILITYID, from, null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		// test if returned reservations belong all to the facility
		result = bookingDAO.getReservationsOfFacility(FACILITYID, from, to);
		assertNotNull("Didn't return result", result);
		assertFalse("Empty list returned", result.size() == 0);
		assertTrue("Wrong amount of reservations returned",
				result.size() == testReservationCol.size());
		for (Reservation resv : result) {
			assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		}
		result = bookingDAO.getReservationsOfFacility(FACILITYID, start, end);
		for (Reservation resv : result) {
			assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		}
		// test if returned reservations are the right
		assertTrue("Doesn't contain all reservations",
				bookingDAO.getReservationsOfFacility(FACILITYID, from, to)
						.containsAll(testReservationCol));
		assertTrue("Doesn't contain all reservations",
				bookingDAO.getReservationsOfFacility(FACILITYID, start, end)
						.containsAll(testReservationCol));
		assertTrue("Doesn't contain all reservations",
				bookingDAO.getReservationsOfFacility(FACILITYID, start, to)
						.containsAll(testReservationCol));
		assertFalse("Did return reservations it shouldn't have!",
				bookingDAO.getReservationsOfFacility(FACILITYID, end, to)
						.containsAll(testReservationCol));
	}

	/**
	 * Test get reservation.
	 */
	@Test
	public void testGetReservation() {
		Reservation resv = null;
		try {
			// throw error or return null if parameter is null
			bookingDAO.getReservation(null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		resv = bookingDAO.getReservation(PERSISTED_RESERVATIONID);
		assertNotNull("Didn't return reservation", resv);
		// ensure the returned reservation is correct
		assertEquals("Ids are not equal", PERSISTED_RESERVATIONID, resv.getId());
		assertTrue("Returned reservation doesn't contain the right facilities",
				resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals("The start dates are not equal", start,
				resv.getStartTime());
		assertEquals("The end dates are not equal", end, resv.getEndTime());
		assertEquals("The booking user ids are not equal", USERID,
				resv.getBookingUserId());
	}

	/**
	 * Test insert reservation.
	 */
	@Test
	public void testInsertReservation() {
		Reservation newResv = new Reservation(start, end, USERID);
		newResv.addBookedFacilityId(FACILITYID);
		String newResvId = null;
		// throw error or return null if parameter is null

		try {
			bookingDAO.insertReservation(null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		// TODO id is not returned correctly
		newResvId = bookingDAO.insertReservation(newResv);
		// ensure reservation was inserted correct
		Reservation resv = bookingDAO.getReservation(newResvId);
		assertNotNull("Didn't return reservation", resv);
		assertEquals(newResvId, resv.getId());
		assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals(start, resv.getStartTime());
		assertEquals(end, resv.getEndTime());
		assertEquals(USERID, resv.getBookingUserId());
	}

	/**
	 * Test update reservation.
	 */
	@Test
	public void testUpdateReservation() {
		Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		testReservation.setEndTime(newEnd);
		try {
			bookingDAO.updateReservation(null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		bookingDAO.updateReservation(testReservation);
		// ensure reservation was updated correct
		Reservation resv = bookingDAO.getReservation(PERSISTED_RESERVATIONID);
		assertNotNull("Didn't return reservation", resv);
		assertEquals(PERSISTED_RESERVATIONID, resv.getId());
		assertEquals(newEnd, resv.getEndTime());
		assertEquals(start, resv.getStartTime());
		assertTrue(resv.getBookedFacilityIds().contains(FACILITYID));
		assertEquals(USERID, resv.getBookingUserId());
	}

	/**
	 * Test delete reservation.
	 */
	@Test
	public void testDeleteReservation() {
		try {
			bookingDAO.deleteReservation(null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
		}
		bookingDAO.deleteReservation(PERSISTED_RESERVATIONID);
		/*
		 * TODO wait for return of getReservation if id not used
		 */
		assertNull(bookingDAO.getReservation(PERSISTED_RESERVATIONID));
	}
}