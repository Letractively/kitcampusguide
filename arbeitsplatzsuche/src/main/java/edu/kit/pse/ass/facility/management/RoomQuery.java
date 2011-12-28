package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

public class RoomQuery extends FacilityQuery {

	protected int minimumWorkplaces;

	public RoomQuery(Collection<Property> properties, String searchText,
			int minimumWorkplaces) {
		super(properties, searchText);
		this.minimumWorkplaces = minimumWorkplaces;
	}

	public int getMinimumWorkplaces() {
		return this.minimumWorkplaces;
	}

}
