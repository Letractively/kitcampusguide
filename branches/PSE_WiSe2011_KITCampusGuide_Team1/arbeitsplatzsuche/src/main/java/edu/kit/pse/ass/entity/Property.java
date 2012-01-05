/**
 *  This Class represents a property of a facility.
 */
package edu.kit.pse.ass.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Sebastian
 * 
 */
@Entity(name = "t_propertiy")
public class Property {

	/**
	 * the name of the property
	 */
	@Id
	private String name;

	/**
	 * no 'value:boolean' needed
	 */

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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Property)) {
			return false;
		}
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
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
	 * returns a string representation of this property
	 */
	@Override
	public String toString() {
		return getName();
	}

}
