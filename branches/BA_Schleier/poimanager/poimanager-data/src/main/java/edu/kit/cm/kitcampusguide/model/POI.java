package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

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
public class POI extends AbstractEntity implements Cloneable, Entity {

	private String name;
	private String icon;
	private String description;
	private POICategory category;
	private Boolean publicly;
	private int parentId;
	private String groupId;
	private Double longitude;
	private Double latitude;
	@Transient private List<POI> children;
	@Transient private POI parent;

	public POI(String name, Integer id, String icon, String description,
			Double longitude, Double latitude, String groupId,
			Boolean publicly) {
		this.description = description;
		this.icon = icon;
		this.name = name;
		this.groupId = groupId;
		this.setId(id);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public POI(String name, Integer uid, String icon, String description,
			Double longitude, Double latitude, String groupId,
			Boolean publicly, POICategory category) {
		this(name, uid, icon, description, longitude, latitude, groupId,
				publicly);

		this.category = category;
	}

	public POI() {

		this(null, null, null, null, null, null, null, null);
	}

	public POI(String name, int id, String icon, String description, double longitude,
			double latitude) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.setId(id);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getParentId() {
		return this.parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public Boolean getPublicly() {
		return this.publicly;
	}
	
	public void setPublicly(Boolean publicly) {
		this.publicly = publicly;
	}

	public Boolean isPublicly() {
		return this.publicly;
	}

	public String getCategoryName() {
		String categoryName = "";

		if (this.getCategory() != null) {

			categoryName = this.getCategory().getName();
		}

		return categoryName;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupId() {
		return groupId;
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
		if ((category != null)
				&& ((category.getPois() == null) || (!category.getPois()
						.contains(this)))) {
			category.add(this);
		}
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}

	public List<POI> getChildren() {
		if(children == null) {
			children = new ArrayList<POI>();
		}
		return children;
	}

	public void setChildren(List<POI> children) {
		this.children = children;
	}

	public POI getParent() {
		return parent;
	}

	public void setParent(POI parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		clone.groupId = this.groupId;
		clone.longitude = this.longitude;
		clone.latitude = this.latitude;
		return clone;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
