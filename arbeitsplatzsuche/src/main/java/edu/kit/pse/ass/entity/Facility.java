/**
 * this class represents a abstract facility
 */
package edu.kit.pse.ass.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Sebastian
 * 
 */
/**
 * @author Lennart
 * 
 */
@Entity(name = "t_facility")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Facility {

	/**
	 * unique ID of this facility
	 */
	@GeneratedValue(generator = "system-uuid")
	@Column(nullable = false)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	private String id;

	/**
	 * the name of this facility. e.g. SR -119 or HS37
	 */
	private String name;

	/**
	 * the parent facility of this facility. e.g. building is parent of room.
	 */
	private Facility parentFacility;

	/**
	 * the contained facilities of this facility. e.g. a room contains
	 * workplaces.
	 */
	private final Collection<Facility> containedFacilities;

	/**
	 * the properties of this facility. e.g. WLAN, Strom, PC
	 */
	private final Collection<Property> properties;

	/**
	 * Creates a new Facility
	 * 
	 * @param name
	 *            the name of the facility e.g. Bibliothek or SR -118
	 */
	public Facility() {
		containedFacilities = new ArrayList<Facility>();
		properties = new ArrayList<Property>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * WARNING! only used for testing! DO NOT under any circumstances use it in
	 * the real application!
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("id must not null or empty.");
		} else {
			this.id = id;
		}
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
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name must not null or empty.");
		} else {
			this.name = name;
		}
	}

	/**
	 * @return the parentFacility
	 */
	public Facility getParentFacility() {
		return parentFacility;
	}

	/**
	 * @param parentFacility
	 *            the parentFacility to set
	 */
	protected void setParentFacility(Facility parentFacility) {
		this.parentFacility = parentFacility;
	}

	/**
	 * @return the containedFacilities
	 */
	public Collection<Facility> getContainedFacilities() {
		return containedFacilities;
	}

	/**
	 * Adds a facility to this facility as a contained one.
	 * 
	 * @param containedFacility
	 *            the facility to add as contained facility
	 * @throws IllegalArgumentException
	 *             , when containedFacility is null.
	 */
	public void addContainedFacility(Facility containedFacility)
			throws IllegalArgumentException {

		if (containedFacility == null) {
			throw new IllegalArgumentException(
					"containedFacility must not be null.");
		}
		if (containedFacility.getParentFacility() != null) {
			throw new IllegalArgumentException(
					"the facility to add already have a parent.");
		}

		containedFacilities.add(containedFacility);
		containedFacility.setParentFacility(this);
	}

	/**
	 * Removes a facility of the collection of the contained facility.
	 * 
	 * @param removedFacility
	 *            the facility to remove
	 * @throws IllegalArgumentException
	 *             , when removedFacility is null or not contained in the
	 *             collection.
	 */
	public void removeContainedFacility(Facility removedFacility)
			throws IllegalArgumentException {
		if (containedFacilities.contains(removedFacility)) {
			containedFacilities.remove(removedFacility);
		} else {
			throw new IllegalArgumentException(
					"the facility to remove is not contained in this facility");
		}
	}

	/**
	 * @return the properties
	 */
	public Collection<Property> getProperties() {
		return properties;
	}

	/**
	 * Check whether a facility have a property or not.
	 * 
	 * @param property
	 *            the property to check
	 * @return true , when the facility have the specified property
	 */
	public boolean hasProperty(Property property) {
		if (properties.contains(property)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a property to the collection of properties of the facility.
	 * 
	 * @param property
	 *            the property to add.
	 * @throws IllegalArgumentException
	 *             , when property was already added or property is null.
	 */
	public void addProperty(Property property) throws IllegalArgumentException {
		if (properties.contains(property)) {
			throw new IllegalArgumentException(
					"property was added a long time ago ;)");
		}
		if (property == null) {
			throw new IllegalArgumentException("property must not null");
		}

		properties.add(property);
	}

	/**
	 * Removes a property of the collection of properties of the facility.
	 * 
	 * @param property
	 *            the property to remove
	 * @throws IllegalArgumentException
	 *             , when property is null or not contained in the collection.
	 */
	public void removeProperty(Property property)
			throws IllegalArgumentException {
		if (property == null) {
			throw new IllegalArgumentException("property must not null.");
		}
		if (properties.contains(property)) {
			properties.remove(property);
		} else {
			throw new IllegalArgumentException(
					"the property to remove is no property of this facility");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		if (this.id != null) {
			hash = this.id.hashCode();
		}
		if (this.name != null) {
			hash += this.name.hashCode();
		}
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof Facility) {
			Facility f = (Facility) obj;
			boolean equal = true;
			equal = (f.id == this.id) || (f.id != null && f.id.equals(this.id));
			if (equal) {
				equal = (f.name == this.name)
						|| (f.name != null && f.name.equals(this.name));
			}
			return equal;
		}
		return false;
	}
}
