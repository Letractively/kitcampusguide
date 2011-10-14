package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UpdateRequestComplexType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateRequestComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="poi" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}PoiWithId"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateRequestComplexType", propOrder = {

})
public class UpdateRequestComplexType implements Serializable {

    private static final long serialVersionUID = -5883108164706799722L;

    @XmlElement(required = true)
    protected PoiWithId poi;

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

}
