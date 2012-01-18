package edu.kit.pse.ass.gui.model;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;

/**
 * This class represents the model of a Reservation.
 * 
 * It wraps a Reservation object and is needed to get and format data not provided by the Reservation class.
 * 
 * @author Jannis Koch
 * 
 */
public class ReservationModel implements Comparable<ReservationModel> {

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/** the reservation to wrap. */
	private final Reservation reservation;

	/**
	 * the room of the reservation (the room where the booked facilities are located in - the room must not necessarily
	 * be part of the reservation!).
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

	// TODO pass facilityManagement in constructor as @inject does not (yet?) work when Spring does not create the
	// objects
	/**
	 * constructs a new ReservationModel wrapping the specified reservation
	 * 
	 * @param reservation
	 *            the reservation to wrap
	 * @param facilityManagement
	 *            the facilityManagement
	 */
	public ReservationModel(Reservation reservation, FacilityManagement facilityManagement) {
		setFacilityManagement(facilityManagement);
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
	 * sets the start time of this reservation
	 * 
	 * @param startTime
	 *            the start time
	 */
	public void setStartTime(Date startTime) {
		this.reservation.setStartTime(startTime);
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
	 * @return the id of this reservation
	 */
	public String getId() {
		return reservation.getId();
	}

	/**
	 * returns the number of hours of the duration, not counting additional time, e.g. a duration of 130 minutes will
	 * return 2
	 * 
	 * @return the number of hours of the duration
	 */
	public int getDurationHours() {

		// Duration in ms
		long duration = reservation.getEndTime().getTime() - reservation.getStartTime().getTime();

		// Calculate hours
		int hours = (int) (duration / (1000 * 60 * 60));

		return hours;
	}

	/**
	 * returns the number of minutes of the duration, not counting additional time, e.g. a duration of 130 minutes will
	 * return 10
	 * 
	 * @return the number of minutes of the duration
	 */
	public int getDurationMinutes() {

		// Duration in ms
		long duration = reservation.getEndTime().getTime() - reservation.getStartTime().getTime();

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
				Facility bookedFacility = null;
				try {
					bookedFacility = facilityManagement.getFacility(facilityID);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FacilityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
		Room room = getRoom();
		if (room != null) {
			return room.getName();
		} else {
			return "";
		}
	}

	/**
	 * returns a formatted name of the room where the booked facilities are located in.
	 * 
	 * @return the formatted name of the room where the booked facilities are located in
	 */
	public String getFormattedRoomName() {
		Room room = getRoom();
		if (room != null) {
			return formatRoomName(room);
		} else {
			return "";
		}
	}

	/**
	 * returns a formatted room name of the given Room.
	 * 
	 * if the given Room is null, an empty String is returned.
	 * 
	 * @param room
	 *            the room
	 * @return the formatted room name of the given Room
	 */
	private static String formatRoomName(Room room) {
		String roomName = "";
		if (room != null) {
			if (room.getName().isEmpty()) {
				roomName = "Raum";
			} else {
				roomName = room.getName();
			}
			if (!room.getNumber().isEmpty()) {
				roomName += " " + room.getNumber();
			}
		}
		return roomName;

	}

	/**
	 * returns the name of the building where the booked facilities are located in.
	 * 
	 * @return the name of the building where the booked facilities are located in
	 */
	public String getBuildingName() {
		Room room = getRoom();
		if (room != null) {
			return room.getParentFacility().getName();
		} else {
			return "";
		}
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

				Facility bookedFacility = null;
				try {
					bookedFacility = facilityManagement.getFacility(facilityID);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FacilityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (bookedFacility instanceof Workplace) {
					// reservation consists of one workplace only
					workplaceCount = 1;
				} else if (bookedFacility instanceof Room) {
					// return number of workplaces of the room
					workplaceCount = bookedFacility.getContainedFacilities().size();
				}
			}
		}

		return workplaceCount;
	}

	/**
	 * Sets the facilityManagement
	 * 
	 * @param facilityManagement
	 *            the facilityManagement
	 */
	public void setFacilityManagement(FacilityManagement facilityManagement) {
		this.facilityManagement = facilityManagement;
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
			Facility bookedFacility = null;
			try {
				bookedFacility = facilityManagement.getFacility(facilityID);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacilityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (bookedFacility instanceof Room) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Compares another ReservationModel with this instance using the start time of the reservation
	 * 
	 * @param otherReservationModel
	 *            the other ReservationModel to compare
	 * @return the comparison result
	 */
	@Override
	public int compareTo(ReservationModel otherReservationModel) {
		if (this.reservation.getStartTime() != null) {
			return this.reservation.getStartTime().compareTo(otherReservationModel.reservation.getStartTime());
		} else {
			return 0;
		}

	}
}
