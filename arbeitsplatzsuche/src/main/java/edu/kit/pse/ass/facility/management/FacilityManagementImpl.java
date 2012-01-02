package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import javax.inject.Inject;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

public class FacilityManagementImpl implements FacilityManagement {

	@Inject
	FacilityDAO facilityDAO;

	@Override
	public Facility getFacility(String facilityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Facility> findMatchingFacilities(
			FacilityQuery facilityQuery) {
		FacilityFinder finder = facilityQuery.createFinder();
		if (finder == null) {
			return null;
		}
		return finder.execute();
	}

	@Override
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
