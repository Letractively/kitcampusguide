package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class RoomQuery.
 */
public class RoomQuery extends FacilityQuery {

	/** The minimum workplaces. */
	protected int minimumWorkplaces;

	/**
	 * Instantiates a new room query.
	 *
	 * @param properties the properties
	 * @param searchText the search text
	 * @param minimumWorkplaces the minimum workplaces
	 */
	public RoomQuery(Collection<Property> properties, String searchText,
			int minimumWorkplaces) {
		super(properties, searchText);
		this.minimumWorkplaces = minimumWorkplaces;
	}

	/**
	 * Gets the minimum workplaces.
	 *
	 * @return the minimum workplaces
	 */
	public int getMinimumWorkplaces() {
		return this.minimumWorkplaces;
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.facility.management.FacilityQuery#createFinder()
	 */
	@Override
	protected FacilityFinder createFinder() {
		return new RoomFinder(this);
	}

}
