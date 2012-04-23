package edu.kit.pse.ass.facility.dao;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * The Interface FacilityDAO, contains methods for facility related database access .
 * 
 * @author Andreas Bosch
 */
public interface FacilityDAO {

	/**
	 * Returns the facility with the given ID.
	 * 
	 * @param facilityID
	 *            - the ID of the facility
	 * @return the facility with given ID
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityID is null or empty
	 */
	Facility getFacility(String facilityID) throws IllegalArgumentException;

	/**
	 * Returns the facility with given ID as the specified subclass type.
	 * 
	 * @param <T>
	 *            - the generic type
	 * @param type
	 *            - the specific subclass of Facility to load
	 * @param facilityID
	 *            - the ID of the facility
	 * @return the facility with given ID as the subclass type
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityID is null or empty or type is null
	 */
	<T extends Facility> T getFacility(Class<T> type, String facilityID);

	/**
	 * Returns a collection of facilities that have the given properties.
	 * 
	 * @param properties
	 *            - the properties that the facilities must have
	 * @return a collection of facilities that have the given properties
	 * 
	 * @throws IllegalArgumentException
	 *             if properties is null
	 */
	Collection<Facility> getFacilities(Collection<Property> properties);

	/**
	 * Returns a collection of all properties that objects of the given class have.
	 * 
	 * @param facilityClass
	 *            - the class whose properties shall be returned
	 * 
	 * @return a collection of all properties that objects of the given class have
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityClass is null
	 */
	Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass);

	/**
	 * Merges the given facility.
	 * 
	 * @param facility
	 *            - the facility which shall be merged
	 * @return the merged facility
	 * 
	 * @throws IllegalArgumentException
	 *             if facility is null
	 */
	Facility merge(Facility facility);

	/**
	 * Removes the facility with the given ID.
	 * 
	 * @param facilityID
	 *            - the ID of the facility to remove
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityID is null or empty
	 */
	void remove(String facilityID);

	/**
	 * Persist the given facility.
	 * 
	 * @param facility
	 *            - the facility to persist
	 * 
	 * @throws IllegalArgumentException
	 *             if facility is null
	 */
	void persist(Facility facility);

	/**
	 * @param <T>
	 *            - the facilityClass has to be a facility subclass.
	 * @param facilityClass
	 *            - the class to search for.
	 * @return all facilities of the specified class, e.g. Room
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityClass is null
	 */
	<T extends Facility> Collection<T> getAllFacilities(Class<T> facilityClass);

}
