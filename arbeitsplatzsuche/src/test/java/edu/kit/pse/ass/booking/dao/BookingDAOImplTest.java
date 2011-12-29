package edu.kit.pse.ass.booking.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
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
	Date start = new GregorianCalendar(2012, 11, 30, 9, 0).getTime();
	Date end = new GregorianCalendar(2012, 11, 30, 10, 0).getTime();
	Reservation testReservation = new Reservation(start, end, USERID);
	Collection<Reservation> testReservationCol = null;

	@Before
	public void setUp() throws Exception {
		// TODO might add more
		testReservation.addBookedFacilityId(FACILITYID);
		testReservation.setId(PERSISTED_RESERVATIONID);
		testReservationCol.add(testReservation);
		assertNotNull("No bookingDAO initialized", bm);
		bm.insertReservation(testReservation);
	}

	@Test
	public void testGetReservationsOfUser() {
		Date from = new GregorianCalendar(2012, 11, 30).getTime();
		Date to = new GregorianCalendar(2012, 11, 31).getTime();
		try {
			assertNotNull("accepted wrong parameters. ",
					bm.getReservationsOfUser(null, from, to));
		} catch (Exception e) {

		}
		assertEquals(testReservationCol,
				bm.getReservationsOfUser(USERID, from, to));
	}

	@Test
	public void testGetReservationsOfFacility() {
		Date from = new GregorianCalendar(2012, 11, 30).getTime();
		Date to = new GregorianCalendar(2012, 11, 31).getTime();
		try {
			assertNotNull("accepted wrong parameters. ",
					bm.getReservationsOfFacility(null, from, to));
		} catch (Exception e) {

		}
		assertEquals(testReservationCol,
				bm.getReservationsOfFacility(FACILITYID, from, to));
	}

	@Test
	public void testGetReservation() {
		try {
			assertNotNull("accepted wrong parameters. ",
					bm.getReservation(null));
		} catch (Exception e) {

		}
		assertEquals(PERSISTED_RESERVATIONID,
				bm.getReservation(PERSISTED_RESERVATIONID).getId());
	}

	@Test
	public void testInsertReservation() {
		try {
			assertNotNull("accepted wrong parameters. ",
					bm.insertReservation(null));
		} catch (Exception e) {

		}
		assertEquals(bm.insertReservation(testReservation),
				PERSISTED_RESERVATIONID);
	}

	@Test
	public void testUpdateReservation() {
		Calendar end = new GregorianCalendar();
		Date newEnd = new GregorianCalendar(2012, 11, 30, 11, 0).getTime();
		end.setTime(newEnd);
		testReservation.setEndTime(end.getTime());
		try {
			bm.updateReservation(null);
		} catch (Exception e) {
			fail("Error: " + e);
		}
		bm.updateReservation(testReservation);
		assertEquals(newEnd, bm.getReservation(PERSISTED_RESERVATIONID)
				.getEndTime());
	}

	@Test
	public void testDeleteReservation() {
		try {
			bm.deleteReservation(null);

		} catch (Exception e) {
			fail("Error: " + e);
		}
		bm.deleteReservation(PERSISTED_RESERVATIONID);
		/*
		 * TODO wait for return of method getReservation if id not used
		 */
		assertNull(bm.getReservation(PERSISTED_RESERVATIONID));
	}
}