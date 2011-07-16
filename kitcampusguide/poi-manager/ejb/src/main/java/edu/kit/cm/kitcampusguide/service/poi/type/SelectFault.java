/**
 * SelectFault_Element.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.kit.cm.kitcampusguide.service.poi.type;

public class SelectFault extends java.rmi.RemoteException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.lang.String faultMessage;

	private java.lang.String[] findByNamesLike;

	private java.lang.String[] findByNames;

	private java.lang.String[] findByNameSuffixes;

	private java.lang.String[] findByNamePrefixes;

	private int[] findByIds;

	public SelectFault() {
	}

	public SelectFault(java.lang.String faultMessage, java.lang.String[] findByNamesLike,
			java.lang.String[] findByNames, java.lang.String[] findByNameSuffixes,
			java.lang.String[] findByNamePrefixes, int[] findByIds) {
		this.faultMessage = faultMessage;
		this.findByNamesLike = findByNamesLike;
		this.findByNames = findByNames;
		this.findByNameSuffixes = findByNameSuffixes;
		this.findByNamePrefixes = findByNamePrefixes;
		this.findByIds = findByIds;
	}

	public java.lang.String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(java.lang.String faultMessage) {
		this.faultMessage = faultMessage;
	}

	public java.lang.String[] getFindByNamesLike() {
		return findByNamesLike;
	}

	public void setFindByNamesLike(java.lang.String[] findByNamesLike) {
		this.findByNamesLike = findByNamesLike;
	}

	public java.lang.String[] getFindByNames() {
		return findByNames;
	}

	public void setFindByNames(java.lang.String[] findByNames) {
		this.findByNames = findByNames;
	}

	public java.lang.String[] getFindByNameSuffixes() {
		return findByNameSuffixes;
	}

	public void setFindByNameSuffixes(java.lang.String[] findByNameSuffixes) {
		this.findByNameSuffixes = findByNameSuffixes;
	}

	public java.lang.String[] getFindByNamePrefixes() {
		return findByNamePrefixes;
	}

	public void setFindByNamePrefixes(java.lang.String[] findByNamePrefixes) {
		this.findByNamePrefixes = findByNamePrefixes;
	}

	public int[] getFindByIds() {
		return findByIds;
	}

	public void setFindByIds(int[] findByIds) {
		this.findByIds = findByIds;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof SelectFault))
			return false;
		SelectFault other = (SelectFault) obj;
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
				&& ((this.findByNamesLike == null && other.getFindByNamesLike() == null) || (this.findByNamesLike != null && java.util.Arrays
						.equals(this.findByNamesLike, other.getFindByNamesLike())))
				&& ((this.findByNames == null && other.getFindByNames() == null) || (this.findByNames != null && java.util.Arrays
						.equals(this.findByNames, other.getFindByNames())))
				&& ((this.findByNameSuffixes == null && other.getFindByNameSuffixes() == null) || (this.findByNameSuffixes != null && java.util.Arrays
						.equals(this.findByNameSuffixes, other.getFindByNameSuffixes())))
				&& ((this.findByNamePrefixes == null && other.getFindByNamePrefixes() == null) || (this.findByNamePrefixes != null && java.util.Arrays
						.equals(this.findByNamePrefixes, other.getFindByNamePrefixes())))
				&& ((this.findByIds == null && other.getFindByIds() == null) || (this.findByIds != null && java.util.Arrays
						.equals(this.findByIds, other.getFindByIds())));
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
		if (getFindByNamesLike() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFindByNamesLike()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFindByNamesLike(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getFindByNames() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFindByNames()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFindByNames(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getFindByNameSuffixes() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFindByNameSuffixes()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFindByNameSuffixes(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getFindByNamePrefixes() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFindByNamePrefixes()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFindByNamePrefixes(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getFindByIds() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFindByIds()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFindByIds(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
