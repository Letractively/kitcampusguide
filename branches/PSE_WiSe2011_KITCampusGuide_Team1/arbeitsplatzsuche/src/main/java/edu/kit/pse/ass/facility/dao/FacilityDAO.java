/**
 * 
 */
package edu.kit.pse.ass.facility.dao;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

/**
 * @author Andreas Bosch
 * 
 */
public interface FacilityDAO {

	/**
	 * returns facility with given ID
	 * 
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 */
	public Facility getFacility(String facilityID);

	/**
	 * returns a collection of facilities that have the given properties
	 * 
	 * @param properties
	 *            the properties that the facilities must have
	 * @return collection of facilities that have the given properties
	 */
	public Collection<Facility> getFacilities(Collection<Property> properties);

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
