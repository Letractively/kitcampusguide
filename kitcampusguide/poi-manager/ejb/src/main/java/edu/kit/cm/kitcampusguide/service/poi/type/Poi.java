/**
 * Poi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

import edu.kit.cm.kitcampusguide.model.POI;

public class Poi implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.lang.String name;

	private java.lang.String categoryName;

	private java.lang.String description;

	private double longitude;

	private double latitude;

	public Poi() {
	}

	public Poi(java.lang.String name, java.lang.String categoryName, java.lang.String description, double longitude,
			double latitude) {
		this.name = name;
		this.categoryName = categoryName;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Poi))
			return false;
		Poi other = (Poi) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.name == null && other.getName() == null) || (this.name != null && this.name.equals(other
						.getName())))
				&& ((this.categoryName == null && other.getCategoryName() == null) || (this.categoryName != null && this.categoryName
						.equals(other.getCategoryName())))
				&& ((this.description == null && other.getDescription() == null) || (this.description != null && this.description
						.equals(other.getDescription()))) && this.longitude == other.getLongitude()
				&& this.latitude == other.getLatitude();
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	@Override
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		if (getCategoryName() != null) {
			_hashCode += getCategoryName().hashCode();
		}
		if (getDescription() != null) {
			_hashCode += getDescription().hashCode();
		}
		_hashCode += new Double(getLongitude()).hashCode();
		_hashCode += new Double(getLatitude()).hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	public POI convertToPojo() {
		POI convertion = new POI();

		convertion.setName(name);
		convertion.setDescription(description);
		convertion.setLatitude(latitude);
		convertion.setLongitude(longitude);

		return convertion;
	}
}
