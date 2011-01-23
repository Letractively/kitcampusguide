package edu.kit.cm.kitcampusguide.controller;

import java.util.Collection;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

// TODO
public class MapListenerImpl implements MapListener {

	@Override
	public void mapLocatorChanged(MapLocator mapLocator) {
		MapModel mapModel = getMapModel();
		
		if (mapLocator.getMapSection() != null) {
			// TODO: Apply category, map and section filter (disabled for testing)
			Collection<POI> poisBySection = POISourceImpl.getInstance()
					.getPOIsBySection(null, null, null);
			mapModel.setPOIs(poisBySection);
			
			mapModel.addChangedProperty(MapProperty.mapLocator);
			mapModel.addChangedProperty(MapProperty.POIs);
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
				mapModel.addChangedProperty(MapProperty.mapLocator);
				mapModel.setHighlightedPOI(poiByID);
			}
		}
		else {
			mapModel.setHighlightedPOI(null);
		}
		mapModel.addChangedProperty(MapProperty.highlightedPOI);
	}

	@Override
	public void setRouteFromByContextMenu(MapPosition position) {
		System.out.println("setRouteFrom");
		getMapModel().setMarkerFrom(position);
		getMapModel().addChangedProperty(MapProperty.markerFrom);
	}

	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		System.out.println("setRouteTo");
		getMapModel().setMarkerTo(position);
		getMapModel().addChangedProperty(MapProperty.markerTo);
	}

	private MapModel getMapModel() {
		ELContext el = FacesContext.getCurrentInstance().getELContext();
		return (MapModel) el.getELResolver().getValue(el, null,
		"mapModel");
	}
}
