package edu.kit.cm.kitcampusguide.service.poi.type;

public class CreateFault extends java.rmi.RemoteException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.lang.String faultMessage;

	private Poi poi;

	public CreateFault() {
	}

	public CreateFault(java.lang.String faultMessage, Poi poi) {
		this.faultMessage = faultMessage;
		this.poi = poi;
	}

	public java.lang.String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(java.lang.String faultMessage) {
		this.faultMessage = faultMessage;
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
		if (!(obj instanceof CreateFault))
			return false;
		CreateFault other = (CreateFault) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.faultMessage == null && other.getFaultMessage() == null) || (this.faultMessage != null && this.faultMessage
						.equals(other.getFaultMessage())))
				&& ((this.poi == null && other.getPoi() == null) || (this.poi != null && this.poi
						.equals(other.getPoi())));
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
		if (getPoi() != null) {
			_hashCode += getPoi().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}
}
