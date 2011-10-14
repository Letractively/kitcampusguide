
package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectFaultComplexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectFaultComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="faultMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="findByNamesLike" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Names"/>
 *           &lt;element name="findByNames" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Names"/>
 *           &lt;element name="findByNameSuffixes" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Names"/>
 *           &lt;element name="findByNamePrefixes" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Names"/>
 *           &lt;element name="findByIds" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Ids"/>
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
@XmlType(name = "SelectFaultComplexType", propOrder = {
    "faultMessage",
    "findByNamesLike",
    "findByNames",
    "findByNameSuffixes",
    "findByNamePrefixes",
    "findByIds"
})
public class SelectFaultComplexType {

    @XmlElement(required = true)
    protected String faultMessage;
    protected Names findByNamesLike;
    protected Names findByNames;
    protected Names findByNameSuffixes;
    protected Names findByNamePrefixes;
    protected Ids findByIds;

    /**
     * Gets the value of the faultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * Sets the value of the faultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultMessage(String value) {
        this.faultMessage = value;
    }

    /**
     * Gets the value of the findByNamesLike property.
     * 
     * @return
     *     possible object is
     *     {@link Names }
     *     
     */
    public Names getFindByNamesLike() {
        return findByNamesLike;
    }

    /**
     * Sets the value of the findByNamesLike property.
     * 
     * @param value
     *     allowed object is
     *     {@link Names }
     *     
     */
    public void setFindByNamesLike(Names value) {
        this.findByNamesLike = value;
    }

    /**
     * Gets the value of the findByNames property.
     * 
     * @return
     *     possible object is
     *     {@link Names }
     *     
     */
    public Names getFindByNames() {
        return findByNames;
    }

    /**
     * Sets the value of the findByNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link Names }
     *     
     */
    public void setFindByNames(Names value) {
        this.findByNames = value;
    }

    /**
     * Gets the value of the findByNameSuffixes property.
     * 
     * @return
     *     possible object is
     *     {@link Names }
     *     
     */
    public Names getFindByNameSuffixes() {
        return findByNameSuffixes;
    }

    /**
     * Sets the value of the findByNameSuffixes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Names }
     *     
     */
    public void setFindByNameSuffixes(Names value) {
        this.findByNameSuffixes = value;
    }

    /**
     * Gets the value of the findByNamePrefixes property.
     * 
     * @return
     *     possible object is
     *     {@link Names }
     *     
     */
    public Names getFindByNamePrefixes() {
        return findByNamePrefixes;
    }

    /**
     * Sets the value of the findByNamePrefixes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Names }
     *     
     */
    public void setFindByNamePrefixes(Names value) {
        this.findByNamePrefixes = value;
    }

    /**
     * Gets the value of the findByIds property.
     * 
     * @return
     *     possible object is
     *     {@link Ids }
     *     
     */
    public Ids getFindByIds() {
        return findByIds;
    }

    /**
     * Sets the value of the findByIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ids }
     *     
     */
    public void setFindByIds(Ids value) {
        this.findByIds = value;
    }

}
