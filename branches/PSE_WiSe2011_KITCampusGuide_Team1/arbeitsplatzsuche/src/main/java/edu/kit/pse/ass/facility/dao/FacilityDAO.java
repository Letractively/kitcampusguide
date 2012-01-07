/**
 * 
 */
package edu.kit.pse.ass.facility.dao;

import java.util.Collection;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

// TODO: Auto-generated Javadoc
/**
 * The Interface FacilityDAO.
 * 
 * @author Andreas Bosch
 */
public interface FacilityDAO {

	/**
	 * returns facility with given ID.
	 * 
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 * @throws IllegalArgumentException
	 *             facilityID is null
	 */
	public Facility getFacility(String facilityID)
			throws IllegalArgumentException;

	/**
	 * returns facility with given ID as a specific subclass type.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the specific subclass of Facility to load
	 * @param facilityID
	 *            the ID of the facility
	 * @return facility with given ID
	 */
	public <T extends Facility> T getFacility(Class<T> type, String facilityID);

	/**
	 * returns a collection of facilities that have the given properties.
	 * 
	 * @param properties
	 *            the properties that the facilities must have
	 * @return collection of facilities that have the given properties
	 */
	public Collection<Facility> getFacilities(Collection<Property> properties);

	/**
	 * returns a collection of properties that an object of the given class can
	 * have.
	 * 
	 * @param facilityClass
	 *            the class
	 * @return collection of properties that an object of the given class can
	 *         have
	 */
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass);

	/**
	 * Standard jpaTemplate methods - merge, remove, persist.
	 * 
	 * @param facility
	 *            the facility
	 * @return the facility
	 */
	public Facility merge(Facility facility);

	/**
	 * Removes the.
	 * 
	 * @param facilityID
	 *            the facility id
	 */
	public void remove(String facilityID);

	/**
	 * Persist.
	 * 
	 * @param facility
	 *            the facility
	 */
	public void persist(Facility facility);

}
