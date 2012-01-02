package edu.kit.pse.ass.booking.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityQuery;

public class BookingManagementImpl implements BookingManagement {

	@Inject
	BookingDAO bookingDAO;

	@Inject
	FacilityManagement facilityManagement;

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
		// the concrete instance of FacilityManagement is specified via
		// Dependency Injection
		// fetch matching facilites
		Collection<? extends Facility> facilityCollection = facilityManagement
				.findMatchingFacilities(query);
		// create output array
		ArrayList<FreeFacilityResult> facilityResult = new ArrayList<FreeFacilityResult>();
		Date startDate = (Date) start.clone();
		Date endDate = (Date) end.clone();

		// find free matching facilities
		// if less than 5 are found, shift the reservation time forward up to
		// 2 hours.
		int addQuarterHour = 0;
		while (!facilityCollection.isEmpty() && addQuarterHour < 9
				&& facilityResult.size() < 5) {
			Iterator<? extends Facility> facilityIterator = facilityCollection
					.iterator();
			// collect all free facilities
			while (facilityIterator.hasNext()) {
				Facility facility = facilityIterator.next();
				if (isFacilityFree(facility.getId(), startDate, endDate)) {
					// add the found facility
					facilityResult.add(new FreeFacilityResult(facility,
							(Date) startDate.clone()));
					// remove from queue
					facilityCollection.remove(facility);
				}
			}
			// shift reservation time 15 minutes in the future.
			++addQuarterHour;
			startDate.setTime(startDate.getTime() + 15 * 60000);
			endDate.setTime(endDate.getTime() + 15 * 60000);
		}
		return facilityResult;
	}

	@Override
	@Transactional
	public boolean isFacilityFree(String facilityID, Date startDate,
			Date endDate) {
		Collection<Reservation> reservations = bookingDAO
				.getReservationsOfFacility(facilityID, startDate, endDate);
		if (reservations != null && reservations.size() > 0) {
			return false;
		}
		Facility facility = facilityManagement.getFacility(facilityID);
		Facility parent = facility.getParentFacility();
		if (parent != null
				&& !isFacilityFree(parent.getId(), startDate, endDate)) {
			return false;
		}
		for (Facility containedFacility : facility.getContainedFacilities()) {
			if (!isFacilityFree(containedFacility.getId(), startDate, endDate)) {
				return false;
			}
		}
		return true;
	}
}
