package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.facility.management.RoomQuery;

public class BookingManagementImplTest {

	private static final String USERID = "uxyxy@student.kit.edu";
	private static final List<String> FACILITIES = Arrays.asList("ID###1",
			"ID###2");
	private static final String RESERVATIONID = "MYRESERVATION###1";
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };
	private static final String SEARCH_TEXT = "Informatik";
	private static final int NEEDED_WORKPLACES = 3;

	@Test
	public void testBook() {
		BookingManagement bm = new BookingManagementImpl();
		Calendar startDate = new GregorianCalendar(2012, 0, 1, 12, 15);
		Calendar endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.MINUTE, 5 * 15);
		try {
			bm.book(USERID, FACILITIES, startDate.getTime(), endDate.getTime());
		} catch (FacilityNotFreeException e) {
			fail("Facilities could not be booked");
		}
	}

	@Test
	public void testListReservationsOfUser() {
		BookingManagement bm = new BookingManagementImpl();

		bm.listReservationsOfUser(USERID, new GregorianCalendar(2011,
				Calendar.DECEMBER, 1, 0, 0, 0).getTime(),
				new GregorianCalendar(2011, Calendar.DECEMBER, 31, 23, 59, 59)
						.getTime());

		// assertNotNull("No collection of reservations", resv);
		// assertTrue("No reservations in collection", resv.size() > 0);
	}

	@Test
	public void testListReservationsOfFacility() {
		BookingManagement bm = new BookingManagementImpl();

		bm.listReservationsOfFacility(FACILITIES.get(0), new GregorianCalendar(
				2011, Calendar.DECEMBER, 1, 0, 0, 0).getTime(),
				new GregorianCalendar(2011, Calendar.DECEMBER, 31, 23, 59, 59)
						.getTime());

		// assertNotNull("No collection of reservations", resv);
		// assertTrue("No reservations in collection", resv.size() > 0);
	}

	@Test
	public void testChangeReservationEnd() {
		BookingManagement bm = new BookingManagementImpl();

		bm.changeReservationEnd(RESERVATIONID, new GregorianCalendar(2012, 0,
				1, 13, 0).getTime());
	}

	@Test
	public void testRemoveFacilityFromReservation() {
		BookingManagement bm = new BookingManagementImpl();

		bm.removeFacilityFromReservation(RESERVATIONID, FACILITIES.get(0));
	}

	@Test
	public void testDeleteReservation() {
		BookingManagement bm = new BookingManagementImpl();
		bm.deleteReservation(RESERVATIONID);
	}

	@Test
	public void testGetReservation() {
		BookingManagement bm = new BookingManagementImpl();
		bm.getReservation(RESERVATIONID);
		// assertNotNull(resv);
		// assertEquals(resv.getId(), RESERVATIONID);
	}

	@Test
	public void testFindFreeFacilites() {
		BookingManagement bm = new BookingManagementImpl();

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
		BookingManagement bm = new BookingManagementImpl();
		Calendar start = new GregorianCalendar(2011, 11, 30, 14, 0);
		Calendar end = new GregorianCalendar(2011, 11, 30, 15, 0);
		bm.isFacilityFree(FACILITIES.get(0), start.getTime(), end.getTime());
	}
}
