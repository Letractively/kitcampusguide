package edu.kit.cm.kitcampusguide.controller;

import java.util.Collection;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

// TODO
public class MapListenerImpl implements MapListener {

	@Override
	public void mapLocatorChanged(MapLocator mapLocator) {
		MapModel mapModel = getMapModel();
		
		if (mapLocator.getMapSection() != null) {
			// TODO: Apply category and section filter (disabled for testing)
			Collection<POI> poisBySection = POISourceImpl.getInstance()
					.getPOIsBySection(null, mapModel.getMap(), null);
			mapModel.setPOIs(poisBySection);
			
		}
	}


	@Override
	public void clickOnPOI(String poiID) {
		System.out.println("clickOnPOI");
		MapModel mapModel = getMapModel();
		if (poiID != null) {
			POI poiByID = POISourceImpl.getInstance().getPOIByID(poiID);
			System.out.println("poiByID: " + poiByID);
			System.out.println("ID: " + poiID);
			if (poiByID != null) {
				mapModel.setMapLocator(
						new MapLocator(poiByID.getPosition()));
				mapModel.setHighlightedPOI(poiByID);
			}
		}
		else {
			mapModel.setHighlightedPOI(null);
		}
	}

	@Override
	public void setRouteFromByContextMenu(MapPosition position) {
		System.out.println("setRouteFrom");
		getMapModel().setMarkerFrom(position);
	}

	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		System.out.println("setRouteTo");
		getMapModel().setMarkerTo(position);
	}

	private MapModel getMapModel() {
		ELContext el = FacesContext.getCurrentInstance().getELContext();
		return (MapModel) el.getELResolver().getValue(el, null,
		"mapModel");
	}
}
