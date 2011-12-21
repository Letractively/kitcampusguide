package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;

public abstract class FacilityFinder {

	protected FacilityFinder(FacilityQuery facilityQuery) {

	}

	public Collection<Facility> execute() {
		return null;
	}
}
