package edu.kit.pse.ass.gui.model;

import java.util.Date;

/**
 * Used by jquery weekcal
 * 
 * @author Oliver Schneider
 * 
 */
public class CalendarParamModel {

	/**
	 * Start Date of displayed weekdays
	 */
	private Date start;
	/**
	 * End Date of displaced weekdays
	 */
	private Date end;

	/**
	 * @return start date to display
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * sets start date to display
	 * 
	 * @param start
	 *            start date to display
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * get end date to display
	 * 
	 * @return end date to display
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * set end date to display
	 * 
	 * @param end
	 *            end date to display
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
}
