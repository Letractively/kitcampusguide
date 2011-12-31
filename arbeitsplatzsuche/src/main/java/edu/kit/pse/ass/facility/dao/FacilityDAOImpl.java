package edu.kit.pse.ass.facility.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.orm.jpa.JpaTemplate;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;

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
		// TODO Auto-generated method stub

	}

}
