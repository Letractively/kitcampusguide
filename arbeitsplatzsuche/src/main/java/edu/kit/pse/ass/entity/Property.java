/**
 *  This Class represents a property of a facility.
 */
package edu.kit.pse.ass.entity;

/**
 * @author Sebastian
 * 
 */
public class Property {

	/**
	 * the name of the property
	 */
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
	 *             , when name is null.
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
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("name must not null");
		} else {
			this.name = name;
		}
	}

}