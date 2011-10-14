
package edu.kit.tm.cm.kitcampusguide.poiservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.kit.tm.cm.kitcampusguide.poiservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExecuteResponse_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteResponse");
    private final static QName _ExecuteRequest_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteRequest");
    private final static QName _ExecuteFault_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.kit.tm.cm.kitcampusguide.poiservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateFaultComplexType }
     * 
     */
    public UpdateFaultComplexType createUpdateFaultComplexType() {
        return new UpdateFaultComplexType();
    }

    /**
     * Create an instance of {@link Strings }
     * 
     */
    public Strings createStrings() {
        return new Strings();
    }

    /**
     * Create an instance of {@link CreateResponseComplexType }
     * 
     */
    public CreateResponseComplexType createCreateResponseComplexType() {
        return new CreateResponseComplexType();
    }

    /**
     * Create an instance of {@link ReadRequestComplexType }
     * 
     */
    public ReadRequestComplexType createReadRequestComplexType() {
        return new ReadRequestComplexType();
    }

    /**
     * Create an instance of {@link DeleteFaultComplexType }
     * 
     */
    public DeleteFaultComplexType createDeleteFaultComplexType() {
        return new DeleteFaultComplexType();
    }

    /**
     * Create an instance of {@link UpdateRequestComplexType }
     * 
     */
    public UpdateRequestComplexType createUpdateRequestComplexType() {
        return new UpdateRequestComplexType();
    }

    /**
     * Create an instance of {@link ReadFaultComplexType }
     * 
     */
    public ReadFaultComplexType createReadFaultComplexType() {
        return new ReadFaultComplexType();
    }

    /**
     * Create an instance of {@link SelectResponseComplexType }
     * 
     */
    public SelectResponseComplexType createSelectResponseComplexType() {
        return new SelectResponseComplexType();
    }

    /**
     * Create an instance of {@link ExecuteRequestComplexType }
     * 
     */
    public ExecuteRequestComplexType createExecuteRequestComplexType() {
        return new ExecuteRequestComplexType();
    }

    /**
     * Create an instance of {@link ExecuteFaultComplexType }
     * 
     */
    public ExecuteFaultComplexType createExecuteFaultComplexType() {
        return new ExecuteFaultComplexType();
    }

    /**
     * Create an instance of {@link SelectFaultComplexType }
     * 
     */
    public SelectFaultComplexType createSelectFaultComplexType() {
        return new SelectFaultComplexType();
    }

    /**
     * Create an instance of {@link CreateRequestComplexType }
     * 
     */
    public CreateRequestComplexType createCreateRequestComplexType() {
        return new CreateRequestComplexType();
    }

    /**
     * Create an instance of {@link ReadResponseComplexType }
     * 
     */
    public ReadResponseComplexType createReadResponseComplexType() {
        return new ReadResponseComplexType();
    }

    /**
     * Create an instance of {@link CreateFaultComplexType }
     * 
     */
    public CreateFaultComplexType createCreateFaultComplexType() {
        return new CreateFaultComplexType();
    }

    /**
     * Create an instance of {@link Poi }
     * 
     */
    public Poi createPoi() {
        return new Poi();
    }

    /**
     * Create an instance of {@link ExecuteResponseComplexType }
     * 
     */
    public ExecuteResponseComplexType createExecuteResponseComplexType() {
        return new ExecuteResponseComplexType();
    }

    /**
     * Create an instance of {@link UpdateResponseComplexType }
     * 
     */
    public UpdateResponseComplexType createUpdateResponseComplexType() {
        return new UpdateResponseComplexType();
    }

    /**
     * Create an instance of {@link PoiWithId }
     * 
     */
    public PoiWithId createPoiWithId() {
        return new PoiWithId();
    }

    /**
     * Create an instance of {@link DeleteResponseComplexType }
     * 
     */
    public DeleteResponseComplexType createDeleteResponseComplexType() {
        return new DeleteResponseComplexType();
    }

    /**
     * Create an instance of {@link Ids }
     * 
     */
    public Ids createIds() {
        return new Ids();
    }

    /**
     * Create an instance of {@link Names }
     * 
     */
    public Names createNames() {
        return new Names();
    }

    /**
     * Create an instance of {@link SelectRequestComplexType }
     * 
     */
    public SelectRequestComplexType createSelectRequestComplexType() {
        return new SelectRequestComplexType();
    }

    /**
     * Create an instance of {@link DeleteRequestComplexType }
     * 
     */
    public DeleteRequestComplexType createDeleteRequestComplexType() {
        return new DeleteRequestComplexType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/", name = "ExecuteResponse")
    public JAXBElement<ExecuteResponseComplexType> createExecuteResponse(ExecuteResponseComplexType value) {
        return new JAXBElement<ExecuteResponseComplexType>(_ExecuteResponse_QNAME, ExecuteResponseComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/", name = "ExecuteRequest")
    public JAXBElement<ExecuteRequestComplexType> createExecuteRequest(ExecuteRequestComplexType value) {
        return new JAXBElement<ExecuteRequestComplexType>(_ExecuteRequest_QNAME, ExecuteRequestComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/", name = "ExecuteFault")
    public JAXBElement<ExecuteFaultComplexType> createExecuteFault(ExecuteFaultComplexType value) {
        return new JAXBElement<ExecuteFaultComplexType>(_ExecuteFault_QNAME, ExecuteFaultComplexType.class, null, value);
    }

}
