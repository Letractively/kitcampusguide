package edu.kit.cm.kitcampusguide.model;

/**
 * 
 * This class class specifies an single important point on the map with its
 * geographical information, name, id, icon on the map and description.
 * 
 * @author Monica Haurilet
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class POI extends Point {

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

		this.setCategory(category);
	}

	public POI() {

		this("", 42, "", "", 0d, 0d);
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

	public POICategory getCategory() {
		return category;
	}

	public void setCategory(POICategory category) {
		this.category = category;
		if (category.getPois() != null || !category.getPois().contains(this)) {
			category.addPOI(this);
		}
	}

}
