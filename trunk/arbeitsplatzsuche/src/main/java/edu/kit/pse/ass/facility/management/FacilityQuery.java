package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Property;

/**
 * The abstract class FacilityQuery. Used to create queries to alleviate the search for facilities via GUI
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
	 *            - the wanted properties
	 * @param searchText
	 *            - the entered search text
	 * @param requiredChildCount
	 *            - the count of necessary children (e.g. to get 2 workplaces in a room)
	 */
	protected FacilityQuery(Collection<Property> properties, String searchText, int requiredChildCount) {
		this.properties = properties;
		this.searchText = searchText;
		this.requiredChildCount = requiredChildCount;
	}

	/**
	 * Create the finder, must than be executed with the facilityDAO
	 * 
	 * @return the facility finder
	 */
	protected FacilityFinder createFinder() {
		return null;
	}

	/**
	 * Get the specified properties.
	 * 
	 * @return the properties
	 */
	public Collection<Property> getProperties() {
		return this.properties;
	}

	/**
	 * Get the required child count.
	 * 
	 * @return the requiredChildCount
	 */
	public int getRequiredChildCount() {
		return this.requiredChildCount;
	}

	/**
	 * Get the added search text.
	 * 
	 * @return the search text
	 */
	public String getSearchText() {
		return this.searchText;
	}
}
