
package edu.kit.tm.cm.kitcampusguide.poiservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExecuteRequestComplexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecuteRequestComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="createRequests" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}CreateRequestComplexType"/>
 *           &lt;element name="readRequests" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}ReadRequestComplexType"/>
 *           &lt;element name="updateRequests" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}UpdateRequestComplexType"/>
 *           &lt;element name="deleteRequests" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}DeleteRequestComplexType"/>
 *           &lt;element name="selectRequests" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}SelectRequestComplexType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecuteRequestComplexType", propOrder = {
    "createRequestsOrReadRequestsOrUpdateRequests"
})
public class ExecuteRequestComplexType {

    @XmlElements({
        @XmlElement(name = "updateRequests", type = UpdateRequestComplexType.class),
        @XmlElement(name = "readRequests", type = ReadRequestComplexType.class),
        @XmlElement(name = "createRequests", type = CreateRequestComplexType.class),
        @XmlElement(name = "deleteRequests", type = DeleteRequestComplexType.class),
        @XmlElement(name = "selectRequests", type = SelectRequestComplexType.class)
    })
    protected List<Object> createRequestsOrReadRequestsOrUpdateRequests;

    /**
     * Gets the value of the createRequestsOrReadRequestsOrUpdateRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createRequestsOrReadRequestsOrUpdateRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateRequestsOrReadRequestsOrUpdateRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateRequestComplexType }
     * {@link ReadRequestComplexType }
     * {@link CreateRequestComplexType }
     * {@link DeleteRequestComplexType }
     * {@link SelectRequestComplexType }
     * 
     * 
     */
    public List<Object> getCreateRequestsOrReadRequestsOrUpdateRequests() {
        if (createRequestsOrReadRequestsOrUpdateRequests == null) {
            createRequestsOrReadRequestsOrUpdateRequests = new ArrayList<Object>();
        }
        return this.createRequestsOrReadRequestsOrUpdateRequests;
    }

}
