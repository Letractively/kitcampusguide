
package edu.kit.pse.ass.entity;

import javax.persistence.Entity;

/**
 * This class represents a building.
 * 
 * @author Sebastian
 */
@Entity(name = "t_building")
public class Building extends Facility {

	/**
	 * the number of the building e.g. 40.31, Mensa, ...
	 */
	private String number;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.entity.Facility#setParentFacility(edu.kit.pse.ass.entity .Facility)
	 */
	@Override
	public void setParentFacility(Facility parentFacility) throws IllegalArgumentException {
		if (parentFacility != null) {
			throw new IllegalArgumentException("a building can not have a parent facility");
		} else {
			super.setParentFacility(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.entity.Facility#addContainedFacilitiy(edu.kit.pse.ass .entity.Facility)
	 * restrict children. Must be instance of room.
	 */
	@Override
	public void addContainedFacility(Facility containedFacility) throws IllegalArgumentException {
		if (containedFacility instanceof Room) {
			super.addContainedFacility(containedFacility);
		} else {
			throw new IllegalArgumentException("a building can only contain rooms.");
		}
	}

	/**
	 * Gets the number.
	 * 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 * 
	 * @param number
	 *            the number to set
	 * @throws IllegalArgumentException
	 *             number is empty or null.
	 */
	public void setNumber(String number) throws IllegalArgumentException {
		if ((number == null) || number.isEmpty()) {
			throw new IllegalArgumentException("number must not be null and not empty");
		} else {
			this.number = number;
		}
	}

}
