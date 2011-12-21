package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

public abstract class FacilityQuery {

	protected FacilityQuery(Collection<Property> properties, String searchText) {
	}

	protected FacilityFinder createFinder() {
		return null;
	}
}
