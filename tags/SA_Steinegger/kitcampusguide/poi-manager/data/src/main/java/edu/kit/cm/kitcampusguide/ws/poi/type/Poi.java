package edu.kit.cm.kitcampusguide.ws.poi.type;

import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="groupIds" type="{http://cm.tm.kit.edu/kitcampusguide/poiservice/}Strings"/>
 *         &lt;element name="publicly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    @XmlElement(required = true)
    protected Strings groupIds;
    protected boolean publicly;

    public POI convertToPojo() {
        POI convertion = new POI();

        convertion.setName(name);
        convertion.setDescription(description);
        convertion.setLatitude(latitude);
        convertion.setLongitude(longitude);
        convertion.setPublicly(publicly);
        if (groupIds != null) {
            convertion.setGroupIds(groupIds.getId());
        } else {
            convertion.setGroupIds(new HashSet<String>());
        }

        return convertion;
    }

    public static Poi convertToPoi(PoiWithId poi) {
        Poi convertion = new Poi();

        convertion.setCategoryName(poi.getCategoryName());
        convertion.setName(poi.getName());
        convertion.setDescription(poi.getDescription());
        convertion.setLatitude(poi.getLatitude());
        convertion.setLongitude(poi.getLongitude());
        convertion.setPublicly(poi.publicly);
        convertion.setGroupIds(poi.groupIds);

        return convertion;
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
     * Gets the value of the groupIds property.
     * 
     * @return possible object is {@link Strings }
     * 
     */
    public Strings getGroupIds() {
        return groupIds;
    }

    /**
     * Sets the value of the groupIds property.
     * 
     * @param value
     *            allowed object is {@link Strings }
     * 
     */
    public void setGroupIds(Strings value) {
        this.groupIds = value;
    }

    /**
     * Gets the value of the publicly property.
     * 
     */
    public boolean isPublicly() {
        return publicly;
    }

    /**
     * Sets the value of the publicly property.
     * 
     */
    public void setPublicly(boolean value) {
        this.publicly = value;
    }
}
