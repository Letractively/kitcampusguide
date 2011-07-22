package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.cxf.jaxb.JAXBToStringStyle;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="faultMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "CreateFault")
public class CreateFault {

	@XmlElement(required = true)
	protected String faultMessage;
	@XmlElement(required = true)
	protected Poi poi;

	/**
	 * Gets the value of the faultMessage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFaultMessage() {
		return faultMessage;
	}

	/**
	 * Sets the value of the faultMessage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFaultMessage(String value) {
		this.faultMessage = value;
	}

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

	/**
	 * Generates a String representation of the contents of this type. This is
	 * an extension method, produced by the 'ts' xjc plugin
	 * 
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
	}

}
