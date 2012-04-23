package edu.kit.pse.ass.facility.management;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;

/**
 * One instance of the Class FacilityResult represents one result of a facility search. E.g.: one room matching the
 * search and the needed workplaces contained in the room.
 */
public class FacilityResult {

	/** The facility matching the search */
	private final Facility facility;

	/** The matching child facilities. */
	private final Collection<Facility> matchingChildFacilities;

	/**
	 * Instantiates a new facility result.
	 * 
	 * @param facility
	 *            - the matching facility
	 * @param matchingChildFacilities
	 *            - the matching child facilities of the matching facility
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
	 * Get the matching facility.
	 * 
	 * @return the facility
	 */
	public Facility getFacility() {
		return this.facility;
	}

	/**
	 * Get all matching child facilities.
	 * 
	 * @return the matchingChildFacilities
	 */
	public Collection<Facility> getMatchingChildFacilities() {
		return this.matchingChildFacilities;
	}

}
