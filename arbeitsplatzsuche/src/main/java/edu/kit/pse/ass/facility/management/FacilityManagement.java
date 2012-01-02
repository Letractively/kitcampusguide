/**
 * 
 */
package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * @author Andreas Bosch
 * 
 */
public interface FacilityManagement {

	/**
	 * returns facility with given ID
	 * 
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 */
	public Facility getFacility(String facilityID);

	/**
	 * returns a collection of facilities matching the criteria of the given
	 * facilityQuery
	 * 
	 * @param facilityQuery
	 *            the facility query
	 * @return a collection of matching facilities
	 */
	public Collection<? extends Facility> findMatchingFacilities(
			FacilityQuery facilityQuery);

	/**
	 * returns a collection of properties that an object of the given class can
	 * have
	 * 
	 * @param facilityClass
	 *            the class
	 * @return collection of properties that an object of the given class can
	 *         have
	 */
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass);
}
