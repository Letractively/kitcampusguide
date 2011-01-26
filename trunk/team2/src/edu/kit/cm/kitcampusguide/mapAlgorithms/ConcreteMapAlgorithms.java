package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.LinkedList;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.view.CampusGuide;

public class ConcreteMapAlgorithms implements MapAlgorithms {
	
	private QueryCalculator queryCalculator = ConcreteQueryCalculator.getSingleton();
	private RouteCalculator routeCalculator = AStar.getSingleton();

	@Override
	public LinkedList<POI> getSuggestions(String name) {
		return queryCalculator.getSuggestions(name);
	}

	@Override
	public POI searchPOI(String name) {
		return queryCalculator.searchPOI(name);
	}

	@Override
	public Route calculateRoute(Point from, Point to) {
		return routeCalculator.calculateRoute(from, to, RouteCalculatingUtility.calculateStreetGraph(new Point[] {from, to}));
	}

	@Override
	public String calculateURL(CampusGuide website) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CampusGuide calculateWebsite(String url) {
		// TODO Auto-generated method stub
		return null;
	}

}