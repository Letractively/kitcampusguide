package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.FacilityQuery;

/**
 * The Interface BookingManagement.
 * 
 * @author Andreas Bosch
 */
public interface BookingManagement {

	/**
	 * Book.
	 * 
	 * @param userID
	 *            the user id
	 * @param facilityIDs
	 *            the facility ids
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the reservationID
	 * @throws FacilityNotFreeException
	 *             the facility not free exception
	 * @throws IllegalArgumentException
	 *             a given parameter is null or invalid
	 * @throws FacilityNotFoundException
	 *             a wrong ID was passed, no facility exists with one the given ID
	 * @throws BookingNotAllowedException
	 *             the reservation is not allowed
	 * @throws IllegalDateException
	 *             a date is in the past.
	 * @throws BookingQuotaExceededExcpetion
	 *             the modified reservation violates a quota
	 */
	String book(String userID, Collection<String> facilityIDs, Date startDate, Date endDate)
			throws FacilityNotFreeException, IllegalArgumentException, FacilityNotFoundException,
			BookingNotAllowedException, IllegalDateException, BookingQuotaExceededExcpetion;

	/**
	 * List reservations of user.
	 * 
	 * @param userID
	 *            the user id
	 * @param asFrom
	 *            the as from
	 * @param upTo
	 *            the up to
	 * @return the collection
	 */
	Collection<Reservation> listReservationsOfUser(String userID, Date asFrom, Date upTo);

	/**
	 * List reservations of facility.
	 * 
	 * @param facilityID
	 *            the facility id
	 * @param asFrom
	 *            the as from
	 * @param upTo
	 *            the up to
	 * @return the collection
	 */
	Collection<Reservation> listReservationsOfFacility(String facilityID, Date asFrom, Date upTo);

	/**
	 * Change reservation end.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @param newEndDate
	 *            the new end date
	 * @throws IllegalArgumentException
	 *             a parameter is null or invalid
	 * @throws FacilityNotFreeException
	 *             the facility of the reservation is already booked
	 * @throws IllegalStateException
	 *             the illegal state exception
	 * @throws BookingQuotaExceededExcpetion
	 *             the modified reservation violates a quota
	 */
	void changeReservationEnd(String reservationID, Date newEndDate) throws IllegalArgumentException,
			FacilityNotFreeException, IllegalStateException, BookingQuotaExceededExcpetion;

	/**
	 * Removes the facility from reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @param facilityID
	 *            the facility id
	 */
	void removeFacilityFromReservation(String reservationID, String facilityID);

	/**
	 * Delete reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 */
	void deleteReservation(String reservationID);

	/**
	 * Gets the reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @return the reservation
	 * @throws ReservationNotFoundException
	 *             there is no reservation with the given ID
	 * @throws IllegalArgumentException
	 *             the reservationID is null or the string is empty
	 */
	Reservation getReservation(String reservationID) throws IllegalArgumentException, ReservationNotFoundException;

	/**
	 * Find free facilities.
	 * 
	 * @param query
	 *            the query
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param fullyAvailable
	 *            the fully available
	 * @return the collection
	 */
	Collection<FreeFacilityResult> findFreeFacilites(FacilityQuery query, Date start, Date end,
			boolean fullyAvailable);

	/**
	 * Checks if facility is free.
	 * 
	 * @param facilityID
	 *            the facility id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if is facility free
	 * @throws FacilityNotFoundException
	 *             the facility with the given ID does not exist
	 * @throws IllegalArgumentException
	 *             a parameter is null or invalid
	 */

	boolean isFacilityFree(String facilityID, Date startDate, Date endDate) throws IllegalArgumentException,
			FacilityNotFoundException;

}
