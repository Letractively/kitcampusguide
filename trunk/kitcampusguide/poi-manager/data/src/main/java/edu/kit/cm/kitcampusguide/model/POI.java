package edu.kit.cm.kitcampusguide.model;

/**
 * 
 * This class class specifies an single important point on the map with its
 * geographical information, name, id, icon on the map and description.
 * 
 * @author Monica Haurilet
 * 
 * 
 */
public class POI extends Point {

	private String name;
	private String icon;
	private String description;

	/**
	 * Creates a new POI with its geographical information and the other
	 * specified characteristics.
	 * 
	 * @param name
	 *            contains the name of the POI.
	 * @param uid
	 *            is an unique number set to each POI.
	 * @param icon
	 *            contains the name of the icon on the map.
	 * @param description
	 *            contains a short description of the POI.
	 * @param x
	 *            is the coordinate on the horizontal axe of the POI on the map.
	 * @param y
	 *            is the coordinate on the vertical axe of the POI on the map.
	 */
	public POI(String name, Integer uid, String icon, String description, Double x, Double y) {
		super(x, y);
		this.description = description;
		this.icon = icon;
		this.name = name;
		this.setUid(uid);
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

}
