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

	/** The required child count. */
	private final int requiredChildCount;

	/**
	 * Instantiates a new facility query.
	 * 
	 * @param properties
	 *            the properties
	 * @param searchText
	 *            the search text
	 * @param requiredChildCount
	 *            the count of necessary childern
	 */
	protected FacilityQuery(Collection<Property> properties, String searchText, int requiredChildCount) {
		this.properties = properties;
		this.searchText = searchText;
		this.requiredChildCount = requiredChildCount;
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
	 * Gets the required child count.
	 * 
	 * @return the requiredChildCount
	 */
	public int getRequiredChildCount() {
		return this.requiredChildCount;
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
