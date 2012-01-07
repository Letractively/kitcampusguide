/**
 *  This Class represents a property of a facility.
 */
package edu.kit.pse.ass.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

// TODO: Auto-generated Javadoc
/**
 * The Class Property.
 * 
 * @author Sebastian
 */
@Entity(name = "t_property")
@Embeddable
public class Property {

	/** the name of the property. */
	@Column(nullable = false)
	@Id
	private String name;

	/*
	 * no 'value:boolean' needed.
	 */

	/**
	 * Creates a new property with the specified values.
	 * 
	 * @param name
	 *            the name of the property
	 * @throws IllegalArgumentException
	 *             when name is null or empty.
	 */
	public Property() {
	}

	/**
	 * Creates a new property with the specified values.
	 * 
	 * @param name
	 *            the name of the property
	 * @throws IllegalArgumentException
	 *             when name is null or empty.
	 */
	public Property(String name) throws IllegalArgumentException {
		setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Property)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Property other = (Property) obj;
		if ((name == null) && (other.name != null)) {
			return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 * @throws IllegalArgumentException
	 *             when name is null or empty
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name must not null or empty");
		} else {
			this.name = name;
		}
	}

	/**
	 * returns a string representation of this property.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return getName();
	}

}
