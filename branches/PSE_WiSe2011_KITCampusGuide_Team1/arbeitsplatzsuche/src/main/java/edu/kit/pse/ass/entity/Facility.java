/**
 * this class represents a abstract facility
 */
package edu.kit.pse.ass.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Class Facility.
 * 
 * @author Sebastian, Lennart
 */
@Entity(name = "t_facility")
@Inheritance(strategy = InheritanceType.JOINED)
public class Facility {

	/** unique ID of this facility. */
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
	@ManyToOne(targetEntity = Facility.class)
	private Facility parentFacility;

	/**
	 * the contained facilities of this facility. e.g. a room contains workplaces.
	 */
	@OneToMany(targetEntity = Facility.class, mappedBy = "parentFacility", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Collection<Facility> containedFacilities;

	/**
	 * the properties of this facility. e.g. WLAN, Strom, PC
	 */
	@ManyToMany(targetEntity = Property.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "T_FACILITY_T_PROPERTY", joinColumns = { @JoinColumn(name = "PROPERTY_NAME") },
			inverseJoinColumns = { @JoinColumn(name = "FACILITY_ID") })
	private Collection<Property> properties;

	/**
	 * Creates a new Facility.
	 * 
	 */
	public Facility() {
		containedFacilities = new ArrayList<Facility>();
		properties = new ArrayList<Property>();
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 * @deprecated WARNING! only used for testing! DO NOT under any circumstances use it in the real application!
	 */
	@Deprecated
	public void setId(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("id must not null or empty.");
		} else {
			this.id = id;
		}
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
	 *             the illegal argument exception
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name must not null or empty.");
		} else {
			this.name = name;
		}
	}

	/**
	 * Gets the parent facility.
	 * 
	 * @return the parentFacility
	 */
	public Facility getParentFacility() {
		return parentFacility;
	}

	/**
	 * Sets the parent facility.
	 * 
	 * @param parentFacility
	 *            the parentFacility to set
	 */
	protected void setParentFacility(Facility parentFacility) {
		this.parentFacility = parentFacility;
	}

	/**
	 * Gets the contained facilities.
	 * 
	 * @return the containedFacilities
	 */
	public Collection<Facility> getContainedFacilities() {
		return containedFacilities;
	}

	/**
	 * Removes all current contained facilities and adds the specified facilities.
	 * 
	 * @param facilities
	 *            the facilities to set
	 * @throws IllegalArgumentException
	 *             parameter is null.
	 */
	public void addContainedFacilities(Collection<Facility> facilities) throws IllegalArgumentException {
		if (facilities == null) {
			throw new IllegalArgumentException("facilities is null");
		}
		for (Facility facility : facilities) {
			if (facility == null) {
				throw new IllegalArgumentException("One of the facilities you want to add is null");
			}
			if (facility.getParentFacility() != null) {
				throw new IllegalArgumentException("One of the facilities you want to add already has a parent.");
			}
		}
		//clear the parent entry of the actual children
		for (Facility facility : containedFacilities) {
				facility.setParentFacility(null);
		}
		
		//set the new children.
		containedFacilities = facilities;
	}

	/**
	 * Adds a facility to this facility as a contained one.
	 * 
	 * @param containedFacility
	 *            the facility to add as contained facility
	 * @throws IllegalArgumentException
	 *             , when containedFacility is null.
	 */
	public void addContainedFacility(Facility containedFacility) throws IllegalArgumentException {

		if (containedFacility == null) {
			throw new IllegalArgumentException("containedFacility must not be null.");
		}
		if (containedFacility.getParentFacility() != null) {
			throw new IllegalArgumentException("the facility to add already have a parent.");
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
	 *             , when removedFacility is null or not contained in the collection.
	 */
	public void removeContainedFacility(Facility removedFacility) throws IllegalArgumentException {
		if (containedFacilities.contains(removedFacility)) {
			try {
				removedFacility.setParentFacility(null);
				containedFacilities.remove(removedFacility);
			} catch (UnsupportedOperationException e) {
				ArrayList<Facility> facilities = new ArrayList<Facility>(containedFacilities);
				facilities.remove(removedFacility);
				this.addContainedFacilities(facilities);
			}
		} else {
			throw new IllegalArgumentException("the facility to remove is not contained in this facility");
		}
	}

	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	public Collection<Property> getProperties() {
		return properties;
	}

	/**
	 * Remove all current properties of the facility and adds the specified.
	 * 
	 * @param properties
	 *            the properties to set. if properties is null, all properties of the facility are only removed.
	 */
	public void setProperties(Collection<Property> properties) {
		if (properties == null) {
			this.properties = new ArrayList<Property>();
		}
		this.properties = properties;
	}

	/**
	 * Check whether a facility have a property or not.
	 * 
	 * @param property
	 *            the property to check
	 * @return true , when the facility have the specified property
	 */
	public boolean hasProperty(Property property) {
		return properties.contains(property);
	}

	/**
	 * @return all properties of this facility and all properties of all parents.
	 */
	public Collection<Property> getInheritedProperties() {
		LinkedHashSet<Property> properties = new LinkedHashSet<Property>(this.properties);
		if (getParentFacility() != null) {
			properties.addAll(getParentFacility().getInheritedProperties());
		}
		return properties;
	}

	/**
	 * @param property
	 *            the property to check
	 * @return true, when the facility have this property or one of its parents.
	 */
	public boolean hasInheritedProperty(Property property) {
		if (this.properties.contains(property)) {
			return true;
		} else if (getParentFacility() == null) {
			return false;
		} else {
			return getParentFacility().hasInheritedProperty(property);
		}
	}

	// TODO clean code: a for loop would be better to read.
	/**
	 * @param properties
	 *            the properties to check
	 * @return true, when all specified properties are owned be this facility or one of its parents.
	 */
	public boolean hasInheritedProperties(Collection<Property> properties) {
		if (this.properties.containsAll(properties)) {
			return true;
		} else if (getParentFacility() == null) {
			return false;
		} else {
			LinkedList<Property> missingProps = new LinkedList<Property>();
			for (Property property : properties) {
				if (!this.properties.contains(property)) {
					missingProps.add(property);
				}
			}
			return getParentFacility().hasInheritedProperties(missingProps);
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
			throw new IllegalArgumentException("property was added a long time ago ;)");
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
	public void removeProperty(Property property) throws IllegalArgumentException {
		if (property == null) {
			throw new IllegalArgumentException("property must not null.");
		}
		if (getProperties().contains(property)) {
			try {
				properties.remove(property);
			} catch (UnsupportedOperationException e) {
				ArrayList<Property> props = new ArrayList<Property>(properties);
				props.remove(property);
				this.setProperties(props);
			}
		} else {
			throw new IllegalArgumentException("the property to remove is no property of this facility");
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
		if (obj instanceof Facility) {
			Facility f = (Facility) obj;
			return id != null && id.equals(f.id);
		}
		return false;
	}
}
