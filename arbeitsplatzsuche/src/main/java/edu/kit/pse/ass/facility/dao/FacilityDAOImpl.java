package edu.kit.pse.ass.facility.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.orm.jpa.JpaTemplate;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;

public class FacilityDAOImpl implements FacilityDAO {

	@Inject
	private JpaTemplate jpaTemplate;

	@Override
	public Facility getFacility(String facilityID) {
		return jpaTemplate.find(Facility.class, facilityID);
	}

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

	@Override
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void facilityFillWithDummies() {
		// TODO create dummy values
		Property prop1 = new Property("WLAN");
		Property prop2 = new Property("Steckdose");
		Facility facil1 = new Room();
		Facility facil2 = new Room();
		Facility facil3 = new Room();
		Facility facil4 = new Room();
		Facility place1 = new Workplace();
		Facility place2 = new Workplace();
		Facility place3 = new Workplace();
		Facility place4 = new Workplace();
		facil1.setName("ID###1");
		facil1.addProperty(prop1);
		facil1.addContainedFacilitiy(place1);
		facil1.addContainedFacilitiy(place2);
		facil1.addContainedFacilitiy(place3);

		facil2.setName("ID###2");
		facil2.addProperty(prop2);
		facil2.addContainedFacilitiy(place1);

		facil3.setName("ID###3");
		facil3.addProperty(prop1);
		facil3.addProperty(prop2);
		facil3.addContainedFacilitiy(place1);
		facil3.addContainedFacilitiy(place2);
		facil3.addContainedFacilitiy(place3);

		facil4.setName("ID###4");
		facil4.addProperty(prop1);
		facil4.addProperty(prop2);
		facil4.addContainedFacilitiy(place1);
		facil4.addContainedFacilitiy(place2);
		facil4.addContainedFacilitiy(place3);
		facil4.addContainedFacilitiy(place4);

		jpaTemplate.persist(facil1);
		jpaTemplate.persist(facil2);
		jpaTemplate.persist(facil3);
		jpaTemplate.persist(facil4);
	}

}
