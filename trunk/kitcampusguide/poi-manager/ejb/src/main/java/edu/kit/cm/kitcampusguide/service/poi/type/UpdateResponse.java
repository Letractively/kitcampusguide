/**
 * UpdateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

public class UpdateResponse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private PoiWithId poi;

	private java.lang.String successMessage;

	public UpdateResponse() {
	}

	public UpdateResponse(PoiWithId poi, java.lang.String successMessage) {
		this.poi = poi;
		this.successMessage = successMessage;
	}

	public PoiWithId getPoi() {
		return poi;
	}

	public void setPoi(PoiWithId poi) {
		this.poi = poi;
	}

	public java.lang.String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(java.lang.String successMessage) {
		this.successMessage = successMessage;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof UpdateResponse))
			return false;
		UpdateResponse other = (UpdateResponse) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.poi == null && other.getPoi() == null) || (this.poi != null && this.poi
						.equals(other.getPoi())))
				&& ((this.successMessage == null && other.getSuccessMessage() == null) || (this.successMessage != null && this.successMessage
						.equals(other.getSuccessMessage())));
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
		if (getSuccessMessage() != null) {
			_hashCode += getSuccessMessage().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
