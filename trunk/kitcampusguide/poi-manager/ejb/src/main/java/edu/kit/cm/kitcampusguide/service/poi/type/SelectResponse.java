/**
 * SelectResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

import java.util.ArrayList;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;

public class SelectResponse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.lang.String successMessage;

	private List<PoiWithId> pois;

	public SelectResponse() {
	}

	public SelectResponse(java.lang.String successMessage, List<PoiWithId> pois) {
		this.successMessage = successMessage;
		this.pois = pois;
	}

	public java.lang.String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(java.lang.String successMessage) {
		this.successMessage = successMessage;
	}

	public List<PoiWithId> getPois() {
		return pois;
	}

	public void setPois(List<PoiWithId> pois) {
		this.pois = pois;
	}

	public void convertPoisForResponse(List<POI> pois) {
		List<PoiWithId> poisWithId = new ArrayList<PoiWithId>();

		for (POI poi : pois) {
			poisWithId.add(new PoiWithId(poi, poi.getCategory().getName()));
		}

		setPois(poisWithId);
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof SelectResponse))
			return false;
		SelectResponse other = (SelectResponse) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.successMessage == null && other.getSuccessMessage() == null) || (this.successMessage != null && this.successMessage
						.equals(other.getSuccessMessage())))
				&& ((this.pois == null && other.getPois() == null) || (this.pois != null && this.pois.equals(other
						.getPois())));
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
		if (getSuccessMessage() != null) {
			_hashCode += getSuccessMessage().hashCode();
		}
		if (getPois() != null) {
			_hashCode += getPois().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
