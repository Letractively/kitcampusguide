package edu.kit.pse.ass.facility.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.orm.jpa.JpaTemplate;

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
	@Inject
	private JpaTemplate jpaTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.facility.dao.FacilityDAO#getFacility(java.lang.String)
	 */
	@Override
	public Facility getFacility(String facilityID) {
		return jpaTemplate.find(Facility.class, facilityID);
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
	@Override
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.dao.FacilityDAO#facilityFillWithDummies()
	 */
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
		place1.setName("place1");
		Facility place2 = new Workplace();
		place2.setName("place2");
		Facility place3 = new Workplace();
		place3.setName("place3");
		Facility place4 = new Workplace();
		place4.setName("place4");
		facil1.setName("ID###1");
		facil1.addProperty(prop1);
		facil1.addContainedFacility(place1);
		facil1.addContainedFacility(place2);
		facil1.addContainedFacility(place3);

		facil2.setName("ID###2");
		facil2.addProperty(prop2);
		facil2.addContainedFacility(place1);

		facil3.setName("ID###3");
		facil3.addProperty(prop1);
		facil3.addProperty(prop2);
		facil3.addContainedFacility(place1);
		facil3.addContainedFacility(place2);
		facil3.addContainedFacility(place3);

		facil4.setName("ID###4");
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
		jpaTemplate.persist(facil1);
		jpaTemplate.persist(facil2);
		jpaTemplate.persist(facil3);
		jpaTemplate.persist(facil4);
		jpaTemplate.persist(build1);
	}
}
