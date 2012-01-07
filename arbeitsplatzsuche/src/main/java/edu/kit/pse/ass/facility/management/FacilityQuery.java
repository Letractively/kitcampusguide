package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityQuery.
 */
public abstract class FacilityQuery {

	/** The properties. */
	private final Collection<Property> properties;
	
	/** The search text. */
	private final String searchText;

	/**
	 * Instantiates a new facility query.
	 *
	 * @param properties the properties
	 * @param searchText the search text
	 */
	protected FacilityQuery(Collection<Property> properties, String searchText) {
		this.properties = properties;
		this.searchText = searchText;
	}

	/**
	 * Creates the finder.
	 *
	 * @return the facility finder
	 */
	protected FacilityFinder createFinder() {
		return null;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Collection<Property> getProperties() {
		return this.properties;
	}

	/**
	 * Gets the search text.
	 *
	 * @return the search text
	 */
	public String getSearchText() {
		return this.searchText;
	}
}
