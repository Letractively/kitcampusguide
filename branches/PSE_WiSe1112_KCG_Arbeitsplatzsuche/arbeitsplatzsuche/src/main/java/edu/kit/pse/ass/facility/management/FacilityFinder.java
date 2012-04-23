package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.facility.dao.FacilityDAO;

/**
 * The abstract Class FacilityFinder, each implementation is used for a different facility subclass
 */
public abstract class FacilityFinder {

	/**
	 * Execute this finder on the given DAO
	 * 
	 * @param facilityDAO
	 *            - The DataAccessObject
	 * @return a collection of facility results with the matching properties
	 */
	public abstract Collection<FacilityResult> execute(FacilityDAO facilityDAO);

}
