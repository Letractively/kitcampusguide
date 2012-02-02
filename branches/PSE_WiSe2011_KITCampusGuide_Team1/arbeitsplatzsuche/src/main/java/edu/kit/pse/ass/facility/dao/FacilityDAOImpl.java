package edu.kit.pse.ass.facility.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

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
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#getFacility(java.lang.String)
	 */
	@Override
	public Facility getFacility(String facilityID) throws IllegalArgumentException {
		if (facilityID == null || facilityID.isEmpty()) {
			throw new IllegalArgumentException("facilityID must not be null");
		}

		return jpaTemplate.find(Facility.class, facilityID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#getFacility(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T extends Facility> T getFacility(Class<T> type, String facilityID) throws IllegalArgumentException {
		if (facilityID == null || facilityID.isEmpty()) {
			throw new IllegalArgumentException("facilityID is null");
		} else if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		return jpaTemplate.find(type, facilityID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#getFacilities(java.util.Collection )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Facility> getFacilities(Collection<Property> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Parameter properties is null.");
		}
		String query = "from t_facility f where 1=1 ";

		HashMap<String, Property> props = new HashMap<String, Property>();
		Iterator<Property> propIter = properties.iterator();
		for (int i = 0; i < properties.size(); i++) {
			query += " and :prop" + i + " in elements(f.properties) ";
			props.put("prop" + i, propIter.next());
		}

		return jpaTemplate.findByNamedParams(query, props);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#getAllFacilities(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Facility> Collection<T> getAllFacilities(Class<T> facilityClass) {
		if (facilityClass == null) {
			throw new IllegalArgumentException("facilityClass is null");
		}
		Collection<T> result = jpaTemplate.find("from t_facility f where f.class = " + facilityClass.getName());
		if (result == null) {
			return new ArrayList<T>();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#getAvailablePropertiesOf(java .lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass) {
		if (facilityClass == null) {
			throw new IllegalArgumentException("Parameter facilityClass musst not be null.");
		}
		Collection<Facility> facilities;
		HashSet<Property> properties = new HashSet<Property>();
		try {
			facilities = jpaTemplate.find("from t_facility fac where fac.class = " + facilityClass.getName());
		} catch (DataRetrievalFailureException e) {
			// no facilities of this type found
			return properties;
		}
		if (facilities != null) {
			for (Facility facility : facilities) {
				Collection<Property> facProperties = facility.getProperties();
				if (facProperties != null) {
					for (Property prop : facProperties) {
						properties.add(prop);
					}
				}
			}
		}
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#merge(edu.kit.pse.ass.entity .Facility)
	 */
	@Override
	@Transactional
	public Facility merge(Facility facility) {
		if (facility == null) {
			throw new IllegalArgumentException("The facility to merge musst not be null.");
		}
		return jpaTemplate.merge(facility);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#remove(edu.kit.pse.ass.entity .Facility)
	 */
	@Override
	@Transactional
	public void remove(String facilityID) {
		if (facilityID == null || facilityID.isEmpty()) {
			throw new IllegalArgumentException("The facilityID to remove musst not be null or empty.");
		}
		Facility facility = getFacility(facilityID);
		if (facility != null) {
			jpaTemplate.remove(facility);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#persist(edu.kit.pse.ass.entity .Facility)
	 */
	@Override
	@Transactional
	public void persist(Facility facility) {
		if (facility == null) {
			throw new IllegalArgumentException("The facility to persist musst not be null.");
		}
		jpaTemplate.persist(facility);

	}

}
