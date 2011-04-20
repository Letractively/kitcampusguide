package edu.kit.cm.kitcampusguide.standardtypes;

import java.io.Serializable;

/**
 * Represents a section of a {@link Map map}.
 * Stores the northwest and southeast corner.
 * Two MapSections representing the same section on a map (same north west and south east corner)
 * don't need to be the same object, due to the WorldPositions coordinates being represented by doubles
 * it's better to construct new MapSections if needed.
 * @author fred
 *
 */
public class MapSection implements Serializable{	
	/** Stores the north west corner of the section.*/
	private final WorldPosition northwestCorner;
	
	/** Stores the south east corner of the section.*/
	private final WorldPosition southeastCorner;
	
	/**
	 * Constructs a new <code>MapSection</code> from two {@link WorldPosition}.
	 * The positions can be any of the positions and don't need to be north east 
	 * and south west edge.
	 * @param pos1 The first {@link WorldPosition}.
	 * @param pos2 The second {@link WorldPosition}.
	 * 
	 * @throws NullPointerException If either WorldPosition is <code>null</code>.
	 */
	public MapSection(WorldPosition pos1, WorldPosition pos2) throws NullPointerException {
		if (pos1 == null || pos2 == null) {
			throw new NullPointerException("MapSection constructed with at least one null WorldPosition.");
		}
		double northMax = pos1.getLatitude();
		double southMax = pos1.getLatitude();
		double eastMax = pos1.getLongitude();
		double westMax = pos1.getLongitude();
		if (pos2.getLatitude() < northMax) {
			northMax = pos2.getLatitude();
		} else {
			southMax = pos2.getLatitude();
		}
		if (pos2.getLongitude() < westMax) {
			westMax = pos2.getLongitude();
		} else {
			eastMax = pos2.getLongitude();	
		}
		this.northwestCorner = new WorldPosition(northMax, westMax);
		this.southeastCorner = new WorldPosition(southMax, eastMax);
	}
	
	/**
	 * Returns the north west corner.
	 * @return A {@link WorldPosition} representing the north west corner.
	 */
	public WorldPosition getNorthWest() {
		return northwestCorner;
	}
	
	/**
	 * Returns the south east corner.
	 * @return A {@link WorldPosition} representing the south east corner.
	 */
	public WorldPosition getSouthEast() {
		return southeastCorner;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MapSection)) {
			return false;
		}
		MapSection other = (MapSection) obj;
		return other.northwestCorner.equals(this.northwestCorner)
				&& other.southeastCorner.equals(this.southeastCorner);
	}
}
