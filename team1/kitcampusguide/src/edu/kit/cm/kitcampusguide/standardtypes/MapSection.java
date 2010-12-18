package edu.kit.cm.kitcampusguide.standardtypes;

public class MapSection {
	private final WorldPosition northwestCorner;
	private final WorldPosition southeastCorner;
	
	public MapSection(WorldPosition pos1, WorldPosition pos2) {
		this.northwestCorner = pos1;
		this.southeastCorner = pos2;
	}
	
	public WorldPosition getNorthWest() {
		return northwestCorner;
	}
	
	public WorldPosition getSouthEast() {
		return southeastCorner;
	}
}
