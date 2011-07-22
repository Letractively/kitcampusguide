package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 2.4.1 2011-07-20T19:00:25.575+02:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "SelectFault", targetNamespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/")
public class SelectFault_Exception extends Exception {
	private static final long serialVersionUID = -3094933266542402328L;

	private edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault selectFault;

	public SelectFault_Exception() {
		super();
	}

	public SelectFault_Exception(String message) {
		super(message);
	}

	public SelectFault_Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public SelectFault_Exception(String message, edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault selectFault) {
		super(message);
		this.selectFault = selectFault;
	}

	public SelectFault_Exception(String message, edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault selectFault,
			Throwable cause) {
		super(message, cause);
		this.selectFault = selectFault;
	}

	public edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault getFaultInfo() {
		return this.selectFault;
	}
}