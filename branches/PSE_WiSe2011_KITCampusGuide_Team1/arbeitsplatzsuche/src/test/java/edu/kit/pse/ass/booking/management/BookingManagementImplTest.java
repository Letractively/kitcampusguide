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
import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.dao.FacilityDAO;
import edu.kit.pse.ass.facility.dao.FacilityDAOImpl;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.facility.management.RoomQuery;
import edu.kit.pse.ass.testdata.TestData;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingManagementImplTest.
 * 
 * @author Lennart
 */
public class BookingManagementImplTest {

	/** The Constant USERID. */
	private static final String USERID = "uxyxy@student.kit.edu";

	/** The Constant FACILITIES. */
	private static final List<String> FACILITIES = Arrays.asList("ID###1",
			"ID###2");

	/** The Constant FACILITIES2. */
	private static final List<String> FACILITIES2 = Arrays.asList("ID###3");

	/** The Constant FACILITIES3. */
	private static final List<String> FACILITIES3 = Arrays.asList("ID###4");

	/** The RESERVATIONID. */
	private static String RESERVATIONID;

	/** The RESERVATIONI d2. */
	private static String RESERVATIONID2;

	/** The Constant FIND_PROPERTY_NAMES. */
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };

	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The bm. */
	BookingManagement bm = new BookingManagementImpl();

	/** The fm. */
	FacilityDAO fm = new FacilityDAOImpl();

	/** The start. */
	Date start = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();

	/** The end. */
	Date end = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();

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
		assertNotNull("No bookingManagement initialized", bm);
		RESERVATIONID = bm.book(USERID, FACILITIES, start, end);
		RESERVATIONID2 = bm.book(USERID, FACILITIES3, start, end);
		testData.facilityFillWithDummies();
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
			assertNull("Accepted wrong parameters.",
					bm.book(null, FACILITIES, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bm.book(USERID, null, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bm.book(USERID, FACILITIES, null, endDate));
			assertNull("Accepted wrong parameters.",
					bm.book(USERID, FACILITIES, startDate, null));

			resvID = bm.book("uxyzz@student.kit.edu", FACILITIES, startDate,
					endDate);
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
		// a reservation must be returned
		assertNotNull("Reservation id is null", resvID);
		assertFalse("Reservation id is emtpy", resvID.equals(""));
		// check if returned reservation is correct
		assertEquals("uxyzz@student.kit.edu", bm.getReservation(resvID)
				.getBookingUserId());
		assertTrue(bm.getReservation(resvID).getBookedFacilityIds()
				.containsAll(FACILITIES));
		assertEquals(startDate, bm.getReservation(resvID).getStartTime());
		assertEquals(endDate, bm.getReservation(resvID).getEndTime());
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
					bm.listReservationsOfUser(null, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bm.listReservationsOfUser(USERID, null, endDate));
			assertNull("Accepted wrong parameters.",
					bm.listReservationsOfUser(USERID, startDate, null));

			resvCol = bm.listReservationsOfUser(USERID, startDate, endDate);
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
					bm.listReservationsOfFacility(null, startDate, endDate));
			assertNull("Accepted wrong parameters.",
					bm.listReservationsOfFacility(FACILITIES.get(0), null,
							endDate));
			assertNull("Accepted wrong parameters.",
					bm.listReservationsOfFacility(FACILITIES.get(0), startDate,
							null));

			resvCol = bm.listReservationsOfFacility(FACILITIES.get(0),
					startDate, endDate);
			resvCol2 = bm.listReservationsOfFacility(FACILITIES3.get(0),
					startDate, endDate);
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
			bm.changeReservationEnd(RESERVATIONID, null);
			bm.changeReservationEnd(null, newEnd);

			bm.changeReservationEnd(RESERVATIONID, newEnd);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// check if reservation is changed successfully
		assertEquals(newEnd, bm.getReservation(RESERVATIONID).getEndTime());
		assertEquals(start, bm.getReservation(RESERVATIONID).getStartTime());
		assertEquals(USERID, bm.getReservation(RESERVATIONID)
				.getBookingUserId());
	}

	/**
	 * Test remove facility from reservation.
	 */
	@Test
	public void testRemoveFacilityFromReservation() {
		assertTrue(FACILITIES.size() == bm.getReservation(RESERVATIONID)
				.getBookedFacilityIds().size());
		try {
			// TODO throw error if parameter is null
			bm.removeFacilityFromReservation(null, FACILITIES.get(0));
			bm.removeFacilityFromReservation(RESERVATIONID, null);

			bm.removeFacilityFromReservation(RESERVATIONID, FACILITIES.get(0));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// check if the right facility was removed
		assertTrue("No facility removed", FACILITIES.size() > bm
				.getReservation(RESERVATIONID).getBookedFacilityIds().size());
		assertFalse(
				"Facility still in bookedFacilityIds, but a facility was removed",
				bm.getReservation(RESERVATIONID).getBookedFacilityIds()
						.contains(FACILITIES.get(0)));
	}

	/**
	 * Test delete reservation.
	 */
	@Test
	public void testDeleteReservation() {
		assertNotNull(bm.getReservation(RESERVATIONID));
		try {
			// TODO throw error if parameter is null
			bm.deleteReservation(null);

			bm.deleteReservation(RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// TODO getRes must return null if res not found
		assertNull(bm.getReservation(RESERVATIONID));
		// check if other reservation untouched
		assertNotNull(bm.getReservation(RESERVATIONID2));
	}

	/**
	 * Test get reservation.
	 */
	@Test
	public void testGetReservation() {
		Reservation resv = null;
		try {
			// throw error or return null if parameter is null
			assertNull(bm.getReservation(null));

			resv = bm.getReservation(RESERVATIONID);
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
			assertNull(bm.findFreeFacilites(null, start, end, false));
			assertNull(bm.findFreeFacilites(query, null, end, false));
			assertNull(bm.findFreeFacilites(query, start, null, false));

			result = bm.findFreeFacilites(query, start, end, false);
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
			assertNull(bm.isFacilityFree(null, start, end));
			assertNull(bm.isFacilityFree(FACILITIES.get(0), null, end));
			assertNull(bm.isFacilityFree(FACILITIES.get(0), start, null));

			// different dates used
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), start, end));
			assertFalse(bm.isFacilityFree(FACILITIES.get(1), start, end));
			assertTrue(bm.isFacilityFree(FACILITIES.get(0), startUnused,
					endUnused));
			assertTrue(bm.isFacilityFree(FACILITIES2.get(0), startUnused,
					endUnused));
			assertTrue(bm.isFacilityFree(FACILITIES2.get(0), start, end));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), start, endUnused));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), end, startUnused));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), startUsedHalf,
					endUsedHalf));
			assertFalse(bm
					.isFacilityFree(FACILITIES.get(0), startUsedHalf, end));
			assertFalse(bm
					.isFacilityFree(FACILITIES.get(0), start, endUsedHalf));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), startUsedHalf,
					endUnused));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), startUsedHalf,
					start));
			assertFalse(bm.isFacilityFree(FACILITIES.get(0), end, endUsedHalf));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}