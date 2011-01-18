package edu.kit.cm.kitcampusguide.controller;

import java.util.Collection;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel.MapProperty;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

// TODO
public class MapListenerImpl implements MapListener {

	@Override
	public void mapSectionChanged(MapSection mapSection) {
		System.out.println("mapSectionChanged");
		MapModel mapModel = getMapModel();
		mapModel.setMapSection(mapSection);
		mapModel.addChangedProperty(MapProperty.mapSection);
		// TODO: Apply category and section filter
		Collection<POI> poisBySection = POISourceImpl.getInstance().getPOIsBySection(null,
				mapModel.getMap(), null);
		System.out.println("Size: " + poisBySection.size());
		mapModel.setPOIs(
				poisBySection);
		mapModel.addChangedProperty(MapProperty.POIs);
	}


	@Override
	public void clickOnPOI(String poiID) {
		System.out.println("clickOnPOI");
		getMapModel().setHighlightedPOIID(poiID);
		getMapModel().addChangedProperty(MapProperty.highlightedPOIID);
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
