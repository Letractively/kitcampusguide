/**
 * DeleteFault_Element.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

public class DeleteFault extends java.rmi.RemoteException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.lang.String faultMessage;

	private int id;

	public DeleteFault() {
	}

	public DeleteFault(java.lang.String faultMessage, int id) {
		this.faultMessage = faultMessage;
		this.id = id;
	}

	public java.lang.String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(java.lang.String faultMessage) {
		this.faultMessage = faultMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof DeleteFault))
			return false;
		DeleteFault other = (DeleteFault) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.faultMessage == null && other.getFaultMessage() == null) || (this.faultMessage != null && this.faultMessage
						.equals(other.getFaultMessage()))) && this.id == other.getId();
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
		if (getFaultMessage() != null) {
			_hashCode += getFaultMessage().hashCode();
		}
		_hashCode += getId();
		__hashCodeCalc = false;
		return _hashCode;
	}

}
