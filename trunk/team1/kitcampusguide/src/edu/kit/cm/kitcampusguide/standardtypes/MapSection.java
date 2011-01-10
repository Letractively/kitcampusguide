package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a section of a {@link Map map}.
 * Saves the northwest and southeast corner.
 * @author fred
 *
 */
public class MapSection {	
	/** Saves the north west corner of the section.*/
	private final WorldPosition northwestCorner;
	
	/** Saves the south east corner of the section.*/
	private final WorldPosition southeastCorner;
	
	/**
	 * Constructs a new <code>MapSection</code> from two {@link WorldPosition}.
	 * @param pos1 The first {@link WorldPosition}.
	 * @param pos2 The second {@link WorldPosition}.
	 */
	public MapSection(WorldPosition pos1, WorldPosition pos2) {
		double northMax = pos1.getLatitude();
		double southMax = pos1.getLatitude();
		double eastMax = pos1.getLongitude();
		double westMax = pos1.getLongitude();
		if (pos2.getLatitude() < northMax) {
			northMax = pos2.getLatitude();
		}
		if (pos2.getLatitude() > southMax) {
			southMax = pos2.getLatitude();
		}
		if (pos2.getLongitude() < westMax) {
			westMax = pos2.getLongitude();
		}
		if (pos2.getLongitude() > eastMax) {
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
}
