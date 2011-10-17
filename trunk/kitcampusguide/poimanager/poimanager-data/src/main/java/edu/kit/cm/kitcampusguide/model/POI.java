package edu.kit.cm.kitcampusguide.model;

import java.util.Arrays;
import java.util.Collection;

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
	private Boolean publicly;
	/**
	 * This field is an workaround because jpa 1.0 is not supporting collections
	 * of primitives. Thus the group ids are saved in one field seperated by
	 */
	private String separatedGroupIds;
	private static final String SEPARATOR = "###";

	public POI(String name, Integer id, String icon, String description,
			Double longitude, Double latitude, Collection<String> groupIds,
			Boolean publicly) {
		super(longitude, latitude);
		this.description = description;
		this.icon = icon;
		this.name = name;
		setGroupIds(groupIds);
		this.setId(id);
	}

	public POI(String name, Integer uid, String icon, String description,
			Double longitude, Double latitude, Collection<String> groupIds,
			Boolean publicly, POICategory category) {
		this(name, uid, icon, description, longitude, latitude, groupIds,
				publicly);

		this.category = category;
	}

	public POI() {

		this(null, null, null, null, null, null, null, null);
	}

	public POI(String name, int id, String icon, String description, double longitude,
			double latitude) {
		super(longitude, latitude);
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.setId(id);
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

	public void setGroupIds(Collection<String> groupIds) {
		final StringBuilder separatedGroupIdsBuilder = new StringBuilder();
		if (groupIds != null) {
			for (String groupId : groupIds) {
				separatedGroupIdsBuilder
						.append(getStrippedGroupIdWithSeparator(groupId));
			}
			removeLastSeparator(separatedGroupIdsBuilder);
		}
		this.separatedGroupIds = separatedGroupIdsBuilder.toString();
	}

	private void removeLastSeparator(
			final StringBuilder separatedGroupIdsBuilder) {
		if (separatedGroupIdsBuilder.length() > POI.SEPARATOR.length()) {
			separatedGroupIdsBuilder
					.delete(separatedGroupIdsBuilder.length()
							- POI.SEPARATOR.length(),
							separatedGroupIdsBuilder.length());
		}
	}

	private String getStrippedGroupIdWithSeparator(String groupId) {
		return groupId.replaceAll(POI.SEPARATOR, "") + POI.SEPARATOR;
	}

	public Collection<String> getGroupIds() {
		return Arrays.asList(this.separatedGroupIds.split(POI.SEPARATOR));
	}

	public POICategory getCategory() {
		return category;
	}

	public void setSeparatedGroupIds(String separatedGroupIds) {
		this.separatedGroupIds = separatedGroupIds;
	}

	public String getSeparatedGroupIds() {
		return this.separatedGroupIds;
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
		return clone;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
