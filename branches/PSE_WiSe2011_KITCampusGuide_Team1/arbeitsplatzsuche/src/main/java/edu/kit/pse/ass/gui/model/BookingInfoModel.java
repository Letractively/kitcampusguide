package edu.kit.pse.ass.gui.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Model for start date an duration of an Bookingevent
 * 
 * @author Oliver Schneider
 * 
 */
public abstract class BookingInfoModel {

	/** The start. */
	@DateTimeFormat(pattern = "d-M-y HH:mm")
	protected Date start;

	/** The duration. */
	protected String duration;

	/** The whole room. */
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
	 * Gets the start.
	 * 
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 * 
	 * @param start
	 *            the new start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Checks if is duration valid.
	 * 
	 * @return true, if is duration valid
	 */
	public boolean isDurationValid() {
		return duration != null && duration.matches("[0-9]*:?[0-9]*");
	}

	/**
	 * Gets the end.
	 * 
	 * @return the end
	 * @throws IllegalStateException
	 *             the illegal state exception
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
	 * Checks if is whole room.
	 * 
	 * @return true, if is whole room
	 */
	public boolean isWholeRoom() {
		return wholeRoom;
	}

	/**
	 * Sets the whole room.
	 * 
	 * @param wholeRoom
	 *            the new whole room
	 */
	public void setWholeRoom(boolean wholeRoom) {
		this.wholeRoom = wholeRoom;
	}

	/**
	 * sets the start date to now, rounded to the next 15 minutes
	 */
	public void setStartToNow() {
		Calendar now = Calendar.getInstance();
		int minutes = now.get(Calendar.MINUTE);
		// round to next 15 minitues;
		now.add(Calendar.MINUTE, 15 - (minutes % 15));
		start = now.getTime();
	}
}
