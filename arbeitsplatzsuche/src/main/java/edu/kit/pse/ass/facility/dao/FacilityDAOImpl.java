package edu.kit.pse.ass.facility.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityDAOImpl implements the FacilityDAO.
 */
public class FacilityDAOImpl implements FacilityDAO {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#getFacility(java.lang.String)
	 */
	@Override
	public Facility getFacility(String facilityID)
			throws IllegalArgumentException {
		if (facilityID == null || facilityID.isEmpty())
			throw new IllegalArgumentException("facilityID must not be null");

		return jpaTemplate.find(Facility.class, facilityID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#getFacility(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	public <T extends Facility> T getFacility(Class<T> type, String facilityID) {
		return jpaTemplate.find(type, facilityID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#getFacilities(java.util.Collection
	 * )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Facility> getFacilities(Collection<Property> properties) {
		String query = "from t_facility f where 1=1 ";

		HashMap<String, Property> props = new HashMap<String, Property>();
		Iterator<Property> propiter = properties.iterator();
		for (int i = 0; i < properties.size(); i++) {
			query += " and :prop" + i + " in elements(f.properties) ";
			props.put("prop" + i, propiter.next());
		}

		return jpaTemplate.findByNamedParams(query, props);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#getAvailablePropertiesOf(java
	 * .lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass) {

		try {
			Collection<Property> properties = jpaTemplate
					.find("from t_property as prop "
							+ " where exists ( from t_facility as fac where fac.class = ? and prop in elements(fac.properties) )",
							facilityClass);
			return properties;
		} catch (DataRetrievalFailureException e) {
			return new ArrayList<Property>();
		}
	}

	/**
	 * Gets the available properties of xyz.
	 * 
	 * @param facilityClass
	 *            the facility class
	 * @return the available properties of xyz
	 */
	public Collection<Property> getAvailablePropertiesOfXYZ(
			Class<? extends Facility> facilityClass) {
		Collection<Property> result = new ArrayList<Property>();
		Collection<Facility> facilities = jpaTemplate.find("from t_facility");
		Iterator<Facility> facilityIterator = facilities.iterator();

		// go through all matching facilities
		for (int i = 0; i < facilities.size(); i++) {
			Facility tmp = facilityIterator.next();
			if (tmp.getClass() == facilityClass) {
				Collection<Property> propertiesOfFacility = tmp.getProperties();
				Iterator<Property> propertyIterator = propertiesOfFacility
						.iterator();

				// add all new properties of a matching facility
				for (int j = 0; j < propertiesOfFacility.size(); j++) {
					Property tmpProperty = propertyIterator.next();
					if (!result.contains(tmpProperty))
						result.add(tmpProperty);
				}

			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#merge(edu.kit.pse.ass.entity
	 * .Facility)
	 */
	@Override
	@Transactional
	public Facility merge(Facility facility) {
		return jpaTemplate.merge(facility);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#remove(edu.kit.pse.ass.entity
	 * .Facility)
	 */
	@Override
	@Transactional
	public void remove(String facilityID) {
		Facility facility = getFacility(facilityID);
		if (facility != null)
			jpaTemplate.remove(facility);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#persist(edu.kit.pse.ass.entity
	 * .Facility)
	 */
	@Override
	@Transactional
	public void persist(Facility facility) {
		jpaTemplate.persist(facility);

	}

}
