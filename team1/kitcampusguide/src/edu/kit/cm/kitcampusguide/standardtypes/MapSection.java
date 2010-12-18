package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Represents a section of a Map.
 * Saves the northwest and southeast corner.
 * @author frederik.diehl@student.kit.edu
 *
 */
public class MapSection {
	private final WorldPosition northwestCorner;
	private final WorldPosition southeastCorner;
	
	/**
	 * Constructs a new MapSection from two positions.
	 * @param pos1 The first position.
	 * @param pos2 The second position.
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
		this.northwestCorner = new WorldPosition(westMax, northMax);
		this.southeastCorner = new WorldPosition(eastMax, southMax);
	}
	
	/**
	 * Returns the northwest corner.
	 * @return A WorldPosition representing the northwest corner.
	 */
	public WorldPosition getNorthWest() {
		return northwestCorner;
	}
	
	/**
	 * Returns the southeast corner.
	 * @return A WorldPosition representing the southeast corner.
	 */
	public WorldPosition getSouthEast() {
		return southeastCorner;
	}
}
