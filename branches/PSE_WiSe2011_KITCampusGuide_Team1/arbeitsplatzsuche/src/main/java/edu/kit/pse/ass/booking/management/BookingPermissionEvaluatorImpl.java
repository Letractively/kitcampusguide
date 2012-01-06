package edu.kit.pse.ass.booking.management;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import edu.kit.pse.ass.booking.dao.BookingDAO;

public class BookingPermissionEvaluatorImpl implements PermissionEvaluator {

	@Autowired
	private BookingDAO bookingDAO;

	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		return false;
	}

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
				// TODO maybe a check for non-existence is necessary?
				String user = bookingDAO.getReservation(targetId.toString())
						.getBookingUserId();

				if (authentication.getName().equals(user)) {
					return true;
				}
			}

		}
		return false;
	}
}
