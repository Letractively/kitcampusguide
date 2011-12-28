package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import javax.inject.Inject;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

public abstract class FacilityFinder {

	@Inject
	protected FacilityDAO facilityDAO;

	public Collection<? extends Facility> execute() {
		return null;
	}
}
