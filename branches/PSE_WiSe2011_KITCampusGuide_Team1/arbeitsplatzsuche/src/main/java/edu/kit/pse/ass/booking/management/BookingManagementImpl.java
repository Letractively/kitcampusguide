package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityQuery;

public class BookingManagementImpl implements BookingManagement {

	@Override
	public String book(String userID, Collection<String> facilityIDs,
			Date startDate, Date endDate) throws FacilityNotFreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Reservation> listReservationsOfUser(String userID,
			Date asFrom, Date upTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Reservation> listReservationsOfFacility(
			String facilityID, Date asFrom, Date upTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeReservationEnd(String reservationID, Date newEndDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFacilityFromReservation(String reservationID,
			String facilityID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteReservation(String reservationID) {
		// TODO Auto-generated method stub

	}

	@Override
	public Reservation getReservation(String reservationID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FreeFacilityResult> findFreeFacilites(
			FacilityQuery query, Date start, Date end, boolean fullyAvailable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFacilityFree(String facilityID, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return false;
	}

}
