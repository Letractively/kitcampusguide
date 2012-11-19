package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CreateRequestComplexType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRequestComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="poi" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Poi"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRequestComplexType", propOrder = {

})
public class CreateRequestComplexType implements Serializable {

    private static final long serialVersionUID = -7150952045276934763L;

    @XmlElement(required = true)
    protected Poi poi;

    /**
     * Gets the value of the poi property.
     * 
     * @return possible object is {@link Poi }
     * 
     */
    public Poi getPoi() {
        return poi;
    }

    /**
     * Sets the value of the poi property.
     * 
     * @param value
     *            allowed object is {@link Poi }
     * 
     */
    public void setPoi(Poi value) {
        this.poi = value;
    }

}
