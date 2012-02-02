package edu.kit.pse.ass.booking.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.facility.management.FacilityResult;

/**
 * The Class BookingManagementImpl.
 */
public class BookingManagementImpl implements BookingManagement {

	/** The booking dao. */
	@Autowired
	private BookingDAO bookingDAO;

	/** The facility management. */
	@Autowired
	private FacilityManagement facilityManagement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#book(java.lang.String , java.util.Collection,
	 * java.util.Date, java.util.Date)
	 */
	@Override
	@Transactional
	@PreAuthorize("authentication.name == #userID or hasRole('ROLE_ADMIN')")
	public String book(String userID, Collection<String> facilityIDs, Date startDate, Date endDate)
			throws FacilityNotFreeException, IllegalArgumentException, FacilityNotFoundException,
			BookingNotAllowedException, IllegalDateException {
		// check given dates
		if ((startDate == null) || (endDate == null)) {
			throw new IllegalArgumentException("start or end date is null");
		}
		if (startDate.after(endDate)) {
			throw new IllegalDateException("The start date has to be before end date.");
		}
		Date now = Calendar.getInstance().getTime();
		if (startDate.before(now)) {
			throw new IllegalDateException("start date is in the past.");
		}

		// check user
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("userID must be not null and not empty.");
		}

		// check user reservations
		Collection<Reservation> userReservations = bookingDAO.getReservationsOfUser(userID, startDate, endDate);
		if (userReservations.size() > 0) {
			throw new BookingNotAllowedException(userID, "The user has a reservation at the same time");
		}

		// check each facility
		if (facilityIDs == null || facilityIDs.isEmpty()) {
			throw new IllegalArgumentException("facilityIDs must be not null and not be empty.");
		}
		for (String tmpID : facilityIDs) {
			try {
				if (isFacilityFree(tmpID, startDate, endDate) == false) {
					throw new FacilityNotFreeException("The facility is not available at the given time.");
				}
			} catch (IllegalArgumentException e) {
				throw new FacilityNotFreeException("The facility does not exist.");
			}
		}

		// create a reservation
		Reservation reservation = new Reservation(startDate, endDate, userID);
		reservation.setBookedFacilityIDs(facilityIDs);

		// return the id of the reservation
		return bookingDAO.insertReservation(reservation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#listReservationsOfUser (java.lang.String,
	 * java.util.Date, java.util.Date)
	 */
	@Override
	@PreAuthorize("authentication.name == #userID")
	public Collection<Reservation> listReservationsOfUser(String userID, Date asFrom, Date upTo)
			throws IllegalArgumentException {
		if (userID == null || userID.isEmpty() || asFrom == null || upTo == null || asFrom.after(upTo)) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		return bookingDAO.getReservationsOfUser(userID, asFrom, upTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement# listReservationsOfFacility(java.lang.String,
	 * java.util.Date, java.util.Date)
	 */
	@Override
	public Collection<Reservation> listReservationsOfFacility(String facilityID, Date asFrom, Date upTo)
			throws IllegalArgumentException {
		if (facilityID == null || facilityID.equals("") || asFrom == null || upTo == null || asFrom.after(upTo)) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		return bookingDAO.getReservationsOfFacility(facilityID, asFrom, upTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#changeReservationEnd (java.lang.String, java.util.Date)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'edit')")
	@Transactional
	public void changeReservationEnd(String reservationID, Date newEndDate) throws IllegalArgumentException,
			FacilityNotFreeException, IllegalStateException {
		if (reservationID == null || reservationID.isEmpty() || newEndDate == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Reservation resv = bookingDAO.getReservation(reservationID);
		if (resv == null) {
			throw new IllegalStateException("The Reservation does not exist");
		}
		if (!newEndDate.after(resv.getStartTime())) {
			throw new IllegalArgumentException("The new end date is before the start date.");
		} else if (newEndDate.after(resv.getEndTime())) {
			for (String facilityID : resv.getBookedFacilityIds()) {
				try {
					// TODO does isFacilityFree ignore the reservation, which we want to change here?
					if (!isFacilityFree(facilityID, resv.getEndTime(), newEndDate)) {
						throw new FacilityNotFreeException(
								"One of the facilities is not free at the specified time.");
					}
				} catch (FacilityNotFoundException e) {
					throw new IllegalStateException("Facility of reservation does not exist.");
				}
			}
		}
		// TODO check other reservation of the same user!
		resv.setEndTime(newEndDate);

		bookingDAO.updateReservation(resv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement# removeFacilityFromReservation(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'edit')")
	@Transactional
	public void removeFacilityFromReservation(String reservationID, String facilityID)
			throws IllegalArgumentException {
		if (reservationID == null || reservationID.isEmpty()) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Reservation reservation = bookingDAO.getReservation(reservationID);
		reservation.removeBookedFacilityId(facilityID);
		bookingDAO.updateReservation(reservation);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#deleteReservation (java.lang.String)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'delete')")
	@Transactional
	public void deleteReservation(String reservationID) throws IllegalArgumentException {
		if (reservationID == null || reservationID.isEmpty()) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		bookingDAO.deleteReservation(reservationID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#getReservation(java .lang.String)
	 */
	@Override
	public Reservation getReservation(String reservationID) throws IllegalArgumentException,
			ReservationNotFoundException {
		if (reservationID == null || reservationID.isEmpty()) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Reservation reserv = bookingDAO.getReservation(reservationID);
		if (reserv == null) {
			throw new ReservationNotFoundException("The reservationID is invalid.");
		}
		return reserv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#findFreeFacilites
	 * (edu.kit.pse.ass.facility.management.FacilityQuery, java.util.Date, java.util.Date, boolean)
	 */
	@Override
	@Transactional
	public Collection<FreeFacilityResult> findFreeFacilites(FacilityQuery query, Date start, Date end,
			boolean fullyAvailable) throws IllegalArgumentException {
		if (query == null || start == null || end == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Date now = Calendar.getInstance().getTime();
		if (start.before(now) || end.before(now) || end.before(start)) {
			throw new IllegalArgumentException("start or end date is in the past or end is after start date.");
		}
		// the concrete instance of FacilityManagement is specified via
		// Dependency Injection
		// fetch matching facilites
		Collection<FacilityResult> facilityCollection = facilityManagement.findMatchingFacilities(query);
		// create output array
		ArrayList<FreeFacilityResult> freeFacilityResult = new ArrayList<FreeFacilityResult>();
		Date startDate = (Date) start.clone();
		Date endDate = (Date) end.clone();

		if (facilityCollection == null) {
			return freeFacilityResult;
		}
		// find free matching facilities
		// if less than 5 are found, shift the reservation time forward up to
		// 2 hours.
		int addQuarterHour = 0;
		while (!facilityCollection.isEmpty() && addQuarterHour < 9 && freeFacilityResult.size() < 5) {
			Iterator<FacilityResult> facilityIterator = facilityCollection.iterator();
			// collect all free facilities
			while (facilityIterator.hasNext()) {
				FacilityResult facilityResult = facilityIterator.next();
				try {
					if (fullyAvailable) {
						if (isFacilityFree(facilityResult.getFacility().getId(), startDate, endDate)) {
							// add the found facility
							freeFacilityResult.add(new FreeFacilityResult(facilityResult.getFacility(),
									(Date) startDate.clone(), facilityResult.getMatchingChildFacilities()));
							// remove from queue
							facilityIterator.remove();
						}
					} else {
						Collection<Facility> freeChildren = getAndTestRequiredFreeChildFacilities(facilityResult,
								query.getRequiredChildCount(), startDate, endDate);
						if (freeChildren != null) {
							// add the found facility
							freeFacilityResult.add(new FreeFacilityResult(facilityResult.getFacility(),
									(Date) startDate.clone(), freeChildren));
							// remove from queue
							facilityIterator.remove();
						}
					}

				} catch (FacilityNotFoundException e) {
					// do not add if not found

					// remove from queue
					facilityIterator.remove();
				}
			}
			// shift reservation time 15 minutes in the future.
			++addQuarterHour;
			startDate.setTime(startDate.getTime() + 15 * 60000);
			endDate.setTime(endDate.getTime() + 15 * 60000);
		}
		return freeFacilityResult;
	}

	/**
	 * Are required child facilities free.
	 * 
	 * @param facilityResult
	 *            the facility result
	 * @param requiredChildCount
	 *            the required child count
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if successful
	 */
	private Collection<Facility> getAndTestRequiredFreeChildFacilities(FacilityResult facilityResult,
			int requiredChildCount, Date startDate, Date endDate) {
		if (facilityResult == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		LinkedList<Facility> free = new LinkedList<Facility>();
		Collection<Reservation> reservations = bookingDAO.getReservationsOfFacility(facilityResult.getFacility()
				.getId(), startDate, endDate);
		if (reservations != null && reservations.size() > 0) {
			return null;
		}
		Facility parent = facilityResult.getFacility().getParentFacility();
		// check parent facilities
		while (parent != null) {
			reservations = bookingDAO.getReservationsOfFacility(parent.getId(), startDate, endDate);
			if (reservations != null && reservations.size() > 0) {
				return null;
			}
			parent = parent.getParentFacility();
		}
		for (Facility requiredFacility : facilityResult.getMatchingChildFacilities()) {
			reservations = bookingDAO.getReservationsOfFacility(requiredFacility.getId(), startDate, endDate);
			if (reservations != null && reservations.size() > 0) {
				return null;
			}
			if (!areChildFacilitiesFree(requiredFacility, startDate, endDate)) {
				return null;
			}
			free.add(requiredFacility);
		}
		return free;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#isFacilityFree(java .lang.String, java.util.Date,
	 * java.util.Date)
	 */
	@Override
	@Transactional
	public boolean isFacilityFree(String facilityID, Date startDate, Date endDate)
			throws IllegalArgumentException, FacilityNotFoundException {
		if (facilityID == null || facilityID.isEmpty() || startDate == null || endDate == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		// TODO check start before end date
		Collection<Reservation> reservations = bookingDAO
				.getReservationsOfFacility(facilityID, startDate, endDate);
		if (reservations != null && reservations.size() > 0) {
			return false;
		}
		Facility facility = facilityManagement.getFacility(facilityID);
		Facility parent = facility.getParentFacility();
		// check parent facilities
		while (parent != null) {
			reservations = bookingDAO.getReservationsOfFacility(parent.getId(), startDate, endDate);
			if (reservations != null && reservations.size() > 0) {
				return false;
			}
			parent = parent.getParentFacility();
		}
		// check child facilities
		return areChildFacilitiesFree(facility, startDate, endDate);
	}

	/**
	 * Are child facilities free.
	 * 
	 * @param facility
	 *            the facility
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if successful
	 */
	@Transactional
	private boolean areChildFacilitiesFree(Facility facility, Date startDate, Date endDate) {
		Collection<Reservation> reservations;
		if (facility.getContainedFacilities() == null) {
			return true;
		}
		for (Facility containedFacility : facility.getContainedFacilities()) {
			reservations = bookingDAO.getReservationsOfFacility(containedFacility.getId(), startDate, endDate);
			if (reservations != null && reservations.size() > 0) {
				return false;
			}
			if (!areChildFacilitiesFree(containedFacility, startDate, endDate)) {
				return false;
			}
		}
		return true;
	}
}
