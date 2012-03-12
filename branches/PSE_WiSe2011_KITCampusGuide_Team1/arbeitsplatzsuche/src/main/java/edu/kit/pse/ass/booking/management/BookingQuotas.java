package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;

/**
 * The Interface BookingQuotas defines the methods the BookingManagement is using to check quotas.
 */
public interface BookingQuotas {

	/**
	 * Create a BookingQuotaExceededException if necessary when creating a reservation.
	 * 
	 * @param userID
	 *            the user id
	 * @param facilityIDs
	 *            the facility i ds
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return a BookingQuotaExceededExcpetion or null if no quota is exceeded
	 */
	BookingQuotaExceededExcpetion createBookingQuotaExceptionNewReservation(String userID,
			Collection<String> facilityIDs, Date startDate, Date endDate);

	/**
	 * Create a BookingQuotaExceededException if necessaray when modifying a reservation.
	 * 
	 * @param bookingID
	 *            the booking id
	 * @param userID
	 *            the user id
	 * @param facilityIDs
	 *            the facility i ds
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return a BookingQuotaExceededExcpetion or null if no quota is exceeded
	 */
	BookingQuotaExceededExcpetion createBookingQuotaExceptionModifyReservation(String bookingID, String userID,
			Collection<String> facilityIDs, Date startDate, Date endDate);

}