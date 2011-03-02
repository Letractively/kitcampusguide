package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import org.apache.log4j.Logger;

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
	private static Logger logger = Logger.getLogger(MapListener.class);
	
	@Override
	public void mapLocatorChanged(MapLocator mapLocator) {
		// MapLocatorChanged events are disabled hence they cause too much traffic
		logger.debug("mapLocatorChanged recieved");
	}


	@Override
	public void clickOnPOI(String poiID) {
		if (poiID != null) {
			POI poiByID = POISourceImpl.getInstance().getPOIByID(poiID);
			if (poiByID != null) {
				logger.debug("clickOnPOI recieved, POI with ID " + poiID
						+ " is highlighted");
				mapModel.setMapLocator(
						new MapLocator(poiByID.getPosition()));
				mapModel.setHighlightedPOI(poiByID);
			}
		}
		else {
			logger.debug("clickOnPOI recieved, highlighted POI is deleted");
			mapModel.setHighlightedPOI(null);
		}
	}

	@Override
	public void setRouteFromByContextMenu(MapPosition position) {
		logger.debug("setRouteFromByContextMenu recieved");
		mapModel.setMarkerFrom(position);
		inputModel.setRouteFromField(coordinateManager.coordinateToString(position));
	}

	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		logger.debug("setRouteToByContextMenu recieved");
		mapModel.setMarkerTo(position);
		inputModel.setRouteToField(coordinateManager.coordinateToString(position));
	}

	/**
	 * Sets the map model for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param mapModel
	 *            the new map model
	 */
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	/**
	 * Sets the input model for this instance. This method is necessary for the
	 * jsf managed property injeciton mechanism.
	 * 
	 * @param inputModel
	 *            the new input model
	 */
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
}
