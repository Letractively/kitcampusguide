package edu.kit.pse.ass.booking.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Reservation;

/**
 * The Class BookingDAOImpl.
 */
public class BookingDAOImpl implements BookingDAO {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/**
	 * Getter for the jpaTemplate.
	 * 
	 * @return the jpaTemplate
	 */
	public JpaTemplate getJpaTemplate() {
		return jpaTemplate;
	}

	/**
	 * Setter for the jpaTemplate.
	 * 
	 * @param jpaTemplate
	 *            the jpaTemplate to set
	 */
	public void setJpaTemplate(JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfUser(java.lang .String, java.util.Date,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfUser(String userID, Date asFrom, Date upTo)
			throws IllegalArgumentException {
		if (userID == null || userID.equals("") || asFrom == null || upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		if (asFrom.after(upTo)) {
			throw new IllegalArgumentException("start-date is after end-date");
		}
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = jpaTemplate.find(
				"from t_reservation r WHERE r.bookingUserId LIKE ?", userID);

		// TODO remove duplicate checks

		// find matching reservations and add them to the result
		for (Reservation tmp : reservations) {
			if (tmp.getBookingUserId().equals(userID)) {
				Date end = tmp.getEndTime();
				Date start = tmp.getStartTime();

				// time quantum starts before reservation ends
				// and ends after reservation starts
				if (start.before(upTo) && end.after(asFrom)) {
					result.add(tmp);
				}
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfFacility(java .lang.String, java.util.Date,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfFacility(String facilityID, Date asFrom, Date upTo)
			throws IllegalArgumentException {
		if (facilityID == null || facilityID.equals("") || asFrom == null || upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		if (asFrom.after(upTo)) {
			throw new IllegalArgumentException("start-date is after end-date");
		}
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = jpaTemplate.find(
				"from t_reservation r WHERE ? IN elements(r.bookedFacilityIds)", facilityID);

		// TODO remove duplicate checks

		// find matching reservations and add them to the result
		for (Reservation tmp : reservations) {
			if (tmp.getBookedFacilityIds().contains(facilityID)) {
				Date end = tmp.getEndTime();
				Date start = tmp.getStartTime();

				// time quantum starts before reservation ends
				// and ends after reservation starts
				if (start.before(upTo) && end.after(asFrom)) {
					result.add(tmp);
				}
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservation(java.lang.String)
	 */
	@Override
	public Reservation getReservation(String reservationID) throws IllegalArgumentException {
		if (reservationID == null || reservationID.isEmpty()) {
			throw new IllegalArgumentException("Parameter is null or empty");
		}
		return jpaTemplate.find(Reservation.class, reservationID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#insertReservation(edu.kit.pse. ass.entity.Reservation)
	 */
	@Override
	@Transactional
	public String insertReservation(Reservation reservation) {
		if (reservation == null) {
			throw new IllegalArgumentException("Reservation is null");
		}
		jpaTemplate.persist(reservation);
		return reservation.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#updateReservation(edu.kit.pse. ass.entity.Reservation)
	 */
	@Override
	public String updateReservation(Reservation reservation) throws IllegalArgumentException {
		if (reservation == null) {
			throw new IllegalArgumentException("Reservation is null");
		}
		Reservation id = jpaTemplate.merge(reservation);
		return id.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#deleteReservation(java.lang.String )
	 */
	@Override
	@Transactional
	public void deleteReservation(String reservationID) {
		if (reservationID == null || reservationID.isEmpty()) {
			throw new IllegalArgumentException("Parameter is null or empty");
		}
		Reservation reservation = getReservation(reservationID);
		if (reservation != null) {
			jpaTemplate.remove(reservation);
		}
	}
}
