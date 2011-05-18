package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.io.Serializable;

import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * This class is either used to determine a position or a section on a map.
 * 
 * @see WorldPosition
 * @see MapSection
 * @author Stefan
 * 
 */
public class MapLocator implements Serializable {

	/**
	 * The map section described by this instance. Either this or
	 * <code>center</code> is <code>null</code>.
	 */
	private MapSection mapSection;
	
	/**
	 * The world position described by this instance. Either this or
	 * <code>mapSection</code> is <code>null</code>.
	 */
	private WorldPosition center;

	/**
	 * Creates a new <code>MapLocator</code> describing a given
	 * {@link MapSection}.
	 * 
	 * @param mapSection
	 *            the map section which will be described.
	 */
	public MapLocator(MapSection mapSection) {
		if (mapSection == null) {
			throw new NullPointerException();
		}
		this.mapSection = mapSection;
	}

	/**
	 * Creates a new <code>MapLocator</code> describing a given
	 * {@link WorldPosition}.
	 * 
	 * @param center
	 *            the world position which will be described.
	 */
	public MapLocator(WorldPosition center) {
		if (center == null) {
			throw new NullPointerException();
		}
		this.center = center;
	}
	
	/**
	 * Returns the map section described by this instance.
	 * 
	 * @return a map section or <code>null</code> if this instance describes a
	 *         world position.
	 */
	public MapSection getMapSection() {
		return mapSection;
	}
	
	/**
	 * Returns the world position described by this instance.
	 * 
	 * @return a world position or <code>null</code> if this instance describes
	 *         a map section.
	 */
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
