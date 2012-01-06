package edu.kit.pse.ass.facility.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;

/**
 * The Class FacilityDAOImpl implements the FacilityDAO
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
		if (facilityID == null)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#facilityFillWithDummies()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void facilityFillWithDummies() {
		// TODO create dummy values
		Property prop1 = new Property("WLAN");
		Property prop2 = new Property("Steckdose");
		Facility build1 = new Building();
		Facility facil1 = new Room();
		Facility facil2 = new Room();
		Facility facil3 = new Room();
		Facility facil4 = new Room();
		Facility place1 = new Workplace();
		place1.setId("place1");
		Facility place2 = new Workplace();
		place2.setId("place2");
		Facility place3 = new Workplace();
		place3.setId("place3");
		Facility place4 = new Workplace();
		place4.setId("place4");

		facil1.setId("ID###1");
		facil1.addProperty(prop1);
		facil1.addContainedFacility(place1);
		facil1.addContainedFacility(place2);
		facil1.addContainedFacility(place3);

		facil2.setId("ID###2");
		facil2.addProperty(prop2);
		facil2.addContainedFacility(place1);

		facil3.setId("ID###3");
		facil3.addProperty(prop1);
		facil3.addProperty(prop2);
		facil3.addContainedFacility(place1);
		facil3.addContainedFacility(place2);
		facil3.addContainedFacility(place3);

		facil4.setId("ID###4");
		facil4.addProperty(prop1);
		facil4.addProperty(prop2);
		facil4.addContainedFacility(place1);
		facil4.addContainedFacility(place2);
		facil4.addContainedFacility(place3);
		facil4.addContainedFacility(place4);

		build1.addContainedFacility(facil1);
		build1.addContainedFacility(facil2);
		build1.addContainedFacility(facil3);
		build1.addContainedFacility(facil4);
		build1.addProperty(prop1);

		jpaTemplate.merge(facil1);
		jpaTemplate.merge(facil2);
		jpaTemplate.merge(facil3);
		jpaTemplate.merge(facil4);
		jpaTemplate.merge(build1);
	}
}
