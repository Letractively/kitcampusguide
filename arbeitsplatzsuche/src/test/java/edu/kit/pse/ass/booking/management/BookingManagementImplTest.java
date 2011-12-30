package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.facility.management.RoomQuery;

/**
 * @author Lennart
 * 
 */
public class BookingManagementImplTest {

	private static final String USERID = "uxyxy@student.kit.edu";
	private static final List<String> FACILITIES = Arrays.asList("ID###1",
			"ID###2");
	private static final List<String> FACILITIES2 = Arrays.asList("ID###3");
	private static String RESERVATIONID;
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };
	private static final String SEARCH_TEXT = "Informatik";
	private static final int NEEDED_WORKPLACES = 3;

	BookingManagement bm = new BookingManagementImpl();

	@Before
	public void setUp() throws Exception {
		Date startDate = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();
		assertNotNull("No bookingManagement initialized", bm);
		RESERVATIONID = bm.book(USERID, FACILITIES, startDate, endDate);
	}

	@Test
	public void testBook() {
		String resvID = null;
		Date startDate = new GregorianCalendar(2012, 0, 1, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 1, 10, 0).getTime();
		try {
			resvID = bm.book(USERID, FACILITIES, startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
		assertEquals(USERID, bm.getReservation(resvID).getBookingUserId());
		assertTrue(bm.getReservation(resvID).getBookedFacilityIds()
				.containsAll(FACILITIES));
		assertEquals(startDate, bm.getReservation(resvID).getStartTime());
		assertEquals(endDate, bm.getReservation(resvID).getEndTime());
	}

	@Test
	public void testListReservationsOfUser() {
		Date startDate = new GregorianCalendar(2012, 0, 0, 0, 0).getTime();
		Date endDate = new GregorianCalendar(2013, 0, 0, 0, 0).getTime();
		Collection<Reservation> resvCol = null;
		try {
			resvCol = bm.listReservationsOfUser(USERID, startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull("No collection of reservations", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		assertEquals(USERID, ((List<Reservation>) resvCol).get(0)
				.getBookingUserId());
		assertTrue(((List<Reservation>) resvCol).get(0).getBookedFacilityIds()
				.containsAll(FACILITIES));
		assertEquals(startDate, ((List<Reservation>) resvCol).get(0)
				.getStartTime());
		assertEquals(endDate, ((List<Reservation>) resvCol).get(0).getEndTime());
	}

	@Test
	public void testListReservationsOfFacility() {
		Date startDate = new GregorianCalendar(2012, 0, 0, 0, 0).getTime();
		Date endDate = new GregorianCalendar(2013, 0, 0, 0, 0).getTime();
		Collection<Reservation> resvCol = null;
		try {
			resvCol = bm.listReservationsOfFacility(FACILITIES.get(0),
					startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull("No collection of reservations", resvCol);
		assertTrue("No reservations in collection", resvCol.size() > 0);
		assertEquals(USERID, ((List<Reservation>) resvCol).get(0)
				.getBookingUserId());
		assertTrue(((List<Reservation>) resvCol).get(0).getBookedFacilityIds()
				.containsAll(FACILITIES));
		assertEquals(startDate, ((List<Reservation>) resvCol).get(0)
				.getStartTime());
		assertEquals(endDate, ((List<Reservation>) resvCol).get(0).getEndTime());
	}

	@Test
	public void testChangeReservationEnd() {
		Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
		try {
			bm.changeReservationEnd(RESERVATIONID, newEnd);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertEquals(newEnd, bm.getReservation(RESERVATIONID).getEndTime());
	}

	@Test
	public void testRemoveFacilityFromReservation() {
		try {
			bm.removeFacilityFromReservation(RESERVATIONID, FACILITIES.get(0));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertTrue(FACILITIES.size() > bm.getReservation(RESERVATIONID)
				.getBookedFacilityIds().size());
	}

	@Test
	public void testDeleteReservation() {

		try {
			bm.deleteReservation(RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// TODO getRes must return null if res not found
		assertNull(bm.getReservation(RESERVATIONID));

	}

	@Test
	public void testGetReservation() {
		Date startDate = new GregorianCalendar(2012, 0, 2, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 2, 10, 0).getTime();
		Reservation resv = null;
		try {
			resv = bm.getReservation(RESERVATIONID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull("no reservation returned", resv);
		assertEquals(RESERVATIONID, resv.getId());
		assertEquals(USERID, resv.getBookingUserId());
		assertTrue(resv.getBookedFacilityIds().containsAll(FACILITIES));
		assertEquals(startDate, resv.getStartTime());
		assertEquals(endDate, resv.getEndTime());
	}

	@Test
	public void testFindFreeFacilites() {
		// TODO
		ArrayList<Property> propertiesList = new ArrayList<Property>();
		for (String property : FIND_PROPERTY_NAMES) {
			propertiesList.add(new Property(property));
		}

		FacilityQuery query = new RoomQuery(propertiesList, SEARCH_TEXT,
				NEEDED_WORKPLACES);
		Collection<FreeFacilityResult> result = null;
		Calendar start = new GregorianCalendar(2012, 0, 2, 9, 0);
		Calendar end = new GregorianCalendar(2012, 0, 2, 10, 0);
		try {
			result = bm.findFreeFacilites(query, start.getTime(),
					end.getTime(), false);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull("No result set", result);
		assertTrue("No results in set", result.size() > 0);
		for (FreeFacilityResult freeFacility : result) {
			assertFalse(FACILITIES.contains(freeFacility.getFacility().getId()));
			assertTrue(FACILITIES2.contains(freeFacility.getFacility().getId()));
		}
	}

	@Test
	public void testIsFacilityFree() {
		// TODO
		Calendar start = new GregorianCalendar(2012, 0, 2, 9, 0);
		Calendar end = new GregorianCalendar(2012, 0, 2, 10, 0);
		boolean result = true;
		try {
			result = bm.isFacilityFree(FACILITIES.get(0), start.getTime(),
					end.getTime());
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertFalse(result);
	}
}
