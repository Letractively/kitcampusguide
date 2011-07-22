package edu.kit.cm.kitcampusguide.ws.poi.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.cxf.jaxb.JAXBToStringStyle;

import edu.kit.cm.kitcampusguide.model.POI;

/**
 * <p>
 * Java class for Poi complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Poi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categoryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Poi", propOrder = {

})
public class Poi {

	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String categoryName;
	@XmlElement(required = true)
	protected String description;
	protected double longitude;
	protected double latitude;

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the categoryName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the value of the categoryName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCategoryName(String value) {
		this.categoryName = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the longitude property.
	 * 
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the value of the longitude property.
	 * 
	 */
	public void setLongitude(double value) {
		this.longitude = value;
	}

	/**
	 * Gets the value of the latitude property.
	 * 
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the value of the latitude property.
	 * 
	 */
	public void setLatitude(double value) {
		this.latitude = value;
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

	public POI convertToPojo() {
		POI convertion = new POI();

		convertion.setName(name);
		convertion.setDescription(description);
		convertion.setLatitude(latitude);
		convertion.setLongitude(longitude);

		return convertion;
	}

}
