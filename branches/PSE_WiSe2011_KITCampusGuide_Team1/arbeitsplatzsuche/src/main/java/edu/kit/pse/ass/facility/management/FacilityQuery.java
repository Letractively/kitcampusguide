package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

public abstract class FacilityQuery {

	private final Collection<Property> properties;
	private final String searchText;

	protected FacilityQuery(Collection<Property> properties, String searchText) {
		this.properties = properties;
		this.searchText = searchText;
	}

	protected FacilityFinder createFinder() {
		return null;
	}

	public Collection<Property> getProperties() {
		return this.properties;
	}

	public String getSearchText() {
		return this.searchText;
	}
}
