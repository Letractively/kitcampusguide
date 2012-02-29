/**
 * 
 */
package edu.kit.pse.ass.booking.management;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Reservation;

/**
 * The Class BookingPermissionEvaluatorImplTest tests the BookingPermissionEvaluatorImpl
 * 
 * @author Andreas Bosch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/evaluator-applicationContext-*.xml" })
public class BookingPermissionEvaluatorImplTest {

	/** The dao used in BookingPermissionEvaluatorImpl, referenced here for mocking purpose. */
	@Autowired
	BookingDAO dao;

	/** The evaluator, using autowire to create since it uses dependency injection. */
	@Autowired
	BookingPermissionEvaluatorImpl evaluator;

	/**
	 * Reset the mock object before running a test.
	 */
	@Before
	public void setUp() {
		reset(dao);
	}

	/**
	 * Test allowed access for editing.
	 */
	@Test
	public void testSuccessEdit() {
		String reservationID = "12345";
		String testUser = "some@user";
		Reservation reservation = new Reservation(new Date(), new Date(), testUser);
		reservation.setId(reservationID);
		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");
		expect(dao.getReservation(reservationID)).andReturn(reservation);
		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Booking", "edit");
		assertTrue("Error", val);

	}

	/**
	 * Test allowed access for deletion.
	 */
	@Test
	public void testSuccessDelete() {
		String reservationID = "12345";
		String testUser = "some@user";
		Reservation reservation = new Reservation(new Date(), new Date(), testUser);
		reservation.setId(reservationID);
		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");

		expect(dao.getReservation(reservationID)).andReturn(reservation);
		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Booking", "delete");
		assertTrue("Error", val);

	}

	/**
	 * Test allowed access for viewing.
	 */
	@Test
	public void testSuccessView() {
		String reservationID = "12345";
		String testUser = "some@user";
		Reservation reservation = new Reservation(new Date(), new Date(), testUser);
		reservation.setId(reservationID);
		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");

		expect(dao.getReservation(reservationID)).andReturn(reservation);
		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Booking", "view");
		assertTrue("Error", val);

	}

	/**
	 * Test allowed access for nonexistend booking.
	 */
	@Test
	public void testNoBooking() {
		String reservationID = "12345";
		String testUser = "some@user";

		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");

		expect(dao.getReservation(reservationID)).andThrow(new IllegalArgumentException());
		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Booking", "view");
		assertTrue("Access to nonexistend booking blocked", val);
	}

	/**
	 * Test forbidden access for wrong user.
	 */
	@Test
	public void testWrongUser() {
		String reservationID = "12345";
		String testUser = "some@user";
		String testBookingUser = "other@user";
		Reservation reservation = new Reservation(new Date(), new Date(), testBookingUser);
		reservation.setId(reservationID);
		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");

		expect(dao.getReservation(reservationID)).andReturn(reservation).times(3);
		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Booking", "view");
		assertFalse("View access although user is different", val);

		val = evaluator.hasPermission(authentication, reservationID, "Booking", "edit");
		assertFalse("Edit access although user is different", val);

		val = evaluator.hasPermission(authentication, reservationID, "Booking", "delete");
		assertFalse("Delete access although user is different", val);
	}

	/**
	 * Test forbidden access for wrong object type.
	 */
	@Test
	public void testWrongType() {
		String reservationID = "12345";
		String testUser = "some@user";

		Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, "dummy");

		replay(dao);

		boolean val = evaluator.hasPermission(authentication, reservationID, "Workplace", "view");
		assertFalse("Access to non-Booking object not blocked", val);
	}
}
