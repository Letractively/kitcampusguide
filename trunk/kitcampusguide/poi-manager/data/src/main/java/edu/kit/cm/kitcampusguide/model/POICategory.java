package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kateryna Yurchenko
 * @author Tobias Zündorf
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class POICategory extends AEntity {

	private String name;
	private String icon;
	private String description;
	private boolean visible;
	private List<POI> pois;

	public POICategory() {
		this("", null, "", "");
	}

	public POICategory(String name, Integer id, String icon, String description) {
		this(name, id, icon, description, new ArrayList<POI>());
	}

	public POICategory(String name, Integer id, String icon, String description, Collection<POI> pois) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.pois = new ArrayList<POI>(pois);
		this.visible = false;
		setUid(id);
	}

	public void addPOI(POI poi) {
		if (!this.pois.contains(poi)) {
			this.pois.add(poi);
		}
		if (poi != null && (poi.getCategory() == null || !poi.getCategory().equals(this))) {
			poi.setCategory(this);
		}
	}

	public List<POI> getPois() {
		return this.pois;
	}

	public void setPois(List<POI> pois) {
		this.pois = pois;
		for (POI poi : pois) {
			if (!poi.getCategory().equals(this)) {
				poi.setCategory(this);
			}
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return this.visible;
	}
}
