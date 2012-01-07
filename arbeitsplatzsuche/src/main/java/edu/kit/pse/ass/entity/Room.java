/**
 * this class represents a room.
 */
package edu.kit.pse.ass.entity;

import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class Room.
 * 
 * @author Sebastian
 */
@Entity(name = "t_room")
public class Room extends Facility {

	/** the description of the room. */
	private String description;

	/**
	 * the level of the room. Can be negative.
	 */
	private int level;

	/** the number of the room. */
	private String number;

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
		if (parentFacility instanceof Building) {
			super.setParentFacility(parentFacility);
		} else {
			throw new IllegalArgumentException(
					"a room can only have a building as parent");
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
		if (containedFacility instanceof Workplace) {
			super.addContainedFacility(containedFacility);
		} else {
			throw new IllegalArgumentException(
					"a room can only contain workplaces");
		}
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the description to set
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void setDescription(String description)
			throws IllegalArgumentException {
		if (description == null) {
			throw new IllegalArgumentException("description must not be null");
		} else {
			this.description = description;
		}
	}

	/**
	 * Gets the level.
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * 
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
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
	 *             the illegal argument exception
	 */
	public void setNumber(String number) throws IllegalArgumentException {
		if (number == null) {
			throw new IllegalArgumentException("number must not be null");
		} else {
			this.number = number;
		}
	}

}
