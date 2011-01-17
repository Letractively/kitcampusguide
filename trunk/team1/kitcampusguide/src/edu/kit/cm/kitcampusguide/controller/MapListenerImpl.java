package edu.kit.cm.kitcampusguide.controller;

import java.util.Collection;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

public class MapListenerImpl implements MapListener {

	private MapModel mapModel = null; //TODO: Change so the MapModel of the current session is invoked.
	
	@Override
	public void mapSectionChanged(MapSection mapSection) {
		mapModel.setMapSection(mapSection);

	}

	@Override
	public void clickOnPOI(String poiID) {
		Collection<POI> pois = mapModel.getPOIs();
		for (POI current : pois) {
			if (poiID.equals(current.getID())) {
				mapModel.setHighlightedPOI(current);
			}
		}
		 
	}

	@Override
	public void setRouteFromByContextMenu(MapPosition position) {
		mapModel.setMarkerFrom(position);

	}

	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		mapModel.setMarkerTo(position);

	}

}
