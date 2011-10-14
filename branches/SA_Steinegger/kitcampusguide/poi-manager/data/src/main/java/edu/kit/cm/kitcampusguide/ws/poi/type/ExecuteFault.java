package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 2.4.1 2011-09-23T16:24:09.762+02:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "ExecuteFault", targetNamespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/")
public class ExecuteFault extends Exception {

    private static final long serialVersionUID = -7962197661821393351L;

    private edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFaultComplexType executeFault;

    public ExecuteFault() {
        super();
    }

    public ExecuteFault(String message) {
        super(message);
    }

    public ExecuteFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecuteFault(String message, edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFaultComplexType executeFault) {
        super(message);
        this.executeFault = executeFault;
    }

    public ExecuteFault(String message, edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFaultComplexType executeFault,
            Throwable cause) {
        super(message, cause);
        this.executeFault = executeFault;
    }

    public edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFaultComplexType getFaultInfo() {
        return this.executeFault;
    }
}
