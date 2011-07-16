/**
 * Create.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

public class Create implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Poi poi;

	public Create() {
	}

	public Create(Poi poi) {
		this.poi = poi;
	}

	public Poi getPoi() {
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Create))
			return false;
		Create other = (Create) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.poi == null && other.getPoi() == null) || (this.poi != null && this.poi.equals(other
				.getPoi())));
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
		if (getPoi() != null) {
			_hashCode += getPoi().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
