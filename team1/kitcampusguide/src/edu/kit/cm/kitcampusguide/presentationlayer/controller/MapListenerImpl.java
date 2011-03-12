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
 * @see MapListener
 * @author Fabian, Stefan
 * 
 */
public class MapListenerImpl implements MapListener {

	/**
	 * The coordinate manager used by this implementation.
	 */
	private static final CoordinateManager coordinateManager = CoordinateManagerImpl.getInstance();
	
	/**
	 * The map model which is changed by this instance. This is a managed
	 * property, see faces-config.xml.
	 */
	private MapModel mapModel;
	
	/**
	 * The input model which is changed by this instance. This is a managed property,
	 * see face-config.xml.
	 */
	private InputModel inputModel;
	
	/**
	 * The logger used by this class.
	 */
	private static Logger logger = Logger.getLogger(MapListener.class);

	/**
	 * In this implementation the event is simply ignored. It even should not be
	 * sent due to traffic issues.
	 */
	@Override
	public void mapLocatorChanged(MapLocator mapLocator) {
		logger.debug("mapLocatorChanged received");
	}

	/**
	 * The POI referenced by the given id will be retrieved from the database
	 * and will be highlighted.
	 */
	@Override
	public void clickOnPOI(String poiID) {
		if (poiID != null) {
			//TODO: performanter, wenn die POISource-Instanz als Attribut gehalten und nicht
				//jedes Mal abgefragt wird?
			POI poiByID = POISourceImpl.getInstance().getPOIByID(poiID);
			if (poiByID != null) {
				logger.debug("clickOnPOI received, POI with ID " + poiID
						+ " is highlighted");
				mapModel.setMapLocator(new MapLocator(poiByID.getPosition()));
				mapModel.setHighlightedPOI(poiByID);
			}
		} else {
			logger.debug("clickOnPOI received, highlighted POI is deleted");
			mapModel.setHighlightedPOI(null);
		}
	}

	/**
	 * This implementation simply sets the map model's "marker from" property to
	 * the given position.
	 */
	@Override
	public void setRouteFromByContextMenu(MapPosition position) {
		logger.debug("setRouteFromByContextMenu received");
		mapModel.setMarkerFrom(position);
		inputModel.setRouteFromField(coordinateManager.coordinateToString(position));
	}

	/**
	 * This method simply sets the map model's "marker to" property to the given
	 * position.
	 */
	@Override
	public void setRouteToByContextMenu(MapPosition position) {
		logger.debug("setRouteToByContextMenu received");
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
	 * jsf managed property injection mechanism.
	 * 
	 * @param inputModel
	 *            the new input model
	 */
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
}
