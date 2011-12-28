package edu.kit.pse.ass.booking.management;

import java.util.Date;

import edu.kit.pse.ass.entity.Facility;

public class FreeFacilityResult {

	Facility facility;

	Date start;

	public FreeFacilityResult(Facility facility, Date start) {
		this.facility = facility;
		this.start = start;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
}
