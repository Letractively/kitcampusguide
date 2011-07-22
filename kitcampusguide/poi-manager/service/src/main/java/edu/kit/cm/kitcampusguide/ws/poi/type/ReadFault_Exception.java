package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 2.4.1 2011-07-20T19:00:25.596+02:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "ReadFault", targetNamespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/")
public class ReadFault_Exception extends Exception {
	private static final long serialVersionUID = 7063016071355120950L;

	private edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault readFault;

	public ReadFault_Exception() {
		super();
	}

	public ReadFault_Exception(String message) {
		super(message);
	}

	public ReadFault_Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public ReadFault_Exception(String message, edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault readFault) {
		super(message);
		this.readFault = readFault;
	}

	public ReadFault_Exception(String message, edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault readFault, Throwable cause) {
		super(message, cause);
		this.readFault = readFault;
	}

	public edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault getFaultInfo() {
		return this.readFault;
	}
}