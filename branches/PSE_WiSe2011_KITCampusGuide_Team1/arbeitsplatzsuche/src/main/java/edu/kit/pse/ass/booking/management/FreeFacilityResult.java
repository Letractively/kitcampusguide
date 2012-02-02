package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import edu.kit.pse.ass.entity.Facility;

/**
 * The Class FreeFacilityResult.
 */
public class FreeFacilityResult {

	/** The child facilities. */
	Collection<Facility> childFacilities;

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
	 * @param freeChildren
	 */
	public FreeFacilityResult(Facility facility, Date start, Collection<Facility> freeChildren) {
		this.facility = facility;
		this.start = start;
		if (freeChildren != null) {
			this.childFacilities = freeChildren;
		} else {
			this.childFacilities = new LinkedList<Facility>();
		}
	}

	/**
	 * Gets the facility.
	 * 
	 * @return the facility
	 */
	public Collection<Facility> getChildFacilities() {
		return childFacilities;
	}

	/**
	 * Sets the facility.
	 * 
	 * @param facilities
	 *            the new facility
	 * @throws IllegalArgumentException
	 *             facility is null.
	 */
	public void setChildFacilities(Collection<Facility> facilities) throws IllegalArgumentException {
		if (facilities == null) {
			throw new IllegalArgumentException("Parameter is null");
		}
		this.childFacilities = facilities;
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
	 * @throws IllegalArgumentException
	 *             facility is null.
	 */
	public void setFacility(Facility facility) throws IllegalArgumentException {
		if (facility == null) {
			throw new IllegalArgumentException("Parameter is null");
		}
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
	 * @throws IllegalArgumentException
	 *             start is null
	 */
	public void setStart(Date start) throws IllegalArgumentException {
		if (start == null) {
			throw new IllegalArgumentException("Parameter is null");
		}
		this.start = start;
	}
}
