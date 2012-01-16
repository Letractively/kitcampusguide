/**
 * 
 */
package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.FacilityQuery;

// TODO: Auto-generated Javadoc
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
	 *            the facility i ds
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the reservationID
	 * @throws FacilityNotFreeException
	 *             the facility not free exception
	 * @throws FacilityNotFoundException
	 * @throws IllegalArgumentException
	 * @throws BookingNotAllowedException
	 */
	public String book(String userID, Collection<String> facilityIDs, Date startDate, Date endDate)
			throws FacilityNotFreeException, IllegalArgumentException, FacilityNotFoundException,
			BookingNotAllowedException;

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
	public Collection<Reservation> listReservationsOfUser(String userID, Date asFrom, Date upTo);

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
	public Collection<Reservation> listReservationsOfFacility(String facilityID, Date asFrom, Date upTo);

	/**
	 * Change reservation end.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @param newEndDate
	 *            the new end date
	 * @throws FacilityNotFreeException
	 * @throws IllegalArgumentException
	 */
	public void changeReservationEnd(String reservationID, Date newEndDate) throws IllegalArgumentException,
			FacilityNotFreeException;

	/**
	 * Removes the facility from reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @param facilityID
	 *            the facility id
	 */
	public void removeFacilityFromReservation(String reservationID, String facilityID);

	/**
	 * Delete reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 */
	public void deleteReservation(String reservationID);

	/**
	 * Gets the reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @return the reservation
	 * @throws ReservationNotFoundException
	 * @throws IllegalArgumentException
	 */
	public Reservation getReservation(String reservationID) throws IllegalArgumentException,
			ReservationNotFoundException;

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
	public Collection<FreeFacilityResult> findFreeFacilites(FacilityQuery query, Date start, Date end,
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
	 * @throws IllegalArgumentException
	 */

	public boolean isFacilityFree(String facilityID, Date startDate, Date endDate)
			throws IllegalArgumentException, FacilityNotFoundException;

}
