/**
 * ReadFault_Element.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

public class ReadFault extends java.rmi.RemoteException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private java.lang.String faultMessage;

	public ReadFault() {
	}

	public ReadFault(int id, java.lang.String faultMessage) {
		this.id = id;
		this.faultMessage = faultMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(java.lang.String faultMessage) {
		this.faultMessage = faultMessage;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof ReadFault))
			return false;
		ReadFault other = (ReadFault) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& this.id == other.getId()
				&& ((this.faultMessage == null && other.getFaultMessage() == null) || (this.faultMessage != null && this.faultMessage
						.equals(other.getFaultMessage())));
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
		_hashCode += getId();
		if (getFaultMessage() != null) {
			_hashCode += getFaultMessage().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
