package edu.kit.pse.ass.booking.management;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Reservation;

/**
 * The Class BookingQuotasImpl.
 */
public class BookingQuotasImpl implements BookingQuotas {

	/** The booking dao. */
	@Autowired
	BookingDAO bookingDAO;

	/** The Constant MILLISECONDS_PER_HOUR. */
	private static final int MILLISECONDS_PER_HOUR = 3600000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingQuotas#createBookingQuotaExceptionNewReservation(java.lang.String,
	 * java.util.Collection, java.util.Date, java.util.Date)
	 */
	@Override
	public BookingQuotaExceededExcpetion createBookingQuotaExceptionNewReservation(String userID,
			Collection<String> facilityIDs, Date startDate, Date endDate) {
		if (!StringUtils.hasText(userID) || facilityIDs == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("At least one parameter is null");
		}
		return createBookingQuotaException(null, userID, facilityIDs, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingQuotas#createBookingQuotaExceptionModifyReservation(java.lang.String,
	 * java.lang.String, java.util.Collection, java.util.Date, java.util.Date)
	 */
	@Override
	public BookingQuotaExceededExcpetion createBookingQuotaExceptionModifyReservation(String bookingID,
			String userID, Collection<String> facilityIDs, Date startDate, Date endDate) {
		if (!StringUtils.hasText(bookingID) || !StringUtils.hasText(userID) || facilityIDs == null
				|| startDate == null || endDate == null) {
			throw new IllegalArgumentException("At least one parameter is null");
		}
		return createBookingQuotaException(bookingID, userID, facilityIDs, startDate, endDate);
	}

	/**
	 * Creates the booking quota exception if a quota is exceeded.
	 * 
	 * @param bookingID
	 *            the booking id
	 * @param userID
	 *            the user id
	 * @param facilityIDs
	 *            the facility ids
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booking quota exceeded excpetion
	 */
	private BookingQuotaExceededExcpetion createBookingQuotaException(String bookingID, String userID,
			Collection<String> facilityIDs, Date startDate, Date endDate) {
		assert userID != null && facilityIDs != null && startDate != null && endDate != null;
		Collection<GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		boolean isTutor = false, isAdmin = false;
		for (GrantedAuthority role : roles) {
			if (role.getAuthority().equals("ROLE_TUTOR")) {
				isTutor = true;
			} else if (role.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
			}
		}

		// check for length of booking and return Exception on violation
		int limit = getQuotaLimitHoursPerBooking(isAdmin, isTutor);
		if (limit >= 0 && dateDifferenceLargerThan(endDate, startDate, limit * MILLISECONDS_PER_HOUR)) {
			return new BookingQuotaExceededExcpetion("Booking too long.",
					BookingQuotaExceededExcpetion.Quota.HOURS_PER_BOOKING, limit, userID);
		}

		// check for bookings per day, return exception on violation
		return createQuotaExceptionReservationsPerDay(isAdmin, isTutor, bookingID, userID, startDate, endDate);
	}

	/**
	 * Creates the quota exceeded exception if there are too many reservations per day.
	 * 
	 * @param isAdmin
	 *            is the user an admin
	 * @param isTutor
	 *            is the user a tutor
	 * @param bookingID
	 *            the booking id
	 * @param userID
	 *            the user id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booking quota exceeded excpetion
	 */
	private BookingQuotaExceededExcpetion createQuotaExceptionReservationsPerDay(boolean isAdmin, boolean isTutor,
			String bookingID, String userID, Date startDate, Date endDate) {
		int maxReservations = getQuotaLimitReservationsPerDay(isAdmin, isTutor);
		if (maxReservations < 0) {
			return null;
		}

		GregorianCalendar start = new GregorianCalendar();
		start.setTime(startDate);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);

		GregorianCalendar lastDay = new GregorianCalendar();
		lastDay.setTime(endDate);
		lastDay.set(Calendar.HOUR_OF_DAY, 0);
		lastDay.set(Calendar.MINUTE, 0);
		lastDay.set(Calendar.SECOND, 0);

		GregorianCalendar end = new GregorianCalendar();
		end.setTime(startDate);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);

		while (!start.after(lastDay)) {

			Collection<Reservation> reservations = bookingDAO.getReservationsOfUser(userID, start.getTime(),
					end.getTime());
			if (reservations != null) {
				if (bookingID != null && !reservations.isEmpty()) {
					Iterator<Reservation> resIt = reservations.iterator();
					while (resIt.hasNext()) {
						Reservation res = resIt.next();
						if (bookingID.equals(res.getBookedFacilityIds())) {
							resIt.remove();
						}
					}
				}
				if (reservations.size() >= maxReservations) {
					return new BookingQuotaExceededExcpetion("Too many bookings per day.",
							BookingQuotaExceededExcpetion.Quota.BOOKINGS_PER_DAY, maxReservations, userID);
				}
			}

			start.add(Calendar.DAY_OF_MONTH, 1);
			end.add(Calendar.DAY_OF_MONTH, 1);
		}
		return null;
	}

	/**
	 * Gets the quota limit reservations per day.
	 * 
	 * @param isAdmin
	 *            the is admin
	 * @param isTutor
	 *            the is tutor
	 * @return the quota limit reservations per day
	 */
	private int getQuotaLimitReservationsPerDay(boolean isAdmin, boolean isTutor) {
		if (isAdmin) {
			return -1;
		} else if (isTutor) {
			return 4;
		} else {
			return 2;
		}
	}

	/**
	 * Gets the maximum booking length in hours.
	 * 
	 * @param isAdmin
	 *            is the user an admin
	 * @param isTutor
	 *            is the user a tutor
	 * @return the maximum booking length in hours. less than 0 is unlimited
	 */
	private int getQuotaLimitHoursPerBooking(boolean isAdmin, boolean isTutor) {
		if (isAdmin) {
			return -1;
		} else if (isTutor) {
			return 3;
		} else {
			return 2;
		}
	}

	/**
	 * Date difference larger than the value in milliseconds.
	 * 
	 * @param date1
	 *            the date1
	 * @param date2
	 *            the date2
	 * @param difference
	 *            the difference in milliseconds
	 * @return true, if successful
	 */
	private boolean dateDifferenceLargerThan(Date date1, Date date2, long difference) {
		assert date1 != null && date2 != null;
		long diff = date2.getTime() - date1.getTime();
		if (diff < 0) {
			diff = -diff;
		}
		return diff > difference;
	}
}
