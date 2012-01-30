package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.facility.dao.FacilityDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityFinder.
 */
public abstract class FacilityFinder {

	/**
	 * Execute.
	 * 
	 * @param facilityDAO
	 *            The DataAccessObject
	 * @return the collection<? extends facility>
	 */
	public abstract Collection<FacilityResult> execute(FacilityDAO facilityDAO);

}
