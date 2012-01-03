package edu.kit.pse.ass.booking.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.orm.jpa.JpaTemplate;

import edu.kit.pse.ass.entity.Reservation;

public class BookingDAOImpl implements BookingDAO {

	/** The jpa template. */
	@Inject
	private JpaTemplate jpaTemplate;

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfUser(java.lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfUser(String userID,
			Date asFrom, Date upTo) {
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = new ArrayList<Reservation>();
		reservations = jpaTemplate.find("from t_reservation");
		Iterator<Reservation> reservationIterator = reservations.iterator();
		// find matching reservations and add them to the result
		
		// TODO diese Filterung kann auch die Datenbank übernehmen. Dafür müsste
		// man die SearchQuery für das jpaTemplate schreiben
		
		for (int i = 0; i < reservations.size(); i++) {
			Reservation tmp = reservationIterator.next();
			if (userID.equals(tmp.getBookingUserId())
					&& tmp.getStartTime().after(asFrom)
					&& tmp.getStartTime().before(upTo))
				result.add(tmp);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservationsOfFacility(java.lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Reservation> getReservationsOfFacility(String facilityID,
			Date asFrom, Date upTo) {
		Collection<Reservation> result = new ArrayList<Reservation>();
		Collection<Reservation> reservations = new ArrayList<Reservation>();
		reservations = jpaTemplate.find("from t_reservation");
		Iterator<Reservation> reservationIterator = reservations.iterator();
		// find matching reservations and add them to the result
		
		// TODO diese Filterung kann auch die Datenbank übernehmen. Dafür müsste
		// man die SearchQuery für das jpaTemplate schreiben
		
		for (int i = 0; i < reservations.size(); i++) {
			Reservation tmp = reservationIterator.next();
			if (tmp.getBookedFacilityIds().contains(facilityID)
					&& tmp.getStartTime().after(asFrom)
					&& tmp.getStartTime().before(upTo))
				result.add(tmp);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#getReservation(java.lang.String)
	 */
	@Override
	public Reservation getReservation(String reservationID) {
		return jpaTemplate.find(Reservation.class, reservationID);
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#insertReservation(edu.kit.pse.ass.entity.Reservation)
	 */
	@Override
	public String insertReservation(Reservation reservation) {
		//TODO generate ID
		jpaTemplate.persist(reservation);
		return reservation.getId();
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#updateReservation(edu.kit.pse.ass.entity.Reservation)
	 */
	@Override
	public void updateReservation(Reservation reservation) {
		jpaTemplate.merge(reservation);
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.booking.dao.BookingDAO#deleteReservation(java.lang.String)
	 */
	@Override
	public void deleteReservation(String reservationID) {
		Reservation reservation = getReservation(reservationID);
		if (reservation != null)
			jpaTemplate.remove(reservation);
	}

	/* (non-Javadoc)
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
			jpaTemplate.persist(resvTmp);
			// adds a day to each star and end, thus preventing double
			// reservations
			start.get(i).add(Calendar.DATE, 1);
			end.get(i).add(Calendar.DATE, 1);
			i++;
		}
	}
}
