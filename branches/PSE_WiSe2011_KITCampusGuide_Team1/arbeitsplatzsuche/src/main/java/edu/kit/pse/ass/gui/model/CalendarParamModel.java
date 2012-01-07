package edu.kit.pse.ass.gui.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
