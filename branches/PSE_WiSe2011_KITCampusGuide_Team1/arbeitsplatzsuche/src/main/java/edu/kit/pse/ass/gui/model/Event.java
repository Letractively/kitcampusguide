package edu.kit.pse.ass.gui.model;

import java.util.Date;

/**
 * Event for Json Response for JQueryWeekCal
 * 
 * @author Oliver Schneider
 * 
 */
public class Event {

	private final String id;
	private final Date start;
	private final Date end;
	private final String title;

	/**
	 * Creates a new event
	 * 
	 * @param id
	 *            a unique id
	 * @param start
	 *            the start time
	 * @param end
	 *            the end time
	 * @param title
	 *            displayed title
	 */
	public Event(String id, Date start, Date end, String title) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
