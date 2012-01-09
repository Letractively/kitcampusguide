package edu.kit.pse.ass.booking.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfUser(java.lang
	 * .String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfUser(String userID,
			Date asFrom, Date upTo) throws IllegalArgumentException {
		if (userID == null || userID.equals("") || asFrom == null
				|| upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = jpaTemplate
				.find("from t_reservation");
		// find matching reservations and add them to the result
		// TODO diese Filterung kann auch die Datenbank übernehmen. Dafür müsste
		// man die SearchQuery für das jpaTemplate schreiben
		for (Reservation tmp : reservations) {
			if (userID.equals(tmp.getBookingUserId())
					&& (tmp.getStartTime().after(asFrom) || tmp.getStartTime()
							.equals(asFrom)) && (tmp.getEndTime().before(upTo))
					|| tmp.getEndTime().equals(upTo))
				result.add(tmp);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfFacility(java
	 * .lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfFacility(String facilityID,
			Date asFrom, Date upTo) throws IllegalArgumentException {
		if (facilityID == null || facilityID.equals("") || asFrom == null
				|| upTo == null) {
			throw new IllegalArgumentException("One parameter is null or empty");
		}
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = jpaTemplate
				.find("from t_reservation");

		// find matching reservations and add them to the result

		// TODO diese Filterung kann auch die Datenbank übernehmen. Dafür müsste
		// man die SearchQuery für das jpaTemplate schreiben

		for (Reservation tmp : reservations) {
			if (tmp.getBookedFacilityIds().contains(facilityID)
					&& tmp.getStartTime().after(asFrom)
					&& tmp.getStartTime().before(upTo))
				result.add(tmp);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#getReservation(java.lang.String)
	 */
	@Override
	public Reservation getReservation(String reservationID)
			throws IllegalArgumentException {
		if (reservationID == null || reservationID.equals("")) {
			throw new IllegalArgumentException("Parameter is null or empty");
		}
		return jpaTemplate.find(Reservation.class, reservationID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#insertReservation(edu.kit.pse.
	 * ass.entity.Reservation)
	 */
	@Override
	@Transactional
	public String insertReservation(Reservation reservation) {
		if (reservation == null) {
			throw new IllegalArgumentException("Reservation is null");
		}
		// TODO generated ID return
		jpaTemplate.persist(reservation);
		return reservation.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#updateReservation(edu.kit.pse.
	 * ass.entity.Reservation)
	 */
	@Override
	public String updateReservation(Reservation reservation)
			throws IllegalArgumentException {
		if (reservation == null) {
			throw new IllegalArgumentException("Reservation is null");
		}
		Reservation id = jpaTemplate.merge(reservation);
		return id.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.booking.dao.BookingDAO#deleteReservation(java.lang.String
	 * )
	 */
	@Override
	@Transactional
	public void deleteReservation(String reservationID) {
		if (reservationID == null || reservationID.equals("")) {
			throw new IllegalArgumentException("Parameter is null or empty");
		}
		Reservation reservation = getReservation(reservationID);
		if (reservation != null)
			jpaTemplate.remove(reservation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#bookingFillWithDummies()
	 */
	@Override
	public void bookingFillWithDummies() {
		// TODO
		// the available facilities
		List<String> facilityIDs = Arrays.asList("ID##01", "ID##02", "ID##03",
				"ID##04", "ID##05", "ID##06");
		// the reservations IDs
		List<String> resvIDs = Arrays.asList("RID0001", "RID0002", "RID0003",
				"RID0004", "RID0005", "RID0006", "RID0007");
		// the userIDs
		List<String> userIDs = Arrays.asList("uaaaa@student.kit.edu",
				"uaaab@student.kit.edu", "uaaac@student.kit.edu",
				"uaaad@student.kit.edu", "uaaae@student.kit.edu",
				"uaaaf@student.kit.edu");
		// the start dates
		List<GregorianCalendar> start = Arrays.asList(new GregorianCalendar(
				2012, 1, 1, 9, 0), new GregorianCalendar(2012, 1, 1, 12, 0),
				new GregorianCalendar(2012, 1, 1, 15, 0));
		// the end dates, all of them after the latest start
		List<GregorianCalendar> end = Arrays.asList(new GregorianCalendar(2012,
				1, 1, 11, 0), new GregorianCalendar(2012, 1, 1, 14, 0),
				new GregorianCalendar(2012, 1, 1, 17, 0));

		// create reservations and persist them
		int i = 0;
		int[] sizes = { facilityIDs.size(), resvIDs.size(), userIDs.size(),
				start.size(), end.size() };
		int k = facilityIDs.size();
		// get size of shortest list
		for (int j : sizes) {
			if (j < k) {
				k = j;
			}
		}
		for (String resvID : resvIDs) {
			// makes the lists size independent
			i %= k;
			Reservation resvTmp = new Reservation(start.get(i).getTime(), end
					.get(i).getTime(), userIDs.get(i));
			resvTmp.addBookedFacilityId(facilityIDs.get(i));
			resvTmp.setId(resvID);
			jpaTemplate.merge(resvTmp);
			// adds a day to each star and end, thus preventing double
			// reservations
			start.get(i).add(Calendar.DATE, 1);
			end.get(i).add(Calendar.DATE, 1);
			i++;
		}
	}
}
