package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.facility.management.RoomQuery;
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

	/** The Constant USERID. */
	private static final String USERID = "uxyxy@student.kit.edu";

	/** The Constant FACILITIES. */
	private static List<String> FACILITIES = null;

	/** The Constant FACILITIES2. */
	private static List<String> FACILITIES2 = null;

	/** The Constant FACILITIES3. */
	private static List<String> FACILITIES3 = null;

	/** The RESERVATIONID. */
	private static String RESERVATIONID;

	/** The RESERVATIONID2. */
	private static String RESERVATIONID2;

	/** The Constant FIND_PROPERTY_NAMES. */
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };

	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The bm. */
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
	Collection<Collection<Facility>> dummyFacilities = null;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		dummyFacilities = testData.facilityFillWithDummies();
		// get real-DB IDs of the test-dummy rooms
		FACILITIES = Arrays
				.asList(((List<Facility>) ((List<Collection<Facility>>) dummyFacilities)
						.get(1)).get(0).getId(),
						((List<Facility>) ((List<Collection<Facility>>) dummyFacilities)
								.get(1)).get(1).getId());
		FACILITIES2 = Arrays
				.asList(((List<Facility>) ((List<Collection<Facility>>) dummyFacilities)
						.get(1)).get(2).getId());
		FACILITIES3 = Arrays
				.asList(((List<Facility>) ((List<Collection<Facility>>) dummyFacilities)
						.get(1)).get(3).getId());

		assertNotNull("No bookingManagement initialized", bookingManagement);
		RESERVATIONID = bookingManagement.book(USERID, FACILITIES, start, end);
		RESERVATIONID2 = bookingManagement
				.book(USERID, FACILITIES3, start, end);

	}

	/**
	 * Test book.
	 */
	@Test
	public void testBook() {
		Date startDate = new GregorianCalendar(2012, 0, 1, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 1, 10, 0).getTime();
		String resvID = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", bookingManagement.book(
					null, FACILITIES, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bookingManagement.book(USERID, null, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bookingManagement.book(USERID, FACILITIES, null, endDate));
			assertNull("Accepted wrong parameters.",
					bookingManagement.book(USERID, FACILITIES, startDate, null));

			resvID = bookingManagement.book("uxyzz@student.kit.edu",
					FACILITIES, startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
		// a reservation must be returned
		assertNotNull("Reservation id is null", resvID);
		assertFalse("Reservation id is emtpy", resvID.equals(""));
		// check if returned reservation is correct
		assertEquals("uxyzz@student.kit.edu",
				bookingManagement.getReservation(resvID).getBookingUserId());
		assertTrue(bookingManagement.getReservation(resvID)
				.getBookedFacilityIds().containsAll(FACILITIES));
		assertEquals(startDate, bookingManagement.getReservation(resvID)
				.getStartTime());
		assertEquals(endDate, bookingManagement.getReservation(resvID)
				.getEndTime());
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
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfUser(null, startDate,
							endDate));
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfUser(USERID, null,
							endDate));
			assertNull("Accepted wrong parameters.",
					bookingManagement.listReservationsOfUser(USERID, startDate,
							null));

			resvCol = bookingManagement.listReservationsOfUser(USERID,
					startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// a reservation must be returned
		assertNotNull("Collection of reservations is null", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		// check if returned reservations are correct
		for (Reservation resv : resvCol) {
			assertEquals(USERID, resv.getBookingUserId());
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
					bookingManagement.listReservationsOfFacility(null,
							startDate, endDate));
			assertNull(
					"Accepted wrong parameters.",
					bookingManagement.listReservationsOfFacility(
							FACILITIES.get(0), null, endDate));
			assertNull(
					"Accepted wrong parameters.",
					bookingManagement.listReservationsOfFacility(
							FACILITIES.get(0), startDate, null));

			resvCol = bookingManagement.listReservationsOfFacility(
					FACILITIES.get(0), startDate, endDate);
			resvCol2 = bookingManagement.listReservationsOfFacility(
					FACILITIES3.get(0), startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// a reservation must be returned
		assertNotNull("Collection of reservations is null", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		assertNotNull("Collection of reservations is null", resvCol2);
		assertTrue("No reservations in collection", resvCol2.size() > 0);
		// check if returned reservations are correct
		for (Reservation resv : resvCol) {
			assertTrue(resv.getBookedFacilityIds().contains(FACILITIES.get(0)));
			assertTrue(startDate.before(resv.getStartTime()));
			assertTrue(endDate.after(resv.getEndTime()));
		}
		for (Reservation resv : resvCol2) {
			assertTrue(resv.getBookedFacilityIds().contains(FACILITIES3.get(0)));
			assertTrue(startDate.before(resv.getStartTime()));
			assertTrue(endDate.after(resv.getEndTime()));
		}
	}

	/**
	 * Test change reservation end.
	 */
	@Test
	public void testChangeReservationEnd() {
		Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		try {
			// TODO throw error if parameter is null
			bookingManagement.changeReservationEnd(RESERVATIONID, null);
			bookingManagement.changeReservationEnd(null, newEnd);

			bookingManagement.changeReservationEnd(RESERVATIONID, newEnd);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// check if reservation is changed successfully
		assertEquals(newEnd, bookingManagement.getReservation(RESERVATIONID)
				.getEndTime());
		assertEquals(start, bookingManagement.getReservation(RESERVATIONID)
				.getStartTime());
		assertEquals(USERID, bookingManagement.getReservation(RESERVATIONID)
				.getBookingUserId());
	}

	/**
	 * Test remove facility from reservation.
	 */
	@Test
	public void testRemoveFacilityFromReservation() {
		assertTrue(FACILITIES.size() == bookingManagement
				.getReservation(RESERVATIONID).getBookedFacilityIds().size());
		try {
			// TODO throw error if parameter is null
			bookingManagement.removeFacilityFromReservation(null,
					FACILITIES.get(0));
			bookingManagement
					.removeFacilityFromReservation(RESERVATIONID, null);

			bookingManagement.removeFacilityFromReservation(RESERVATIONID,
					FACILITIES.get(0));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// check if the right facility was removed
		assertTrue("No facility removed", FACILITIES.size() > bookingManagement
				.getReservation(RESERVATIONID).getBookedFacilityIds().size());
		assertFalse(
				"Facility still in bookedFacilityIds, but a facility was removed",
				bookingManagement.getReservation(RESERVATIONID)
						.getBookedFacilityIds().contains(FACILITIES.get(0)));
	}

	/**
	 * Test delete reservation.
	 */
	@Test
	public void testDeleteReservation() {
		assertNotNull(bookingManagement.getReservation(RESERVATIONID));
		try {
			// TODO throw error if parameter is null
			bookingManagement.deleteReservation(null);

			bookingManagement.deleteReservation(RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// TODO getRes must return null if res not found
		assertNull(bookingManagement.getReservation(RESERVATIONID));
		// check if other reservation untouched
		assertNotNull(bookingManagement.getReservation(RESERVATIONID2));
	}

	/**
	 * Test get reservation.
	 */
	@Test
	public void testGetReservation() {
		Reservation resv = null;
		try {
			// throw error or return null if parameter is null
			assertNull(bookingManagement.getReservation(null));

			resv = bookingManagement.getReservation(RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// a reservation must be returned
		assertNotNull("No reservation returned", resv);
		// check if the right reservation was returned
		assertEquals(RESERVATIONID, resv.getId());
		assertEquals(USERID, resv.getBookingUserId());
		assertTrue(resv.getBookedFacilityIds().containsAll(FACILITIES));
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
		FacilityQuery query = new RoomQuery(propertiesList, SEARCH_TEXT,
				NEEDED_WORKPLACES);
		Collection<FreeFacilityResult> result = null;
		try {
			// TODO throw error or return null if parameter is null
			assertNull(bookingManagement.findFreeFacilites(null, start, end,
					false));
			assertNull(bookingManagement.findFreeFacilites(query, null, end,
					false));
			assertNull(bookingManagement.findFreeFacilites(query, start, null,
					false));

			result = bookingManagement.findFreeFacilites(query, start, end,
					false);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// free facilities should be returned
		assertNotNull("Result is null", result);
		assertTrue("Resultlist is empty", result.size() > 0);
		// check if right facilities are returned
		for (FreeFacilityResult freeFacility : result) {
			// TODO search text unused in the facility construction, what needed
			// for?
			assertFalse(FACILITIES.contains(freeFacility.getFacility().getId()));
			assertTrue(FACILITIES2.contains(freeFacility.getFacility().getId()));
			assertFalse(FACILITIES3
					.contains(freeFacility.getFacility().getId()));
		}
	}

	/**
	 * Test is facility free.
	 */
	@Test
	public void testIsFacilityFree() {
		Date startUnused = new GregorianCalendar(2012, 0, 3, 9, 0).getTime();
		Date endUnused = new GregorianCalendar(2012, 0, 3, 10, 0).getTime();
		Date startUsedHalf = new GregorianCalendar(2012, 0, 2, 8, 0).getTime();
		Date endUsedHalf = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		try {
			// throw error or return null if parameter is null
			assertNull(bookingManagement.isFacilityFree(null, start, end));
			assertNull(bookingManagement.isFacilityFree(FACILITIES.get(0),
					null, end));
			assertNull(bookingManagement.isFacilityFree(FACILITIES.get(0),
					start, null));

			// different dates used
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					start, end));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(1),
					start, end));
			assertTrue(bookingManagement.isFacilityFree(FACILITIES.get(0),
					startUnused, endUnused));
			assertTrue(bookingManagement.isFacilityFree(FACILITIES2.get(0),
					startUnused, endUnused));
			assertTrue(bookingManagement.isFacilityFree(FACILITIES2.get(0),
					start, end));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					start, endUnused));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					end, startUnused));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					startUsedHalf, endUsedHalf));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					startUsedHalf, end));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					start, endUsedHalf));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					startUsedHalf, endUnused));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					startUsedHalf, start));
			assertFalse(bookingManagement.isFacilityFree(FACILITIES.get(0),
					end, endUsedHalf));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}
