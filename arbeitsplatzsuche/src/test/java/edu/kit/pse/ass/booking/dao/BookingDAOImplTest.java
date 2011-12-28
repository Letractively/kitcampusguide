package edu.kit.pse.ass.booking.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.User;

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
	private final BookingDAO bm = new BookingDAOImpl();

	User testUser = new User();
	Date start = new GregorianCalendar(2012, 12, 30, 9, 0).getTime();
	Date end = new GregorianCalendar(2012, 12, 30, 10, 0).getTime();
	Reservation testReservation = new Reservation(start, end, testUser.getId());
	Collection<Reservation> testReservationCol = null;

	@Before
	public void setUp() throws Exception {
		// TODO might add more to user
		testUser.setId(USERID);
		testReservation.addBookedFacilityId(FACILITYID);
		testReservation.setId(PERSISTED_RESERVATIONID);
		testReservationCol.add(testReservation);
		bm.insertReservation(testReservation);
	}

	@Test
	public void testGetReservationsOfUser() {
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 31).getTime();

		assertNotNull(bm);
		try {
			bm.getReservationsOfUser(null, from, to);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}
		assertEquals(testReservationCol,
				bm.getReservationsOfUser(USERID, from, to));
	}

	@Test
	public void testGetReservationsOfFacility() {
		Date from = new GregorianCalendar(2012, 12, 30).getTime();
		Date to = new GregorianCalendar(2012, 12, 31).getTime();

		assertNotNull(bm);
		try {
			bm.getReservationsOfFacility(null, from, to);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}

		assertEquals(testReservationCol,
				bm.getReservationsOfFacility(FACILITYID, from, to));
	}

	@Test
	public void testGetReservation() {
		assertNotNull(bm);
		try {
			bm.getReservation(null);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}
		assertEquals(testReservation.getId(),
				bm.getReservation(PERSISTED_RESERVATIONID).getId());
	}

	@Test
	public void testInsertReservation() {
		assertNotNull(bm);
		try {
			bm.insertReservation(null);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}
	}

	@Test
	public void testUpdateReservation() {
		Calendar end = new GregorianCalendar();
		Date newEnd = new GregorianCalendar(2012, 12, 30, 11, 0).getTime();
		end.setTime(newEnd);
		testReservation.setEndTime(end.getTime());
		assertNotNull(bm);
		try {
			bm.updateReservation(null);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}

		bm.updateReservation(testReservation);
		assertEquals(newEnd, bm.getReservation(PERSISTED_RESERVATIONID)
				.getEndTime());
	}

	@Test
	// TODO wait for finish of method
	public void testDeleteReservation() {
		assertNotNull(bm);
		try {
			bm.deleteReservation(null);
			fail("accepted wrong parameters");
		} catch (IllegalArgumentException expected) {
		}
		bm.deleteReservation(PERSISTED_RESERVATIONID);
	}

}
