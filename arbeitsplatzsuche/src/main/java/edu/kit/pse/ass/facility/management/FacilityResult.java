package edu.kit.pse.ass.facility.management;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;

/**
 * The Class FacilityResult represents the result of a facility search.
 */
public class FacilityResult {

	/** The found facility. */
	private final Facility facility;

	/** The matching child facilities. */
	private final Collection<Facility> matchingChildFacilities;

	/**
	 * Instantiates a new facility result.
	 * 
	 * @param facility
	 *            the facility
	 * @param matchingChildFacilities
	 *            the matching child facilities
	 */
	protected FacilityResult(Facility facility, Collection<Facility> matchingChildFacilities) {
		this.facility = facility;
		if (matchingChildFacilities == null) {
			this.matchingChildFacilities = new ArrayList<Facility>();
		} else {
			this.matchingChildFacilities = matchingChildFacilities;
		}
	}

	/**
	 * Gets the found facility.
	 * 
	 * @return the facility
	 */
	public Facility getFacility() {
		return this.facility;
	}

	/**
	 * Gets the matching child facilities.
	 * 
	 * @return the matchingChildFacilities
	 */
	public Collection<Facility> getMatchingChildFacilities() {
		return this.matchingChildFacilities;
	}

}
