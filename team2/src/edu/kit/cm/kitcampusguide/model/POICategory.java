package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains the description of a Category of POI.
 * 
 * @author Kateryna Yurchenko
 * @author Tobias Zündorf
 * 
 */

public class POICategory {

	private final String name;
	private final String icon;
	private final int id;
	private final String description;
	private boolean visible;
	
// TODO im Entwurf fehlt dieses Attribut in der Beschreibung (im Diagramm aber nicht)
	private ArrayList<POI> categoryPOI;
	
	/**
	 * Creates a new category of POI with its characteristics which does not contain any POI yet. 
	 * 
	 * @param name the name of the category.
	 * @param id an unique number set to each category.
	 * @param icon the name of the icon on the map.
	 * @param description a short description of the category.
	 * @param visible determines if the category must be shown on the map.
	 */
	public POICategory(String name, int id, String icon, String description) {
		this(name, id, icon, description, new ArrayList<POI>());
	}
	
	public POICategory(String name, int id, String icon, String description, Collection<POI> poi) {
		this.name = name;
		this.id = id;
		this.icon = icon;
		this.description = description;
		this.categoryPOI = new ArrayList<POI>(poi);
		this.visible = false;
	}
	
	/**
	 * Adds a new POI to this category if the category does not contain this POI yet. 
	 * 
	 * @param poi a POI which must be added to the category.
	 */
	public void addPOI(POI poi) {
		if (!this.categoryPOI.contains(poi)) {
			this.categoryPOI.add(poi);
		}
	}
	
	/**
	 * This method returns a set of POI which belong to this category.
	 * 
	 * @return POI of this category.
	 */
	public ArrayList<POI> getAllPOI() {
		return this.categoryPOI;
	}
	
	/**
	 * This method returns the name of the category.
	 * 
	 * @return the name of the category.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method returns the unique id of the category. 
	 * 
	 * @return the id of the category.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * This method returns the icon of the category.
	 * 
	 * @return the icon of the category.
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * This method returns the description of the category.
	 * 
	 * @return the description of the category.
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * This method changes the visibility of this category on the map.
	 * 
	 * @param visible the new visibility of the category.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}	
	
	// TODO im Entwurf fehlt noch diese Methode
	/**
	 * This method returns the visibility of the category.
	 * 
	 * @return true if the the category is visible, otherwise false.
	 */
	public boolean getVisible() {
		return this.visible;
	}
}
