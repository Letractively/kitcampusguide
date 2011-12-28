package edu.kit.pse.ass.booking.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.User;

public class BookingDAOImplTest {

	private static final String USERID = "uzzzz@student.kit.edu";
	private static final String FACILITYID = "#SOME_FACILITY_ID#";
	private static final String PERSISTED_RESERVATIONID = "#SOME_RESERVATION_ID#";

	/**
	 * Setup a BookingDAO with a dummy reservation
	 * */
	private final BookingDAO bm = new BookingDAOImpl();

	@Before
	public void setUp() throws Exception {
		Date start = new GregorianCalendar(2012, 12, 30, 9, 0).getTime();
		Date end = new GregorianCalendar(2012, 12, 30, 10, 0).getTime();
		// TODO change User constructor to right one
		User testUser = new User();
		bm.insertReservation(new Reservation(start, end, testUser.getId()));
	}

	@Test
	public void testGetReservationsOfUser() {
		// BookingDAO bm = new BookingDAOImpl();
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 31).getTime();
		/*
		 * try { bm.getReservationsOfUser(null, from, to);
		 * fail("accepted wrong parameters"); } catch (IllegalArgumentException
		 * expeted) { }
		 */
		bm.getReservationsOfUser(USERID, from, to);
	}

	@Test
	public void testGetReservationsOfFacility() {
		BookingDAO bm = new BookingDAOImpl();
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 30).getTime();
		/*
		 * try { bm.getReservationsOfFacility(null, from, to);
		 * fail("accepted wrong parameters"); } catch (IllegalArgumentException
		 * expeted) { }
		 */
		bm.getReservationsOfFacility(FACILITYID, from, to);
	}

	@Test
	public void testGetReservation() {
		BookingDAO bm = new BookingDAOImpl();
		/*
		 * try { bm.getReservation(null); fail("accepted wrong parameters"); }
		 * catch (IllegalArgumentException expeted) { }
		 */
		bm.getReservation(PERSISTED_RESERVATIONID);
	}

	@Test
	public void testInsertReservation() {
		BookingDAO bm = new BookingDAOImpl();
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 30).getTime();
		Reservation reservation = new Reservation(from, to, USERID);
		/*
		 * try { bm.insertReservation(null); fail("accepted wrong parameters");
		 * } catch (IllegalArgumentException expeted) { }
		 */

		reservation.addBookedFacilityId(FACILITYID);

		bm.insertReservation(reservation);
	}

	@Test
	public void testUpdateReservation() {
		BookingDAO bm = new BookingDAOImpl();

		// Reservation reservation = bm.getReservation(PERSISTED_RESERVATIONID);
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 30).getTime();
		Reservation reservation = new Reservation(from, to, USERID);
		reservation.setId(PERSISTED_RESERVATIONID);
		reservation.addBookedFacilityId(FACILITYID);

		Calendar end = new GregorianCalendar();
		end.setTime(reservation.getEndTime());
		reservation.setEndTime(end.getTime());

		bm.updateReservation(reservation);
	}

	@Test
	public void testDeleteReservation() {
		BookingDAO bm = new BookingDAOImpl();
		bm.deleteReservation(PERSISTED_RESERVATIONID);
	}

}
