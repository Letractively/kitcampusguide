
package edu.kit.tm.cm.kitcampusguide.poiservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExecuteFaultComplexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecuteFaultComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="createFaults" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}CreateFaultComplexType"/>
 *           &lt;element name="readFaults" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}ReadFaultComplexType"/>
 *           &lt;element name="updateFaults" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}UpdateFaultComplexType"/>
 *           &lt;element name="deleteFaults" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}DeleteFaultComplexType"/>
 *           &lt;element name="selectFaults" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}SelectFaultComplexType"/>
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
@XmlType(name = "ExecuteFaultComplexType", propOrder = {
    "createFaultsOrReadFaultsOrUpdateFaults"
})
public class ExecuteFaultComplexType {

    @XmlElements({
        @XmlElement(name = "deleteFaults", type = DeleteFaultComplexType.class),
        @XmlElement(name = "readFaults", type = ReadFaultComplexType.class),
        @XmlElement(name = "createFaults", type = CreateFaultComplexType.class),
        @XmlElement(name = "updateFaults", type = UpdateFaultComplexType.class),
        @XmlElement(name = "selectFaults", type = SelectFaultComplexType.class)
    })
    protected List<Object> createFaultsOrReadFaultsOrUpdateFaults;

    /**
     * Gets the value of the createFaultsOrReadFaultsOrUpdateFaults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createFaultsOrReadFaultsOrUpdateFaults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateFaultsOrReadFaultsOrUpdateFaults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeleteFaultComplexType }
     * {@link ReadFaultComplexType }
     * {@link CreateFaultComplexType }
     * {@link UpdateFaultComplexType }
     * {@link SelectFaultComplexType }
     * 
     * 
     */
    public List<Object> getCreateFaultsOrReadFaultsOrUpdateFaults() {
        if (createFaultsOrReadFaultsOrUpdateFaults == null) {
            createFaultsOrReadFaultsOrUpdateFaults = new ArrayList<Object>();
        }
        return this.createFaultsOrReadFaultsOrUpdateFaults;
    }

}
