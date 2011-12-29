package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		String resv = null;
		Date startDate = new GregorianCalendar(2012, 0, 1, 9, 0).getTime();
		Date endDate = new GregorianCalendar(2012, 0, 1, 10, 0).getTime();
		try {
			resv = bm.book(USERID, FACILITIES, startDate, endDate);
		} catch (Exception e) {
			fail("Error:" + e);
		}
		assertEquals(USERID, bm.getReservation(resv).getBookingUserId());
		assertTrue(bm.getReservation(resv).getBookedFacilityIds()
				.containsAll(FACILITIES));
		assertEquals(startDate, bm.getReservation(resv).getStartTime());
		assertEquals(endDate, bm.getReservation(resv).getEndTime());
	}

	@Test
	public void testListReservationsOfUser() {
		Date startDate = new GregorianCalendar(2012, 0, 0, 0, 0).getTime();
		Date endDate = new GregorianCalendar(2013, 0, 0, 0, 0).getTime();
		Collection<Reservation> resvCol = null;
		try {
			resvCol = bm.listReservationsOfUser(USERID, startDate, endDate);
		} catch (Exception e) {
			fail("Error: " + e);
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
			fail("Error: " + e);
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
			fail("Error: " + e);
		}
		assertEquals(newEnd, bm.getReservation(RESERVATIONID).getEndTime());
	}

	@Test
	public void testRemoveFacilityFromReservation() {
		try {
			bm.removeFacilityFromReservation(RESERVATIONID, FACILITIES.get(0));
		} catch (Exception e) {
			fail("Error: " + e);
		}
		assertTrue(FACILITIES.size() > bm.getReservation(RESERVATIONID)
				.getBookedFacilityIds().size());
	}

	@Test
	public void testDeleteReservation() {
		// TODO
		bm.deleteReservation(RESERVATIONID);
	}

	@Test
	public void testGetReservation() {

		bm.getReservation(RESERVATIONID);
		// assertNotNull(resv);
		// assertEquals(resv.getId(), RESERVATIONID);
	}

	@Test
	public void testFindFreeFacilites() {

		ArrayList<Property> properties = new ArrayList<Property>();
		for (String property : FIND_PROPERTY_NAMES) {
			properties.add(new Property(property));
		}

		FacilityQuery query = new RoomQuery(properties, SEARCH_TEXT,
				NEEDED_WORKPLACES);

		Calendar start = new GregorianCalendar(2011, Calendar.DECEMBER, 31, 12,
				45, 0);
		Calendar end = new GregorianCalendar(2011, Calendar.DECEMBER, 31, 15,
				0, 0);

		bm.findFreeFacilites(query, start.getTime(), end.getTime(), false);
		// assertNotNull("No result set", result);
		// assertTrue("No results in set", result.size() > 0);
	}

	@Test
	public void testIsFacilityFree() {

		Calendar start = new GregorianCalendar(2011, 11, 30, 14, 0);
		Calendar end = new GregorianCalendar(2011, 11, 30, 15, 0);
		bm.isFacilityFree(FACILITIES.get(0), start.getTime(), end.getTime());
	}
}
