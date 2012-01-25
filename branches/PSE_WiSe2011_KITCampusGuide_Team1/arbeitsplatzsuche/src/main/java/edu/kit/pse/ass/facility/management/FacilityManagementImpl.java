package edu.kit.pse.ass.facility.management;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityManagementImpl.
 */
public class FacilityManagementImpl implements FacilityManagement {

	/** The facility dao. */
	@Autowired
	private FacilityDAO facilityDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#getFacility(java .lang.String)
	 */
	@Override
	public Facility getFacility(String facilityID) throws IllegalArgumentException, FacilityNotFoundException {
		if (facilityID == null) {
			throw new IllegalArgumentException("facilityID is null.");
		}
		Facility facility = facilityDAO.getFacility(facilityID);

		if (facility == null) {
			throw new FacilityNotFoundException("The ID does not exist.");
		}

		return facility;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#findMatchingFacilities
	 * (edu.kit.pse.ass.facility.management.FacilityQuery)
	 */
	@Override
	public <T extends Facility> T getFacility(Class<T> type, String facilityID) throws FacilityNotFoundException {
		if (facilityID == null) {
			throw new IllegalArgumentException("facilityID is null.");
		}
		T facility = facilityDAO.getFacility(type, facilityID);

		if (facility == null) {
			throw new FacilityNotFoundException("The specified facility was not found.");
		}

		return facility;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement#findMatchingFacilities
	 * (edu.kit.pse.ass.facility.management.FacilityQuery)
	 */
	@Override
	public Collection<? extends Facility> findMatchingFacilities(FacilityQuery facilityQuery) {
		if (facilityQuery == null) {
			throw new IllegalArgumentException("query was null");
		}
		FacilityFinder finder = facilityQuery.createFinder();

		return finder.execute(facilityDAO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.management.FacilityManagement# getAvailablePropertiesOf(java.lang.Class)
	 */
	@Override
	public Collection<Property> getAvailablePropertiesOf(Class<? extends Facility> facilityClass) {
		if (facilityClass == null) {
			throw new IllegalArgumentException("class was null");
		}
		return facilityDAO.getAvailablePropertiesOf(facilityClass);
	}

}
