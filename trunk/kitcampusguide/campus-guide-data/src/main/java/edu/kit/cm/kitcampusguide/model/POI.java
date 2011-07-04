package edu.kit.cm.kitcampusguide.model;
/**
 * 
 * This class class specifies an single important point on the map with its geographical information, name, id, icon on 
 * the map and description.
 * 
 * @author Monica Haurilet
 *
 *
 */
public class POI extends Point {

	private final String name;
	private final int id;
	private final String icon;
	private final String description;

	/**
	 * Creates a new POI with its geographical information and the other specified characteristics. 
	 * 
	 * @param name contains the name of the POI.
	 * @param id is an unique number set to each POI.
	 * @param icon contains the name of the icon on the map.
	 * @param description contains a short description of the POI.
	 * @param x is the coordinate on the horizontal axe of the POI on the map.
	 * @param y is the coordinate on the vertical axe of the POI on the map.
	 */
	public POI(String name, int id, String icon, String description, Double x,
			Double y) {
		super(x, y);
		this.description = description;
		this.icon = icon;
		this.id = id;
		this.name = name;
	}

	/**
	 * This method returns the name of the POI.
	 * @return the name of the POI.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method returns the id of the POI. Each POI has its unique id.
	 * @return the id of the POI.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * This method returns the icon of the POI.
	 * @return the icon of the POI.
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * This method returns the description of the POI object.
	 * 
	 * @return the description of the POI.
	 */
	public String getDescription() {
		return this.description;
	}
}
