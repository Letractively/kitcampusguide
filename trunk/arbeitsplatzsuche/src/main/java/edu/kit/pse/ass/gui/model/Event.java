package edu.kit.pse.ass.gui.model;

import java.util.Date;

/**
 * Event for Json Response for JQueryWeekCal.
 * 
 * @author Oliver Schneider
 * 
 */
public class Event {

	/** A unique id for the room */
	private final String id;
	/** the start date of the event/booking */
	private final Date start;
	/** the end date of the event/booking */
	private final Date end;
	/** the title of the event/booking */
	private final String title;
	/** is the whole room booked */
	private final boolean wholeRoom;

	/**
	 * Creates a new event.
	 * 
	 * @param id
	 *            a unique id
	 * @param start
	 *            the start time
	 * @param end
	 *            the end time
	 * @param title
	 *            displayed title
	 * @param wholeRoom
	 *            is the whole Room booked
	 */
	public Event(String id, Date start, Date end, String title, boolean wholeRoom) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.wholeRoom = wholeRoom;
	}

	/**
	 * Get the id of the event
	 * 
	 * @return the id of the event
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the start date of the event
	 * 
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Get the end date of the event
	 * 
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Get the title of the event
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Is the whole room booked
	 * 
	 * @return the wholeRoom
	 */
	public boolean isWholeRoom() {
		return wholeRoom;
	}

}
