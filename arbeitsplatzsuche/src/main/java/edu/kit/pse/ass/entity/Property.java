package edu.kit.pse.ass.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The class Property.
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

	/**
	 * Creates a new property
	 */
	public Property() {
	}

	/**
	 * Creates a new property with the specified values.
	 * 
	 * @param name
	 *            the name of the property
	 * @throws IllegalArgumentException
	 *             name is null or empty.
	 */
	public Property(String name) throws IllegalArgumentException {
		setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 1 + ((this.getName() == null) ? 0 : this.name.hashCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			Property p = (Property) obj;
			return name != null && name.equals(p.getName());
		}
		return false;
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
	 *             name is null or empty
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name must not be null or empty");
		} else {
			this.name = name;
		}
	}

	/**
	 * Returns a string representation of this property.
	 * 
	 * @return the name
	 */
	@Override
	public String toString() {
		return getName();
	}

}
