
package edu.kit.cm.kitcampusguide.ws.poi.type;

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

    private final static QName _ExecuteResponseComplexTypeCreateResponses_QNAME = new QName("", "createResponses");
    private final static QName _ExecuteResponseComplexTypeReadResponses_QNAME = new QName("", "readResponses");
    private final static QName _ExecuteResponseComplexTypeDeleteResponses_QNAME = new QName("", "deleteResponses");
    private final static QName _ExecuteResponseComplexTypeUpdateResponses_QNAME = new QName("", "updateResponses");
    private final static QName _ExecuteResponseComplexTypeSelectResponses_QNAME = new QName("", "selectResponses");
    private final static QName _ExecuteResponse_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteResponse");
    private final static QName _ExecuteRequest_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteRequest");
    private final static QName _ExecuteFault_QNAME = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "ExecuteFault");
    private final static QName _ExecuteRequestComplexTypeReadRequests_QNAME = new QName("", "readRequests");
    private final static QName _ExecuteRequestComplexTypeDeleteRequests_QNAME = new QName("", "deleteRequests");
    private final static QName _ExecuteRequestComplexTypeUpdateRequests_QNAME = new QName("", "updateRequests");
    private final static QName _ExecuteRequestComplexTypeCreateRequests_QNAME = new QName("", "createRequests");
    private final static QName _ExecuteRequestComplexTypeSelectRequests_QNAME = new QName("", "selectRequests");
    private final static QName _ExecuteFaultComplexTypeUpdateFaults_QNAME = new QName("", "updateFaults");
    private final static QName _ExecuteFaultComplexTypeSelectFaults_QNAME = new QName("", "selectFaults");
    private final static QName _ExecuteFaultComplexTypeReadFaults_QNAME = new QName("", "readFaults");
    private final static QName _ExecuteFaultComplexTypeDeleteFaults_QNAME = new QName("", "deleteFaults");
    private final static QName _ExecuteFaultComplexTypeCreateFaults_QNAME = new QName("", "createFaults");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.kit.tm.cm.kitcampusguide.poiservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExecuteResponseComplexType }
     * 
     */
    public ExecuteResponseComplexType createExecuteResponseComplexType() {
        return new ExecuteResponseComplexType();
    }

    /**
     * Create an instance of {@link PoiWithId }
     * 
     */
    public PoiWithId createPoiWithId() {
        return new PoiWithId();
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
     * Create an instance of {@link CreateRequestComplexType }
     * 
     */
    public CreateRequestComplexType createCreateRequestComplexType() {
        return new CreateRequestComplexType();
    }

    /**
     * Create an instance of {@link ExecuteRequestComplexType }
     * 
     */
    public ExecuteRequestComplexType createExecuteRequestComplexType() {
        return new ExecuteRequestComplexType();
    }

    /**
     * Create an instance of {@link SelectRequestComplexType }
     * 
     */
    public SelectRequestComplexType createSelectRequestComplexType() {
        return new SelectRequestComplexType();
    }

    /**
     * Create an instance of {@link SelectFaultComplexType }
     * 
     */
    public SelectFaultComplexType createSelectFaultComplexType() {
        return new SelectFaultComplexType();
    }

    /**
     * Create an instance of {@link UpdateResponseComplexType }
     * 
     */
    public UpdateResponseComplexType createUpdateResponseComplexType() {
        return new UpdateResponseComplexType();
    }

    /**
     * Create an instance of {@link CreateFaultComplexType }
     * 
     */
    public CreateFaultComplexType createCreateFaultComplexType() {
        return new CreateFaultComplexType();
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
     * Create an instance of {@link Poi }
     * 
     */
    public Poi createPoi() {
        return new Poi();
    }

    /**
     * Create an instance of {@link UpdateRequestComplexType }
     * 
     */
    public UpdateRequestComplexType createUpdateRequestComplexType() {
        return new UpdateRequestComplexType();
    }

    /**
     * Create an instance of {@link ReadResponseComplexType }
     * 
     */
    public ReadResponseComplexType createReadResponseComplexType() {
        return new ReadResponseComplexType();
    }

    /**
     * Create an instance of {@link DeleteResponseComplexType }
     * 
     */
    public DeleteResponseComplexType createDeleteResponseComplexType() {
        return new DeleteResponseComplexType();
    }

    /**
     * Create an instance of {@link DeleteRequestComplexType }
     * 
     */
    public DeleteRequestComplexType createDeleteRequestComplexType() {
        return new DeleteRequestComplexType();
    }

    /**
     * Create an instance of {@link CreateResponseComplexType }
     * 
     */
    public CreateResponseComplexType createCreateResponseComplexType() {
        return new CreateResponseComplexType();
    }

    /**
     * Create an instance of {@link SelectResponseComplexType }
     * 
     */
    public SelectResponseComplexType createSelectResponseComplexType() {
        return new SelectResponseComplexType();
    }

    /**
     * Create an instance of {@link Ids }
     * 
     */
    public Ids createIds() {
        return new Ids();
    }

    /**
     * Create an instance of {@link ExecuteFaultComplexType }
     * 
     */
    public ExecuteFaultComplexType createExecuteFaultComplexType() {
        return new ExecuteFaultComplexType();
    }

    /**
     * Create an instance of {@link Names }
     * 
     */
    public Names createNames() {
        return new Names();
    }

    /**
     * Create an instance of {@link ReadFaultComplexType }
     * 
     */
    public ReadFaultComplexType createReadFaultComplexType() {
        return new ReadFaultComplexType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "createResponses", scope = ExecuteResponseComplexType.class)
    public JAXBElement<CreateResponseComplexType> createExecuteResponseComplexTypeCreateResponses(CreateResponseComplexType value) {
        return new JAXBElement<CreateResponseComplexType>(_ExecuteResponseComplexTypeCreateResponses_QNAME, CreateResponseComplexType.class, ExecuteResponseComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "readResponses", scope = ExecuteResponseComplexType.class)
    public JAXBElement<ReadResponseComplexType> createExecuteResponseComplexTypeReadResponses(ReadResponseComplexType value) {
        return new JAXBElement<ReadResponseComplexType>(_ExecuteResponseComplexTypeReadResponses_QNAME, ReadResponseComplexType.class, ExecuteResponseComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "deleteResponses", scope = ExecuteResponseComplexType.class)
    public JAXBElement<DeleteResponseComplexType> createExecuteResponseComplexTypeDeleteResponses(DeleteResponseComplexType value) {
        return new JAXBElement<DeleteResponseComplexType>(_ExecuteResponseComplexTypeDeleteResponses_QNAME, DeleteResponseComplexType.class, ExecuteResponseComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updateResponses", scope = ExecuteResponseComplexType.class)
    public JAXBElement<UpdateResponseComplexType> createExecuteResponseComplexTypeUpdateResponses(UpdateResponseComplexType value) {
        return new JAXBElement<UpdateResponseComplexType>(_ExecuteResponseComplexTypeUpdateResponses_QNAME, UpdateResponseComplexType.class, ExecuteResponseComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectResponseComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "selectResponses", scope = ExecuteResponseComplexType.class)
    public JAXBElement<SelectResponseComplexType> createExecuteResponseComplexTypeSelectResponses(SelectResponseComplexType value) {
        return new JAXBElement<SelectResponseComplexType>(_ExecuteResponseComplexTypeSelectResponses_QNAME, SelectResponseComplexType.class, ExecuteResponseComplexType.class, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "readRequests", scope = ExecuteRequestComplexType.class)
    public JAXBElement<ReadRequestComplexType> createExecuteRequestComplexTypeReadRequests(ReadRequestComplexType value) {
        return new JAXBElement<ReadRequestComplexType>(_ExecuteRequestComplexTypeReadRequests_QNAME, ReadRequestComplexType.class, ExecuteRequestComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "deleteRequests", scope = ExecuteRequestComplexType.class)
    public JAXBElement<DeleteRequestComplexType> createExecuteRequestComplexTypeDeleteRequests(DeleteRequestComplexType value) {
        return new JAXBElement<DeleteRequestComplexType>(_ExecuteRequestComplexTypeDeleteRequests_QNAME, DeleteRequestComplexType.class, ExecuteRequestComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updateRequests", scope = ExecuteRequestComplexType.class)
    public JAXBElement<UpdateRequestComplexType> createExecuteRequestComplexTypeUpdateRequests(UpdateRequestComplexType value) {
        return new JAXBElement<UpdateRequestComplexType>(_ExecuteRequestComplexTypeUpdateRequests_QNAME, UpdateRequestComplexType.class, ExecuteRequestComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "createRequests", scope = ExecuteRequestComplexType.class)
    public JAXBElement<CreateRequestComplexType> createExecuteRequestComplexTypeCreateRequests(CreateRequestComplexType value) {
        return new JAXBElement<CreateRequestComplexType>(_ExecuteRequestComplexTypeCreateRequests_QNAME, CreateRequestComplexType.class, ExecuteRequestComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRequestComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "selectRequests", scope = ExecuteRequestComplexType.class)
    public JAXBElement<SelectRequestComplexType> createExecuteRequestComplexTypeSelectRequests(SelectRequestComplexType value) {
        return new JAXBElement<SelectRequestComplexType>(_ExecuteRequestComplexTypeSelectRequests_QNAME, SelectRequestComplexType.class, ExecuteRequestComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updateFaults", scope = ExecuteFaultComplexType.class)
    public JAXBElement<UpdateFaultComplexType> createExecuteFaultComplexTypeUpdateFaults(UpdateFaultComplexType value) {
        return new JAXBElement<UpdateFaultComplexType>(_ExecuteFaultComplexTypeUpdateFaults_QNAME, UpdateFaultComplexType.class, ExecuteFaultComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "selectFaults", scope = ExecuteFaultComplexType.class)
    public JAXBElement<SelectFaultComplexType> createExecuteFaultComplexTypeSelectFaults(SelectFaultComplexType value) {
        return new JAXBElement<SelectFaultComplexType>(_ExecuteFaultComplexTypeSelectFaults_QNAME, SelectFaultComplexType.class, ExecuteFaultComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "readFaults", scope = ExecuteFaultComplexType.class)
    public JAXBElement<ReadFaultComplexType> createExecuteFaultComplexTypeReadFaults(ReadFaultComplexType value) {
        return new JAXBElement<ReadFaultComplexType>(_ExecuteFaultComplexTypeReadFaults_QNAME, ReadFaultComplexType.class, ExecuteFaultComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "deleteFaults", scope = ExecuteFaultComplexType.class)
    public JAXBElement<DeleteFaultComplexType> createExecuteFaultComplexTypeDeleteFaults(DeleteFaultComplexType value) {
        return new JAXBElement<DeleteFaultComplexType>(_ExecuteFaultComplexTypeDeleteFaults_QNAME, DeleteFaultComplexType.class, ExecuteFaultComplexType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFaultComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "createFaults", scope = ExecuteFaultComplexType.class)
    public JAXBElement<CreateFaultComplexType> createExecuteFaultComplexTypeCreateFaults(CreateFaultComplexType value) {
        return new JAXBElement<CreateFaultComplexType>(_ExecuteFaultComplexTypeCreateFaults_QNAME, CreateFaultComplexType.class, ExecuteFaultComplexType.class, value);
    }

}
