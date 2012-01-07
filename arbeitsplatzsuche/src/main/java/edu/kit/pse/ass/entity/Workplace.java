/**
 * This class represents a workplace.
 */
package edu.kit.pse.ass.entity;

import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class Workplace.
 * 
 * @author Sebastian
 */
@Entity(name = "t_workplace")
public class Workplace extends Facility {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.entity.Facility#setParentFacility(edu.kit.pse.ass.entity
	 * .Facility)
	 */
	@Override
	public void setParentFacility(Facility parentFacility)
			throws IllegalArgumentException {
		if (parentFacility instanceof Room) {
			super.setParentFacility(parentFacility);
		} else {
			throw new IllegalArgumentException("parentFacilty must be a room");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.pse.ass.entity.Facility#addContainedFacilitiy(edu.kit.pse.ass
	 * .entity.Facility)
	 */
	@Override
	public void addContainedFacility(Facility containedFacility)
			throws IllegalArgumentException {
		throw new IllegalArgumentException(
				"a workplace can not contain a other facility");
	}

}
