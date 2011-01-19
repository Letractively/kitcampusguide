package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.io.Serializable;

import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;


// TODO: Javadocs
public class MapLocator implements Serializable {

	private MapSection mapSection;
	private WorldPosition center;

	public MapLocator(MapSection mapSection) {
		if (mapSection == null) {
			throw new NullPointerException();
		}
		this.mapSection = mapSection;
	}

	public MapLocator(WorldPosition center) {
		if (center == null) {
			throw new NullPointerException();
		}
		this.center = center;
	}
	
	public MapSection getMapSection() {
		return mapSection;
	}
	
	public WorldPosition getCenter() {
		return center;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		MapLocator other = (MapLocator) obj;
		if (this.mapSection != null) {
			return this.mapSection.equals(other.mapSection) && other.center == null;
		}
		else {
			return this.center.equals(other.center) && other.mapSection == null;
		}
	}
}
