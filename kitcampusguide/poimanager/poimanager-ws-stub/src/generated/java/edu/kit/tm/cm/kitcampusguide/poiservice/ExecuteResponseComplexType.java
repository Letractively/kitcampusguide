
package edu.kit.tm.cm.kitcampusguide.poiservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExecuteResponseComplexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecuteResponseComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="createResponses" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}CreateResponseComplexType"/>
 *           &lt;element name="readResponses" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}ReadResponseComplexType"/>
 *           &lt;element name="updateResponses" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}UpdateResponseComplexType"/>
 *           &lt;element name="deleteResponses" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}DeleteResponseComplexType"/>
 *           &lt;element name="selectResponses" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}SelectResponseComplexType"/>
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
@XmlType(name = "ExecuteResponseComplexType", propOrder = {
    "createResponsesOrReadResponsesOrUpdateResponses"
})
public class ExecuteResponseComplexType {

    @XmlElements({
        @XmlElement(name = "updateResponses", type = UpdateResponseComplexType.class),
        @XmlElement(name = "selectResponses", type = SelectResponseComplexType.class),
        @XmlElement(name = "deleteResponses", type = DeleteResponseComplexType.class),
        @XmlElement(name = "createResponses", type = CreateResponseComplexType.class),
        @XmlElement(name = "readResponses", type = ReadResponseComplexType.class)
    })
    protected List<Object> createResponsesOrReadResponsesOrUpdateResponses;

    /**
     * Gets the value of the createResponsesOrReadResponsesOrUpdateResponses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createResponsesOrReadResponsesOrUpdateResponses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateResponsesOrReadResponsesOrUpdateResponses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateResponseComplexType }
     * {@link SelectResponseComplexType }
     * {@link DeleteResponseComplexType }
     * {@link CreateResponseComplexType }
     * {@link ReadResponseComplexType }
     * 
     * 
     */
    public List<Object> getCreateResponsesOrReadResponsesOrUpdateResponses() {
        if (createResponsesOrReadResponsesOrUpdateResponses == null) {
            createResponsesOrReadResponsesOrUpdateResponses = new ArrayList<Object>();
        }
        return this.createResponsesOrReadResponsesOrUpdateResponses;
    }

}
