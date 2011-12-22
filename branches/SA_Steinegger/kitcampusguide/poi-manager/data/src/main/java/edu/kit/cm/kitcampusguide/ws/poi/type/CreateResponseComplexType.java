package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CreateResponseComplexType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResponseComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="poi" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}PoiWithId"/>
 *         &lt;element name="successMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResponseComplexType", propOrder = {

})
public class CreateResponseComplexType implements Serializable {

    private static final long serialVersionUID = -7219032257282124166L;

    @XmlElement(required = true)
    protected PoiWithId poi;
    @XmlElement(required = true)
    protected String successMessage;

    /**
     * Gets the value of the poi property.
     * 
     * @return possible object is {@link PoiWithId }
     * 
     */
    public PoiWithId getPoi() {
        return poi;
    }

    /**
     * Sets the value of the poi property.
     * 
     * @param value
     *            allowed object is {@link PoiWithId }
     * 
     */
    public void setPoi(PoiWithId value) {
        this.poi = value;
    }

    /**
     * Gets the value of the successMessage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * Sets the value of the successMessage property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSuccessMessage(String value) {
        this.successMessage = value;
    }

}