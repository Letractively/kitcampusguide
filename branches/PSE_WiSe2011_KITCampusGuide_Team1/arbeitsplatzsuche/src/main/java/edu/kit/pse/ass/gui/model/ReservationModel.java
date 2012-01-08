package edu.kit.pse.ass.gui.model;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityManagement;

/**
 * This class represents the model of a Reservation.
 * 
 * It wraps a Reservation object and is needed to get and format data not
 * provided by the Reservation class.
 * 
 * @author Jannis Koch
 * 
 */
public class ReservationModel {

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/** the reservation to wrap. */
	private final Reservation reservation;

	/**
	 * the room of the reservation (the room where the booked facilities are
	 * located in - the room must not necessarily be part of the reservation!).
	 */
	private Room room;

	/**
	 * the workplace count of reservation
	 */
	private int workplaceCount;

	/**
	 * constructs a new ReservationModel wrapping an newly created reservation
	 * 
	 */
	public ReservationModel() {
		this.reservation = new Reservation();
	}

	/**
	 * constructs a new ReservationModel wrapping the specified reservation
	 * ======= constructs a new ReservationModel wrapping the specified
	 * reservation. >>>>>>> .r1143
	 * 
	 * @param reservation
	 *            the reservation to wrap
	 */
	public ReservationModel(Reservation reservation) {
		this.reservation = reservation;
		setWorkplaceCount(calculateWorkplaceCountFromReservation());
	}

	/**
	 * returns the workplace count
	 * 
	 * @return the workplace count
	 */
	public int getWorkplaceCount() {
		return workplaceCount;
	}

	/**
	 * sets the workplace count
	 * 
	 * @param workplaceCount
	 *            the workplace count
	 */
	public void setWorkplaceCount(int workplaceCount) {
		this.workplaceCount = workplaceCount;
	}

	/**
	 * returns the start time of this reservation
	 * 
	 * @return the start time of this reservation
	 */
	public Date getStartTime() {
		return reservation.getStartTime();
	}

	/**
	 * returns the end time of this reservation
	 * 
	 * @return the end time of this reservation
	 */
	@DateTimeFormat(pattern = "d-M-y HH:mm")
	public Date getEndTime() {
		return reservation.getEndTime();
	}

	/**
	 * sets the end time of this reservation
	 * 
	 * @param endTime
	 *            the end time of this reservation
	 */
	public void setEndTime(Date endTime) {
		this.reservation.setEndTime(endTime);
	}

	/**
	 * returns the id of this reservation
	 * 
	 * @return
	 */
	public String getId() {
		return reservation.getId();
	}

	/**
	 * returns the number of hours of the duration, not counting additional
	 * time, e.g. a duration of 130 minutes will return 2
	 * 
	 * @return the number of hours of the duration
	 */
	public int getDurationHours() {

		// Duration in ms
		long duration = reservation.getEndTime().getTime()
				- reservation.getStartTime().getTime();

		// Calculate hours
		int hours = (int) (duration / (1000 * 60 * 60));

		return hours;
	}

	/**
	 * returns the number of minutes of the duration, not counting additional
	 * time, e.g. a duration of 130 minutes will return 10
	 * 
	 * @return the number of minutes of the duration
	 */
	public int getDurationMinutes() {

		// Duration in ms
		long duration = reservation.getEndTime().getTime()
				- reservation.getStartTime().getTime();

		// Calculate minutes
		int minutes = (int) (duration / (1000 * 60) % 60);

		return minutes;
	}

	/**
	 * returns the room of this reservation
	 * 
	 * @return the room of this reservation
	 */
	public Room getRoom() {
		if (room == null) {

			// Get first facility of booked facilities
			Iterator<String> i = reservation.getBookedFacilityIds().iterator();
			if (i.hasNext()) {
				String facilityID = i.next();
				Facility bookedFacility = tempGetFacility(facilityID); // Facility
																		// bookedFacility
																		// =
				// facilityManagement.getFacility(facilityID);

				if (bookedFacility instanceof Room) {
					// we assume that the reservation consists of one room only
					// (system does not make reservations with more than one
					// room)
					room = (Room) bookedFacility;
				} else if (bookedFacility instanceof Workplace) {
					// we assume that the reservation consists of workplaces
					// only
					// (system does not make reservation with different Facility
					// types), all of which are in the same room
					room = (Room) bookedFacility.getParentFacility();
				}
			}
		}
		return room;
	}

	/**
	 * returns the name of the room where the booked facilities are located in.
	 * 
	 * @return the name of the room where the booked facilities are located in
	 */
	public String getRoomName() {
		return getRoom().getName();
	}

	/**
	 * returns the name of the building where the booked facilities are located
	 * in.
	 * 
	 * @return the name of the building where the booked facilities are located
	 *         in
	 */
	public String getBuildingName() {
		return getRoom().getParentFacility().getName();
	}

	/**
	 * calculates the number of workplaces booked
	 * 
	 * @return the number of workplaces booked
	 */
	private int calculateWorkplaceCountFromReservation() {

		int workplaceCount = 0;

		if (reservation.getBookedFacilityIds().size() > 1) {
			// we assume the reservation consists of workplaces (the system does
			// not make reservations with more than one facility that is not a
			// workplace)
			workplaceCount = reservation.getBookedFacilityIds().size();

		} else {

			// Get the one booked facility
			Iterator<String> i = reservation.getBookedFacilityIds().iterator();
			if (i.hasNext()) {
				String facilityID = i.next();
				// Facility bookedFacility =
				// facilityManagement.getFacility(facilityID);
				Facility bookedFacility = tempGetFacility(facilityID);

				if (bookedFacility instanceof Workplace) {
					// reservation consists of one workplace only
					workplaceCount = 1;
				} else if (bookedFacility instanceof Room) {
					// return number of workplaces of the room
					workplaceCount = bookedFacility.getContainedFacilities()
							.size();
				}
			}
		}

		return workplaceCount;
	}

	/**
	 * Temp get facility.
	 * 
	 * @param facilityID
	 *            the facility id
	 * @return the facility
	 */
	private Facility tempGetFacility(String facilityID) {
		Building b = new Building();
		b.setName("Informatik Hauptgebäude");

		Room r = new Room();
		r.setId("roomid3");
		r.setName("Seminarraum 1");
		b.addContainedFacility(r);

		Workplace w = new Workplace();
		w.setName("Arbeitsplatz 1");
		r.addContainedFacility(w);

		if (facilityID.equals("wpid1")) {
			return w;
		} else if (facilityID.equals("wpid2")) {
			w.setName("Arbeitsplatz 2");
			return w;
		} else if (facilityID.equals("roomid3")) {
			Workplace w1 = new Workplace();
			w1.setName("Arbeitsplatz 2");
			r.addContainedFacility(w1);
			Workplace w2 = new Workplace();
			w2.setName("Arbeitsplatz 3");
			r.addContainedFacility(w2);
			return r;
		}
		return w;
	}

	/**
	 * returns true if the bookedFacility of the reservation is a room.
	 * 
	 * @return true if the bookedFacility of the reservation is a room
	 */
	public boolean bookedFacilityIsRoom() {
		// Get first facility of booked facilities
		Iterator<String> i = reservation.getBookedFacilityIds().iterator();
		if (i.hasNext()) {
			String facilityID = i.next();
			Facility bookedFacility = tempGetFacility(facilityID); // Facility
																	// bookedFacility
																	// =
			// facilityManagement.getFacility(facilityID);

			if (bookedFacility instanceof Room) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
