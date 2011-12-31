package edu.kit.pse.ass.booking.dao;

import java.util.Collection;
import java.util.Date;

import edu.kit.pse.ass.entity.Reservation;

public class BookingDAOImpl implements BookingDAO {

	@Override
	public Collection<Reservation> getReservationsOfUser(String userID,
			Date asFrom, Date upTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Reservation> getReservationsOfFacility(String facilityID,
			Date asFrom, Date upTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation getReservation(String reservationID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReservation(Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteReservation(String reservationID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bookingFillWithDummies() {
		// TODO Auto-generated method stub

	}

}
