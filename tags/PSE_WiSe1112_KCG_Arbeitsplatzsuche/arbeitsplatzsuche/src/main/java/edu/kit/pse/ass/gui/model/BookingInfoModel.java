package edu.kit.pse.ass.gui.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Model for start date an duration of an booking event
 * 
 * @author Oliver Schneider
 * 
 */
public abstract class BookingInfoModel {

	/** The start date of the (desired) booking */
	@DateTimeFormat(pattern = "d-M-y HH:mm")
	protected Date start;

	/** The duration of the (desired booking). */
	protected String duration;

	/** Should the whole room be booked. */
	protected boolean wholeRoom;

	/**
	 * Sets now date, duration and wholeRoom to standard values.
	 */
	protected BookingInfoModel() {
		setStartToNow();
		duration = "1:00";
		wholeRoom = false;
	}

	/**
	 * Gets the start date of the (desired) booking.
	 * 
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Sets the start date of the (desired) booking.
	 * 
	 * @param start
	 *            the new start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Gets the duration of the (desired) booking.
	 * 
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the (desired) booking.
	 * 
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Checks if duration has a valid format.
	 * 
	 * @return true, if duration is valid
	 */
	public boolean isDurationValid() {
		return duration != null && duration.matches("[0-9]*:?[0-9]*");
	}

	/**
	 * Gets the end date of the (desired) booking.
	 * 
	 * @return the end date
	 * @throws IllegalStateException
	 *             if the duration has an illegal format.
	 */
	public Date getEnd() throws IllegalStateException {
		Calendar result = Calendar.getInstance();
		result.setTime(start);

		if (isDurationValid()) {
			String[] tmp = duration.split(":");
			try {
				int hour = Integer.parseInt(tmp[0]);
				int minutes = Integer.parseInt(tmp[1]);
				result.add(Calendar.HOUR, hour);
				result.add(Calendar.MINUTE, minutes);

			} catch (NumberFormatException ex) {
				throw new IllegalStateException("Illegal Duration", ex);
			}
		}

		return result.getTime();

	}

	/**
	 * Checks if the whole room should be booked.
	 * 
	 * @return true, if the whole room should be booked
	 */
	public boolean isWholeRoom() {
		return wholeRoom;
	}

	/**
	 * Sets if the whole room should be booked
	 * 
	 * @param wholeRoom
	 *            should the whole room be booked
	 */
	public void setWholeRoom(boolean wholeRoom) {
		this.wholeRoom = wholeRoom;
	}

	/**
	 * Sets the start date to now, rounded to the next 15 minutes.
	 */
	public void setStartToNow() {
		Calendar now = Calendar.getInstance();
		int minutes = now.get(Calendar.MINUTE);
		// round to next 15 minitues;
		now.add(Calendar.MINUTE, 15 - (minutes % 15));
		start = now.getTime();
	}
}
