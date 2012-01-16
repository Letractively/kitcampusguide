package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.testdata.TestData;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingManagementImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingManagementImplTest {

	/** The email of the user. */
	private String userID;

	/** The Constant FACILITIES. */
	private List<String> facilities;

	/** The Constant FACILITIES2. */
	private List<String> facilities2;

	/** The Constant FACILITIES3. */
	private List<String> facilities3;

	/** The RESERVATIONID. */
	private String reservationID;

	/** The RESERVATIONID2. */
	private String reservationID2;

	/** The Constant FIND_PROPERTY_NAMES. */
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };

	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The booking management. */
	@Autowired
	BookingManagement bookingManagement;

	/** The start. */
	Date start = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();

	/** The end. */
	Date end = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();

	/** The test data. */
	@Autowired
	TestData testData;

	/** Collection of the Collections of the test-dummy facilities */
	TestData.DummyFacilities dummyFacilities;

	/** Collection of the Collections of the test-dummy facilities */
	TestData.DummyUsers dummyUsers;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		dummyFacilities = testData.facilityFillWithDummies();
		dummyUsers = testData.userFillWithDummies();
		// get real-DB ID of a user
		userID = dummyUsers.users.get(0).getEmail();
		// get real-DB IDs of the test-dummy rooms
		facilities = new ArrayList<String>(Arrays.asList(dummyFacilities.rooms.get(0).getId(), dummyFacilities.rooms
				.get(1).getId()));
		facilities2 = new ArrayList<String>(Arrays.asList(dummyFacilities.rooms.get(2).getId(), dummyFacilities.rooms
				.get(4).getId()));
		facilities3 = new ArrayList<String>(Arrays.asList(dummyFacilities.rooms.get(3).getId()));

		assertNotNull("No bookingManagement initialized", bookingManagement);
		reservationID = bookingManagement.book(userID, facilities, start, end);
		reservationID2 = bookingManagement.book(dummyUsers.users.get(1).getEmail(), facilities3, start, end);

	}

	/**
	 * Tests the method book.
	 * 
	 * @throws IllegalArgumentException
	 * @throws ReservationNotFoundException
	 * @throws FacilityNotFreeException
	 * @throws FacilityNotFoundException
	 */
	@Test
	public void testBook() throws IllegalArgumentException, ReservationNotFoundException, FacilityNotFreeException,
			FacilityNotFoundException {
		Date startDate = new GregorianCalendar(2012, 0, 1, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 1, 10, 0).getTime();
		String resvID = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", bookingManagement.book(null, facilities, startDate, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error:" + e);
		}
		try {
			assertNull("Accepted wrong parameters.", bookingManagement.book(userID, null, startDate, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error:" + e);
		}
		try {
			assertNull("Accepted wrong parameters.", bookingManagement.book(userID, facilities, null, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error:" + e);
		}
		try {
			assertNull("Accepted wrong parameters.", bookingManagement.book(userID, facilities, startDate, null));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error:" + e);
		}

		resvID = bookingManagement.book("uxyzz@student.kit.edu", facilities, startDate, endDate);
		// a reservation must be returned
		assertNotNull("Reservation id is null", resvID);
		assertFalse("Reservation id is emtpy", resvID.equals(""));
		// check if returned reservation is correct
		assertEquals("uxyzz@student.kit.edu", bookingManagement.getReservation(resvID).getBookingUserId());
		assertTrue(bookingManagement.getReservation(resvID).getBookedFacilityIds().containsAll(facilities));
		assertEquals(startDate, bookingManagement.getReservation(resvID).getStartTime());
		assertEquals(endDate, bookingManagement.getReservation(resvID).getEndTime());
	}

	/**
	 * Test list reservations of user.
	 */
	@Test
	public void testListReservationsOfUser() {
		Date startDate = new GregorianCalendar(2012, 0, 0, 0, 0).getTime();
		Date endDate = new GregorianCalendar(2013, 0, 0, 0, 0).getTime();
		Collection<Reservation> resvCol = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", bookingManagement.listReservationsOfUser(null, startDate, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull("Accepted wrong parameters.", bookingManagement.listReservationsOfUser(userID, null, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull("Accepted wrong parameters.", bookingManagement.listReservationsOfUser(userID, startDate, null));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		resvCol = bookingManagement.listReservationsOfUser(userID, startDate, endDate);
		// a reservation must be returned
		assertNotNull("Collection of reservations is null", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		// check if returned reservations are correct
		for (Reservation resv : resvCol) {
			assertEquals(userID, resv.getBookingUserId());
			assertTrue(startDate.before(resv.getStartTime()));
			assertTrue(endDate.after(resv.getEndTime()));
		}
	}

	/**
	 * Test list reservations of facility.
	 */
	@Test
	public void testListReservationsOfFacility() {
		Date startDate = new GregorianCalendar(2012, 0, 0, 0, 0).getTime();
		Date endDate = new GregorianCalendar(2013, 0, 0, 0, 0).getTime();
		Collection<Reservation> resvCol = null;
		Collection<Reservation> resvCol2 = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfFacility(null, startDate, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfFacility(facilities.get(0), null, endDate));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfFacility(facilities.get(0), startDate, null));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		resvCol = bookingManagement.listReservationsOfFacility(facilities.get(0), startDate, endDate);
		resvCol2 = bookingManagement.listReservationsOfFacility(facilities3.get(0), startDate, endDate);
		// a reservation must be returned
		assertNotNull("Collection of reservations is null", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		assertNotNull("Collection of reservations is null", resvCol2);
		assertTrue("No reservations in collection", resvCol2.size() > 0);
		// check if returned reservations are correct
		for (Reservation resv : resvCol) {
			assertTrue(resv.getBookedFacilityIds().contains(facilities.get(0)));
			assertTrue(startDate.before(resv.getStartTime()));
			assertTrue(endDate.after(resv.getEndTime()));
		}
		for (Reservation resv : resvCol2) {
			assertTrue(resv.getBookedFacilityIds().contains(facilities3.get(0)));
			assertTrue(startDate.before(resv.getStartTime()));
			assertTrue(endDate.after(resv.getEndTime()));
		}
	}

	/**
	 * Test change reservation end.
	 * 
	 * @throws ReservationNotFoundException
	 * @throws IllegalArgumentException
	 * @throws FacilityNotFreeException
	 */
	@Test
	public void testChangeReservationEnd() throws IllegalArgumentException, ReservationNotFoundException,
			FacilityNotFreeException {
		Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		try {
			// TODO throw error if parameter is null
			bookingManagement.changeReservationEnd(reservationID, null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			bookingManagement.changeReservationEnd(null, newEnd);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		bookingManagement.changeReservationEnd(reservationID, newEnd);
		// check if reservation is changed successfully
		assertEquals(newEnd, bookingManagement.getReservation(reservationID).getEndTime());
		assertEquals(start, bookingManagement.getReservation(reservationID).getStartTime());
		assertEquals(userID, bookingManagement.getReservation(reservationID).getBookingUserId());
	}

	/**
	 * Test remove facility from reservation.
	 * 
	 * @throws ReservationNotFoundException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testRemoveFacilityFromReservation() throws IllegalArgumentException, ReservationNotFoundException {
		assertTrue(facilities.size() == bookingManagement.getReservation(reservationID).getBookedFacilityIds().size());
		try {
			// TODO throw error if parameter is null
			bookingManagement.removeFacilityFromReservation(null, facilities.get(0));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			bookingManagement.removeFacilityFromReservation(reservationID, null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		bookingManagement.removeFacilityFromReservation(reservationID, facilities.get(0));
		// check if the right facility was removed
		assertTrue("No facility removed", facilities.size() > bookingManagement.getReservation(reservationID)
				.getBookedFacilityIds().size());
		assertFalse("Facility still in bookedFacilityIds, but a facility was removed", bookingManagement
				.getReservation(reservationID).getBookedFacilityIds().contains(facilities.get(0)));
	}

	/**
	 * Test delete reservation.
	 * 
	 * @throws ReservationNotFoundException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testDeleteReservation() throws IllegalArgumentException, ReservationNotFoundException {
		assertNotNull(bookingManagement.getReservation(reservationID));
		try {
			// TODO throw error if parameter is null
			bookingManagement.deleteReservation(null);
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		bookingManagement.deleteReservation(reservationID);
		// TODO getRes must return null if res not found
		try {
			bookingManagement.getReservation(reservationID);
			fail("No ReservationNotFoundException");
		} catch (ReservationNotFoundException e) {
		}
		// check if other reservation untouched
		assertNotNull(bookingManagement.getReservation(reservationID2));
	}

	/**
	 * Test get reservation.
	 * 
	 * @throws ReservationNotFoundException
	 */
	@Test
	public void testGetReservation() throws ReservationNotFoundException {
		Reservation resv = null;
		try {
			// throw error or return null if parameter is null
			assertNull(bookingManagement.getReservation(null));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		resv = bookingManagement.getReservation(reservationID);

		// a reservation must be returned
		assertNotNull("No reservation returned", resv);
		// check if the right reservation was returned
		assertEquals(reservationID, resv.getId());
		assertEquals(userID, resv.getBookingUserId());
		assertTrue(resv.getBookedFacilityIds().containsAll(facilities));
		assertEquals(start, resv.getStartTime());
		assertEquals(end, resv.getEndTime());
	}

	/**
	 * Test find free facilites.
	 */
	@Test
	public void testFindFreeFacilites() {
		// TODO findFreeFacilites needs a facility database to work
		ArrayList<Property> propertiesList = new ArrayList<Property>();
		for (String property : FIND_PROPERTY_NAMES) {
			propertiesList.add(new Property(property));
		}
		FacilityQuery query = new FreeRoomQuery(propertiesList, SEARCH_TEXT, NEEDED_WORKPLACES);
		Collection<FreeFacilityResult> result = null;
		try {
			// TODO throw error or return null if parameter is null
			assertNull(bookingManagement.findFreeFacilites(null, start, end, false));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull(bookingManagement.findFreeFacilites(query, null, end, false));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull(bookingManagement.findFreeFacilites(query, start, null, false));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		result = bookingManagement.findFreeFacilites(query, start, end, false);

		// free facilities should be returned
		assertNotNull("Result is null", result);
		assertTrue("Resultlist is empty", result.size() > 0);
		// check if right facilities are returned

		System.out.println("##FREE FACS");
		for (FreeFacilityResult freeFacility : result) {
			System.out.println(freeFacility.getFacility().getId());
		}

		for (FreeFacilityResult freeFacility : result) {
			// TODO search text unused in the facility construction, what needed
			// for?
			assertTrue("invalid facility returned", dummyFacilities.rooms.contains(freeFacility.getFacility()));
			assertFalse("facility is in booking 1", facilities.contains(freeFacility.getFacility().getId())
					&& freeFacility.getStart().before(end));
			assertFalse("facility is in booking 2", facilities3.contains(freeFacility.getFacility().getId())
					&& freeFacility.getStart().before(end));
			// assertTrue(FACILITIES2.contains(freeFacility.getFacility().getId()));
		}

	}

	/**
	 * Test is facility free.
	 * 
	 * @throws FacilityNotFoundException
	 */
	@Test
	public void testIsFacilityFree() throws FacilityNotFoundException {
		Date startUnused = new GregorianCalendar(2012, 0, 3, 9, 0).getTime();
		Date endUnused = new GregorianCalendar(2012, 0, 3, 10, 0).getTime();
		Date startUsedHalf = new GregorianCalendar(2012, 0, 2, 8, 0).getTime();
		Date endUsedHalf = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		try {
			// throw error or return null if parameter is null
			assertNull(bookingManagement.isFacilityFree(null, start, end));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull(bookingManagement.isFacilityFree(facilities.get(0), null, end));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
		try {
			assertNull(bookingManagement.isFacilityFree(facilities.get(0), start, null));
			fail("Accepted wrong parameters.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}

		// different dates used
		assertFalse("assert 1", bookingManagement.isFacilityFree(facilities.get(0), start, end));
		assertFalse("assert 2", bookingManagement.isFacilityFree(facilities.get(1), start, end));
		assertTrue("assert 3", bookingManagement.isFacilityFree(facilities.get(0), startUnused, endUnused));
		assertTrue("assert 4", bookingManagement.isFacilityFree(facilities2.get(0), startUnused, endUnused));
		assertTrue("assert 5", bookingManagement.isFacilityFree(facilities2.get(0), start, end));
		assertFalse("assert 6", bookingManagement.isFacilityFree(facilities.get(0), start, endUnused));
		assertTrue("assert 7", bookingManagement.isFacilityFree(facilities.get(0), end, startUnused));
		assertFalse("assert 8", bookingManagement.isFacilityFree(facilities.get(0), startUsedHalf, endUsedHalf));
		assertFalse("assert 9", bookingManagement.isFacilityFree(facilities.get(0), startUsedHalf, end));
		assertFalse("assert 10", bookingManagement.isFacilityFree(facilities.get(0), start, endUsedHalf));
		assertFalse("assert 11", bookingManagement.isFacilityFree(facilities.get(0), startUsedHalf, endUnused));
		assertTrue("assert 12", bookingManagement.isFacilityFree(facilities.get(0), startUsedHalf, start));
		assertTrue("assert 13", bookingManagement.isFacilityFree(facilities.get(0), end, endUsedHalf));
	}
}
