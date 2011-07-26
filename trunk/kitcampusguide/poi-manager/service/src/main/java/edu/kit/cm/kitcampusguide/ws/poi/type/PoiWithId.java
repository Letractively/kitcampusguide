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
 * Java class for PoiWithId complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PoiWithId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "PoiWithId", propOrder = {

})
public class PoiWithId {

	protected Integer uid;
	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String categoryName;
	@XmlElement(required = true)
	protected String description;
	protected Double longitude;
	protected Double latitude;

	public PoiWithId() {
		// empty constructor for serializer
	}

	public PoiWithId(POI poi, String categoryName) {
		setUid(poi.getUid());
		setName(poi.getName());
		setDescription(poi.getDescription());
		setLatitude(poi.getLatitude());
		setLongitude(poi.getLongitude());
		setCategoryName(categoryName);
	}

	/**
	 * Gets the value of the uid property.
	 * 
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * Sets the value of the uid property.
	 * 
	 */
	public void setUid(Integer value) {
		this.uid = value;
	}

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
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the value of the longitude property.
	 * 
	 */
	public void setLongitude(Double value) {
		this.longitude = value;
	}

	/**
	 * Gets the value of the latitude property.
	 * 
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the value of the latitude property.
	 * 
	 */
	public void setLatitude(Double value) {
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

		convertion.setUid(uid);
		convertion.setName(name);
		convertion.setDescription(description);
		convertion.setLatitude(latitude);
		convertion.setLongitude(longitude);

		return convertion;
	}

}
