package edu.kit.cm.kitcampusguide.controller;

import java.util.Collection;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Implements the interface {@link MapListener}
 * @see MapListner
 * @author Fabian, Stefan
 * 
 */
public class MapListenerImpl implements MapListener {

	private static final CoordinateManager coordinateManager = CoordinateManagerImpl.getInstance();
	private MapModel mapModel;
	private InputModel inputModel;
	
	@Override
	public void mapLocatorChanged(MapLocator mapLocator) {
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
		mapModel.setMarkerFrom(position);
		inputModel.setRouteFromField(coordinateManager.coordinateToString(position));
	}

	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		System.out.println("setRouteTo");
		mapModel.setMarkerTo(position);
		inputModel.setRouteToField(coordinateManager.coordinateToString(position));
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
}
