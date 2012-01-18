package edu.kit.pse.ass.facility.dao;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * The Interface FacilityDAO.
 * 
 * @author Andreas Bosch
 */
public interface FacilityDAO {

	/**
	 * Returns facility with given ID.
	 * 
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 * @throws IllegalArgumentException
	 *             facilityID is null
	 */
	Facility getFacility(String facilityID) throws IllegalArgumentException;

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
	 */
	<T extends Facility> T getFacility(Class<T> type, String facilityID);

	/**
	 * Returns a collection of facilities that have the given properties.
	 * 
	 * @param properties
	 *            the properties that the facilities must have
	 * @return collection of facilities that have the given properties
	 */
	Collection<Facility> getFacilities(Collection<Property> properties);

	/**
	 * Returns a collection of all properties that an object of the given class can have.
	 * 
	 * @param facilityClass
	 *            the class whose properties shall be returned
	 * @return collection of all properties that an object of the given class can have
	 */
	Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass);

	/* Standard jpaTemplate methods - merge, remove, persist. */
	/**
	 * Merges the given facility.
	 * 
	 * @param facility
	 *            the facility which shall be merged
	 * @return the merged facility
	 */
	Facility merge(Facility facility);

	/**
	 * Removes the facility with the given ID.
	 * 
	 * @param facilityID
	 *            the ID of the facility to remove
	 */
	void remove(String facilityID);

	/**
	 * Persist the given facility.
	 * 
	 * @param facility
	 *            the facility to persist
	 */
	void persist(Facility facility);

}
