package edu.kit.pse.ass.booking.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.booking.dao.BookingDAO;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityQuery;

// TODO: Auto-generated Javadoc
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
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#book(java.lang.String
	 * , java.util.Collection, java.util.Date, java.util.Date)
	 */
	@Override
	@Secured({ "ROLE_STUDENT", "ROLE_TUTOR" })
	public String book(String userID, Collection<String> facilityIDs,
			Date startDate, Date endDate) throws FacilityNotFreeException,
			IllegalArgumentException {
		// check given dates
		if ((startDate == null) || (endDate == null)
				|| (startDate.after(endDate)))
			throw new IllegalArgumentException(
					"The start date has to be before the end date and both must not be null.");

		// check user
		if (userID == null || userID.equals(""))
			throw new IllegalArgumentException(
					"userID must be not null and not empty.");
		Collection<Reservation> userReservations = bookingDAO
				.getReservationsOfUser(userID, startDate, endDate);
		if (userReservations.size() > 0)
			throw new IllegalArgumentException(
					"The user has a reservation at the same time");

		// check each facility
		for (String tmpID : facilityIDs) {
			if (isTheFacilityAvailable(tmpID, startDate, endDate) == false) {
				throw new FacilityNotFreeException(
						"The facility is not available at the given time.");
			}
		}

		// create a reservation
		Reservation reservation = new Reservation(startDate, endDate, userID);
		reservation.setBookedFacilityIDs(facilityIDs);
		String reservationID = bookingDAO.insertReservation(reservation);

		return reservationID;
	}

	/**
	 * Checks the reservations of the facility and all contained facilities.
	 * 
	 * @param facilityID
	 *            the id of the facility
	 * @param asFrom
	 *            the as from
	 * @param upTo
	 *            the up to
	 * @return true, when the facility and all junior facilities have no
	 *         reservations to the given time.
	 * @throws IllegalArgumentException
	 *             the facilityID is not valid or a ID of a contained facility
	 *             is not valid.
	 */
	private boolean isTheFacilityAvailable(String facilityID, Date asFrom,
			Date upTo) throws IllegalArgumentException {
		if (facilityID.equals("") || facilityID == null || asFrom == null
				|| upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		// get the facility
		Facility facility;
		try {
			facility = facilityManagement.getFacility(facilityID);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The facility does not exist.");
		}
		// check the reservations of the facility
		if (bookingDAO
				.getReservationsOfFacility(facility.getId(), asFrom, upTo)
				.size() > 0)
			return false;
		// check the reservations of contained facilities
		if (facility.getContainedFacilities() != null) {
			Iterator<Facility> containedIterator = facility
					.getContainedFacilities().iterator();
			for (int i = 0; i < facility.getContainedFacilities().size(); i++) {
				if (isTheFacilityAvailable(containedIterator.next().getId(),
						asFrom, upTo))
					return false;
			}
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#listReservationsOfUser
	 * (java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	@PreAuthorize("authentication.name == #userID")
	public Collection<Reservation> listReservationsOfUser(String userID,
			Date asFrom, Date upTo) throws IllegalArgumentException {
		if (userID == null || userID.equals("") || asFrom == null
				|| upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#
	 * listReservationsOfFacility(java.lang.String, java.util.Date,
	 * java.util.Date)
	 */
	@Override
	public Collection<Reservation> listReservationsOfFacility(
			String facilityID, Date asFrom, Date upTo)
			throws IllegalArgumentException {
		if (facilityID == null || facilityID.equals("") || asFrom == null
				|| upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		return bookingDAO.getReservationsOfFacility(facilityID, asFrom, upTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#changeReservationEnd
	 * (java.lang.String, java.util.Date)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'edit')")
	public void changeReservationEnd(String reservationID, Date newEndDate)
			throws IllegalArgumentException {
		if (reservationID == null || reservationID.equals("")
				|| newEndDate == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		bookingDAO.getReservation(reservationID).setEndTime(newEndDate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.management.BookingManagement#
	 * removeFacilityFromReservation(java.lang.String, java.lang.String)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'edit')")
	public void removeFacilityFromReservation(String reservationID,
			String facilityID) throws IllegalArgumentException {
		if (reservationID == null || reservationID.equals("")) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#deleteReservation
	 * (java.lang.String)
	 */
	@Override
	@PreAuthorize("hasPermission(#reservationID, 'Booking', 'delete')")
	public void deleteReservation(String reservationID)
			throws IllegalArgumentException {
		if (reservationID == null || reservationID.equals("")) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#getReservation(java
	 * .lang.String)
	 */
	@Override
	public Reservation getReservation(String reservationID)
			throws IllegalArgumentException {
		if (reservationID == null || reservationID.equals("")) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Reservation reserv = bookingDAO.getReservation(reservationID);
		if (reserv == null) {
			throw new IllegalArgumentException("The reservationID is invalid.");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#findFreeFacilites
	 * (edu.kit.pse.ass.facility.management.FacilityQuery, java.util.Date,
	 * java.util.Date, boolean)
	 */
	@Override
	public Collection<FreeFacilityResult> findFreeFacilites(
			FacilityQuery query, Date start, Date end, boolean fullyAvailable)
			throws IllegalArgumentException {
		if (query == null || start == null || end == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.management.BookingManagement#isFacilityFree(java
	 * .lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	@Transactional
	public boolean isFacilityFree(String facilityID, Date startDate,
			Date endDate) throws IllegalArgumentException {
		if (facilityID == null || facilityID.equals("") || startDate == null
				|| endDate == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}

		Collection<Reservation> reservations = bookingDAO
				.getReservationsOfFacility(facilityID, startDate, endDate);
		if (reservations != null && reservations.size() > 0) {
			return false;
		}
		Facility facility = facilityManagement.getFacility(facilityID);
		Facility parent = facility;
		// check parent facilities
		while ((parent = parent.getParentFacility()) != null) {
			reservations = bookingDAO.getReservationsOfFacility(parent.getId(),
					startDate, endDate);
			if (reservations != null && reservations.size() > 0) {
				return false;
			}
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
	private boolean areChildFacilitiesFree(Facility facility, Date startDate,
			Date endDate) {
		Collection<Reservation> reservations;
		for (Facility containedFacility : facility.getContainedFacilities()) {
			reservations = bookingDAO.getReservationsOfFacility(
					containedFacility.getId(), startDate, endDate);
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
