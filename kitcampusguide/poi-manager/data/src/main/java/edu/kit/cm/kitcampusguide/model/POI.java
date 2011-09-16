package edu.kit.cm.kitcampusguide.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * This class class specifies an single important point on the map with its
 * geographical information, name, id, icon on the map and description.
 * 
 * @author Monica Haurilet
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class POI extends Point implements Cloneable, Entity {

	private String name;
	private String icon;
	private String description;
	private POICategory category;

	public POI(String name, Integer uid, String icon, String description, Double longitude, Double latitude) {
		super(longitude, latitude);
		this.description = description;
		this.icon = icon;
		this.name = name;
		this.setUid(uid);
	}

	public POI(String name, Integer uid, String icon, String description, Double longitude, Double latitude,
			POICategory category) {
		this(name, uid, icon, description, longitude, latitude);

		this.category = category;
	}

	public POI() {

		this(null, null, null, null, null, null);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getCategoryName() {
		String categoryName = "";

		if (this.getCategory() != null) {

			categoryName = this.getCategory().getName();
		}

		return categoryName;
	}

	public POICategory getCategory() {
		return category;
	}

	public void setCategory(POICategory category) {
		POICategory oldCategory = this.category;
		this.category = category;
		if (oldCategory != null) {
			oldCategory.remove(this);
		}
		if ((category != null) && ((category.getPois() == null) || (!category.getPois().contains(this)))) {
			category.add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		POI other = (POI) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public POI clone() {
		final POI clone = new POI();
		clone.category = this.category;
		clone.description = this.description;
		clone.icon = this.icon;
		clone.name = this.name;
		return clone;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
