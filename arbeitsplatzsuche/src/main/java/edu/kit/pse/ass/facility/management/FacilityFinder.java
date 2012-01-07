package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import javax.inject.Inject;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityFinder.
 */
public abstract class FacilityFinder {

	/** The facility dao. */
	@Inject
	protected FacilityDAO facilityDAO;

	/**
	 * Execute.
	 *
	 * @return the collection<? extends facility>
	 */
	public abstract Collection<? extends Facility> execute();
}
