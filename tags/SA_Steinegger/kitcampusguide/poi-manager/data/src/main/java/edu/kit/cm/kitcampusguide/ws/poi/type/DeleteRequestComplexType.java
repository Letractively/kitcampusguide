package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.io.Serializable;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DeleteRequestComplexType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteRequestComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteRequestComplexType", propOrder = {

})
public class DeleteRequestComplexType implements Serializable {

    private static final long serialVersionUID = 6968589816266272536L;

    @XmlElement(required = true)
    protected Integer id;

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link Id }
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setId(Integer value) {
        this.id = value;
    }

}
