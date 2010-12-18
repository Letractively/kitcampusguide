package edu.kit.cm.kitcampusguide.standardtypes;

import java.util.List;

public class Route {
	private final MapPosition start;
	private final MapPosition end;
	private final List<MapPosition> waypoints;
	private final MapSection boundingBox;
	
	public Route(List<MapPosition> waypoints) {
		this.waypoints = waypoints;
		this.start = waypoints.get(0);
		this.end = waypoints.get(waypoints.size() - 1);
		this.boundingBox = calculateBoundingBox();
	}
	
	public MapPosition getStart() {
		return start;
	}
	
	public MapPosition getEnd() {
		return end;
	}
	
	public List<MapPosition> getWaypoints() {
		return waypoints;
	}
	
	public MapSection getBoundingBox() {
		return boundingBox;
	}

	private MapSection calculateBoundingBox() {
		double northMax = waypoints.get(0).getLatitude();
		double southMax = waypoints.get(0).getLatitude();
		double eastMax = waypoints.get(0).getLongitude();
		double westMax = waypoints.get(0).getLongitude();
		for (int i = 1; i < waypoints.size(); i++) {
			if (waypoints.get(i).getLatitude() < northMax) {
				northMax = waypoints.get(i).getLatitude();
			}
			if (waypoints.get(i).getLatitude() > southMax) {
				southMax = waypoints.get(i).getLatitude();
			}
			if (waypoints.get(i).getLongitude() < westMax) {
				westMax = waypoints.get(i).getLongitude();
			}
			if (waypoints.get(i).getLongitude() > eastMax) {
				eastMax = waypoints.get(i).getLongitude();
			}
		}
		return new MapSection(new WorldPosition(westMax, northMax), new WorldPosition(eastMax, southMax));
	}
}
