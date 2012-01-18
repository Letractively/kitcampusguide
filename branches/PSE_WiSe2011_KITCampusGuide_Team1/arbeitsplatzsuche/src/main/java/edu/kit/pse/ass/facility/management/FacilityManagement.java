package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * The Interface FacilityManagement.
 * 
 * @author Andreas Bosch
 */
public interface FacilityManagement {

	/**
	 * Gets the facility.
	 * 
	 * @param facilityID
	 *            the ID of the facility
	 * @return the facility object with the given ID
	 * @throws IllegalArgumentException
	 *             facilityID is null or the string is empty
	 * @throws FacilityNotFoundException
	 *             ID does not exist
	 */
	Facility getFacility(String facilityID) throws IllegalArgumentException, FacilityNotFoundException;

	/**
	 * Returns facility with given ID as a specific subclass type.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the specific subclass of Facility to load
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 * @throws FacilityNotFoundException
	 *             there is no facility with the given ID
	 */
	<T extends Facility> T getFacility(Class<T> type, String facilityID) throws FacilityNotFoundException;

	/**
	 * Returns a collection of facilities matching the criteria of the given facilityQuery.
	 * 
	 * @param facilityQuery
	 *            the facility query
	 * @return a collection of matching facilities
	 */
	Collection<? extends Facility> findMatchingFacilities(FacilityQuery facilityQuery);

	/**
	 * Returns a collection of properties that an object of the given class can have.
	 * 
	 * @param facilityClass
	 *            the class
	 * @return collection of properties that an object of the given class can have
	 */
	Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass);
}
