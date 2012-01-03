package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import javax.inject.Inject;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

public class FacilityManagementImpl implements FacilityManagement {

	@Inject
	FacilityDAO facilityDAO;

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#getFacility(java.lang.String)
	 */
	@Override
	public Facility getFacility(String facilityID)
			throws IllegalArgumentException {
		Facility facility = facilityDAO.getFacility(facilityID);

		if (facility == null) {
			throw new IllegalArgumentException("The ID does not exist.");
		}

		return facility;
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#findMatchingFacilities(edu.kit.pse.ass.facility.management.FacilityQuery)
	 */
	@Override
	public Collection<? extends Facility> findMatchingFacilities(
			FacilityQuery facilityQuery) {
		FacilityFinder finder = facilityQuery.createFinder();
		if (finder == null) {
			return null;
		}
		return finder.execute();
	}

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#getAvailablePropertiesOf(java.lang.Class)
	 */
	@Override
	public Collection<Property> getAvailablePropertiesOf(
			Class<? extends Facility> facilityClass) {
		return facilityDAO.getAvailablePropertiesOf(facilityClass);
	}

}
