package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DeleteResponseComplexType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteResponseComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
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
@XmlType(name = "DeleteResponseComplexType", propOrder = {

})
public class DeleteResponseComplexType implements Serializable {

    private static final long serialVersionUID = -4291612655406269965L;

    @XmlElement(required = true)
    protected String successMessage;

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
