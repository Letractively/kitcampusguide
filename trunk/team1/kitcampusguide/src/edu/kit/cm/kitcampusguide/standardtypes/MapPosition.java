package edu.kit.cm.kitcampusguide.standardtypes;

public class MapPosition  extends WorldPosition {
	private final Map map;
	
	public MapPosition(double longitude, double latitude, Map map) {
		super(longitude, latitude);
		this.map = map;
	}

	public Map getMap() {
		return map;
	}
}
