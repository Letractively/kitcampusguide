package edu.kit.pse.ass.booking.management;

import java.util.Date;

import edu.kit.pse.ass.entity.Facility;

// TODO: Auto-generated Javadoc
/**
 * The Class FreeFacilityResult.
 */
public class FreeFacilityResult {

	/** The facility. */
	Facility facility;

	/** The start. */
	Date start;

	/**
	 * Instantiates a new free facility result.
	 * 
	 * @param facility
	 *            the facility
	 * @param start
	 *            the start
	 */
	public FreeFacilityResult(Facility facility, Date start) {
		this.facility = facility;
		this.start = start;
	}

	/**
	 * Gets the facility.
	 * 
	 * @return the facility
	 */
	public Facility getFacility() {
		return facility;
	}

	/**
	 * Sets the facility.
	 * 
	 * @param facility
	 *            the new facility
	 */
	public void setFacility(Facility facility) {
		this.facility = facility;
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
}
