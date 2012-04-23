package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * The Interface FacilityManagement encapsulates the facilityDAO methods.
 * 
 * @author Andreas Bosch
 */
public interface FacilityManagement {

	/**
	 * Returns the facility with the given ID.
	 * 
	 * @param facilityID
	 *            - the ID of the facility
	 * @return the facility object with the given ID
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityID is null or empty
	 * @throws FacilityNotFoundException
	 *             if no facility with the given ID exists
	 */
	Facility getFacility(String facilityID) throws IllegalArgumentException, FacilityNotFoundException;

	/**
	 * Returns the facility with given ID as the specified subclass type.
	 * 
	 * @param <T>
	 *            - the generic type
	 * @param type
	 *            - the specific subclass of Facility to load
	 * @param facilityID
	 *            - the ID of the facility
	 * @return facility with given ID as the subclass type
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityID is null or empty
	 * 
	 * @throws FacilityNotFoundException
	 *             if no facility with the given ID exists
	 */
	<T extends Facility> T getFacility(Class<T> type, String facilityID) throws FacilityNotFoundException;

	/**
	 * Returns a collection of facilities matching the criteria of the given facilityQuery.
	 * 
	 * @param facilityQuery
	 *            - the facility query
	 * @return a collection of matching facilities
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityQuery is null
	 */
	Collection<FacilityResult> findMatchingFacilities(FacilityQuery facilityQuery);

	/**
	 * Returns a collection of properties that objects of the given class have.
	 * 
	 * @param facilityClass
	 *            - the class whose properties shall be returned
	 * @return collection of properties that objects of the given class have
	 * 
	 * @throws IllegalArgumentException
	 *             if facilityClass is null
	 */
	Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass);
}
