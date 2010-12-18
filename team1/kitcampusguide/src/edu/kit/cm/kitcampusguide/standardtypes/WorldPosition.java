package edu.kit.cm.kitcampusguide.standardtypes;

public class WorldPosition {
	private final double longitude;
	private final double latitude;
	
	public WorldPosition(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
