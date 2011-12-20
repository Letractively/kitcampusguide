/**
 * 
 */
package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;

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
     * @param userID the user id
     * @param facilityIDs the facility i ds
     * @param startDate the start date
     * @param endDate the end date
     * @return the string
     */
    public String book(String userID, Collection<String> facilityIDs, Date startDate, Date endDate);

    /**
     * List reservations of user.
     *
     * @param userID the user id
     * @param asFrom the as from
     * @param upTo the up to
     * @return the collection
     */
    public Collection<Reservation> listReservationsOfUser(String userID, Date asFrom, Date upTo);
    
    /**
     * List reservations of facility.
     *
     * @param facilityID the facility id
     * @param asFrom the as from
     * @param upTo the up to
     * @return the collection
     */
    public Collection<Reservation> listReservationsOfFacility(String facilityID, Date asFrom, Date upTo);

    /**
     * Change reservation end.
     *
     * @param reservationID the reservation id
     * @param newEndDate the new end date
     */
    public void changeReservationEnd(String reservationID, Date newEndDate);
    
    /**
     * Removes the facility from reservation.
     *
     * @param reservationID the reservation id
     * @param facilityID the facility id
     */
    public void removeFacilityFromReservation(String reservationID, String facilityID);
    
    /**
     * Delete reservation.
     *
     * @param reservationID the reservation id
     */
    public void deleteReservation(String reservationID)
    
    /**
     * Gets the reservation.
     *
     * @param reservationID the reservation id
     * @return the reservation
     */
    public Reservation getReservation(String reservationID);

    /**
     * Find free facilites.
     *
     * @param query the query
     * @param start the start
     * @param end the end
     * @param fullyAvailible the fully availible
     * @return the collection
     */
    public Collection<FreeFacilityResult> findFreeFacilites(FacilityQuery query, Date start, Date end, boolean fullyAvailible);
    
    /**
     * Checks if is facility free.
     *
     * @param facilityID the facility id
     * @param startDate the start date
     * @param endDate the end date
     * @return true, if is facility free
     */
    public boolean isFacilityFree(String facilityID, Date startDate, Date endDate);
}
