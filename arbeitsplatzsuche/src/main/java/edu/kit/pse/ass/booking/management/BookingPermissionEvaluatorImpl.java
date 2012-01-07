package edu.kit.pse.ass.booking.management;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Reservation;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingPermissionEvaluatorImpl.
 */
public class BookingPermissionEvaluatorImpl implements PermissionEvaluator {

	/** The booking dao. */
	@Autowired
	private BookingDAO bookingDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission
	 * (org.springframework.security.core.Authentication, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission
	 * (org.springframework.security.core.Authentication, java.io.Serializable,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		if (targetId == null
				|| (targetId instanceof String && ((String) targetId).isEmpty())) {
			return false;
		}
		if (targetType.equalsIgnoreCase("Booking")) {
			// allow anyone to edit their own bookings
			if (permission.equals("edit") || permission.equals("delete")
					|| permission.equals("view")) {
				Reservation resv;
				try {
					resv = bookingDAO.getReservation(targetId.toString());
				} catch (IllegalArgumentException e) {
					// if ID does not exist, then allow access for proper error
					// handling
					return true;
				} catch (DataRetrievalFailureException e) {
					return true;
				}
				if (authentication.getName().equals(resv.getBookingUserId())) {
					return true;
				}
			}

		}
		return false;
	}
}
