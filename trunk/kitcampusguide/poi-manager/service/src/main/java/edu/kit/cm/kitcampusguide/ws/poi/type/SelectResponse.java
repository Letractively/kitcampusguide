package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.cxf.jaxb.JAXBToStringStyle;

import edu.kit.cm.kitcampusguide.model.POI;

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
 *       &lt;sequence>
 *         &lt;element name="successMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="poi" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}PoiWithId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "successMessage", "poi" })
@XmlRootElement(name = "SelectResponse")
public class SelectResponse {

	@XmlElement(required = true)
	protected String successMessage;
	protected List<PoiWithId> poi;

	public void convertPoisForResponse(List<POI> pois) {
		List<PoiWithId> poisWithId = new ArrayList<PoiWithId>();

		for (POI poi : pois) {
			poisWithId.add(new PoiWithId(poi, getCategoryNameForPoi(poi)));
		}

		poi = poisWithId;
	}

	private String getCategoryNameForPoi(POI poi) {
		if (poi.getCategory() == null) {
			return "";
		} else {
			return poi.getCategory().getName();
		}
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

	/**
	 * Gets the value of the poi property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the poi property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPoi().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link PoiWithId }
	 * 
	 * 
	 */
	public List<PoiWithId> getPoi() {
		if (poi == null) {
			poi = new ArrayList<PoiWithId>();
		}
		return this.poi;
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
