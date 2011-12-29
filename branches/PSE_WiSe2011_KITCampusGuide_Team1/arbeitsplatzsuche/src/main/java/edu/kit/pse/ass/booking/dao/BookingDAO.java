/**
 * 
 */
package edu.kit.pse.ass.booking.dao;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;

/**
 * @author Andreas Bosch
 * 
 */
public interface BookingDAO {

	/**
	 * returns reservations within the given time span of the user with the
	 * given ID. If no Reservation found return null. If a parameter is null
	 * throw exception
	 * 
	 * @param userID
	 *            the user's ID
	 * @param asFrom
	 *            beginning of time span
	 * @param upTo
	 *            end of time span
	 * @return the reservations
	 */
	public Collection<Reservation> getReservationsOfUser(String userID,
			Date asFrom, Date upTo);

	/**
	 * returns reservations within the given time span of the facility with the
	 * given ID. If a parameter is null throw exception
	 * 
	 * @param facilityID
	 *            the id of the facility
	 * @param asFrom
	 *            beginning of time span
	 * @param upTo
	 *            end of time span
	 * @return the reservations
	 */
	public Collection<Reservation> getReservationsOfFacility(String facilityID,
			Date asFrom, Date upTo);

	/**
	 * returns the reservation with the given ID. If a parameter is null throw
	 * exception
	 * 
	 * @param reservationID
	 *            the ID of the reservation
	 * @return the reservation with the given ID
	 */
	public Reservation getReservation(String reservationID);

	/**
	 * inserts the specified reservation and returns its ID. If a parameter is
	 * null throw exception
	 * 
	 * @param reservation
	 *            the reservation to insert
	 * @return the ID of the inserted reservation
	 */
	public String insertReservation(Reservation reservation);

	/**
	 * updates the specified reservation
	 * 
	 * @param reservation
	 */
	public void updateReservation(Reservation reservation);

	/**
	 * deletes the reservation with the given ID
	 * 
	 * @param reservationID
	 */
	public void deleteReservation(String reservationID);
}
