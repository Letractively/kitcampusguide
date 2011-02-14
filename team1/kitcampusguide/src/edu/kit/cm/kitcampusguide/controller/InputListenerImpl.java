package edu.kit.cm.kitcampusguide.controller;

import java.util.List;
import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategy;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategyImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Implements the interface {@link InputListener}.
 *  
 * @author Team1
 */
public class InputListenerImpl implements InputListener {	
	
	private Logger logger = Logger.getLogger(InputListenerImpl.class);
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();	
	private POISource poiSource = POISourceImpl.getInstance();	
	private RoutingStrategy routing = RoutingStrategyImpl.getInstance();
	
	private MapModel mapModel;
	private InputModel inputModel;
	private TranslationModel translationModel;
	private DefaultModelValues defaultModelValueClass;	
	
	/**
	 * Default constructor.
	 */
	public InputListenerImpl() {
		
	}
			
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void searchTriggered(String searchTerm, InputField inputField) {
		resetView();
		MapPosition position = positionRepresentedBySearchTerm(searchTerm, inputField);
		if (position != null) {
			if (inputField == InputField.ROUTE_FROM) {
				logger.debug("set markerFrom to " + searchTerm);
				mapModel.setMarkerFrom(position);
			} else {
				logger.debug("set markerTo to " + searchTerm);
				mapModel.setMarkerTo(position);
			}
		} else {
			POI poi = performSearch(searchTerm, inputField);
			if (poi != null) {
				highlightPOI(poi);
			}			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void searchTriggered(POI soughtAfter) {
		resetView();
		highlightPOI(soughtAfter);
	}
	
	//Effects that the POI 'poi' is highlighted in the view. 'poi' mustn't be null.
	private void highlightPOI(POI poi) {
		logger.debug("highlight poi: " + poi.getName());
		mapModel.setMarkerFrom(null);
		mapModel.setMarkerTo(null);
		mapModel.setHighlightedPOI(poi);
		mapModel.setMapLocator(new MapLocator (new MapPosition(poi.getPosition().getLatitude(),
				poi.getPosition().getLongitude(), poi.getMap())));
		mapModel.setMap(poi.getMap());
		if (poi.getMap().getID() != defaultModelValueClass.getDefaultMap().getID()) {
			mapModel.setBuilding(poi.getMap().getBuilding());
		}
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(String routeFrom, String routeTo) {
		resetView();
		MapPosition from = determinePosition(routeFrom, InputField.ROUTE_FROM);	
		MapPosition to = determinePosition(routeTo, InputField.ROUTE_TO);		
		if (from != null && to != null) {			
			calculateRoute(from, to);
		}		
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(String routeFrom, MapPosition to) {
		resetView();
		MapPosition from = determinePosition(routeFrom, InputField.ROUTE_FROM);		
		if (from != null) {			
			calculateRoute(from, to);
		} 		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(MapPosition from, String routeTo) {
		resetView();
		MapPosition to = determinePosition(routeTo, InputField.ROUTE_TO);		
		if (to != null) {			
			calculateRoute(from, to);
		} 	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(MapPosition from, MapPosition to) {
		resetView();
		calculateRoute(from, to);
	}
	
	//Effects that the route from 'from' to 'to' is calculated and displayed in the view.
	//'from' and 'to' mustn't be null.
	private void calculateRoute(MapPosition from, MapPosition to) {
		Route route = routing.calculateRoute(from, to);
		if (route != null) {
			logger.debug("Display route");
			mapModel.setMarkerFrom(route.getStart());
			mapModel.setMarkerTo(route.getEnd());
			mapModel.setRoute(route);
			mapModel.setMapLocator(new MapLocator (route.getBoundingBox()));
			if (from.getMap().getID() == to.getMap().getID()) {	
				mapModel.setMap(from.getMap());
			} else {
				mapModel.setMap(defaultModelValueClass.getDefaultMap());
				mapModel.setBuilding(null);
			}
		} else {
			inputModel.setRouteCalculationFailed(true);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void choiceProposalTriggered(List<POI> proposalList, InputField inputField) {
		if (inputField == InputField.ROUTE_FROM) {
			inputModel.setRouteFromField("");
			inputModel.setRouteFromProposalList(proposalList);			
		} else {
			inputModel.setRouteToField("");
			inputModel.setRouteToProposalList(proposalList);		
		}
	}
	
	//Tries to interpret the String 'searchTerm', which was typed into the InputField 'inputField',
	//as coordinates and returns the corresponding MapPosition. 'searchTerm' mustn't be null.
	//If 'searchTerm' can be interpreted as coordinates and the corresponding marker is set in the MapModel
	//at the same position (not necessary on the same Map), the MapPosition of this marker will be returned.
	//If the conversion fails, null will be returned.
	private MapPosition positionRepresentedBySearchTerm (String searchTerm, InputField inputField) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else {
			if (inputField.equals(InputField.ROUTE_FROM)) {
				MapPosition markerFrom = mapModel.getMarkerFrom();
				if (markerFrom != null && equivalent(coordinate, markerFrom)) {	
					return markerFrom; 
				} else {
					return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());
				}
			} else {
				MapPosition markerTo = mapModel.getMarkerTo();
				if (markerTo != null && equivalent(coordinate, markerTo)) {		
					return markerTo;
				} else {
					return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());
				}
			}			
		}
	}
	
	//Returns true if the WorldPosition 'worldposition' and the MapPosition 'mapposition' are at the same 
	//position, else false. 'worldposition' and 'mapposition' mustn't be null.
	private boolean equivalent(WorldPosition worldposition, MapPosition mapposition) {
		double eps = 1e-6;
		if (Math.abs(worldposition.getLatitude() - mapposition.getLatitude()) < eps 
				&& Math.abs(worldposition.getLongitude() - mapposition.getLongitude()) < eps) {
			return true;
		} else {
			return false;
		}
	}
	
	//Tries to determine the position represented by the String 'searchTerm', which was typed into the
	//InputField 'inputField', and returns it. 'searchTerm' mustn't be null.
	//Tries to interpret 'searchTerm' as coordinates at first. If the conversion fails, a search after an 
	//appropriate POI will be triggered and its position will be returned.
	//If the position can't be determined, null will be returned.
	private MapPosition determinePosition(String searchTerm, InputField inputField) {
		MapPosition position = positionRepresentedBySearchTerm(searchTerm, inputField);
		if (position == null) {
			POI poi = performSearch(searchTerm, inputField);
			if (poi != null) {
				position = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		}
		return position;
	}

	//Executes the search after 'searchTerm' and returns the POI that has been found 
	//if there's a unique search result. 'searchTerm' mustn't be null.
	//If there is no or more than one search result, null will be returned 
	//and the inputModel will be given the corresponding information.
	private POI performSearch(String searchTerm, InputField inputField) {
		List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);	
		if (searchResults == null || searchResults.size() == 0) {
			logger.debug("no search results for " + searchTerm);
			if (inputField == InputField.ROUTE_FROM) {
				inputModel.setRouteFromSearchFailed(true);
			} else {
				inputModel.setRouteToSearchFailed(true);
			}
			return null;
		} else if (searchResults.size() == 1) {
			logger.debug("unique search result for " + searchTerm + " : " + searchResults.get(0).getName());
			return searchResults.get(0);
		} else {
			logger.debug("multiple search results for " + searchTerm);	
			choiceProposalTriggered(searchResults, inputField);
			return null;
		}
	}	
	
	//Resets the view which means that no highlighted POIs, routes and error messages
	//will be displayed anymore.
	private void resetView() {
		mapModel.setHighlightedPOI(null);
		mapModel.setRoute(null);
		inputModel.setRouteFromProposalList(null);
		inputModel.setRouteToProposalList(null);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToSearchFailed(false);
		inputModel.setRouteCalculationFailed(false);
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void languageChangeTriggered(String language) {
		logger.debug("change language to: " + language);
		translationModel.setCurrentLanguage(language);
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeToMapViewTriggered() {
		logger.debug("change to map view");
		mapModel.setMap(defaultModelValueClass.getDefaultMap());
		mapModel.setBuilding(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void changeFloorTriggered(Map floor) {
		logger.debug("change floor to: " + floor.getName());
		mapModel.setMap(floor);
	}
	
	/**
	 * Sets the MapModel-property.
	 * @param mapModel Not null.
	 *
	 */
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	
	/**
	 * Sets the InputModel-property.
	 * @param inputModel Not null.
	 */
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
	
	/**
	 * Sets the TranslationModel-property.
	 * @param translationModel Not null.
	 */
	public void setTranslationModel(TranslationModel translationModel) {
		this.translationModel = translationModel;
	}
	
	/**
	 * Sets the DefaultModelValueClass-property.
	 * @param defaultModelValueClass Not null.
	 */
	public void setDefaultModelValueClass(DefaultModelValues defaultModelValueClass) {
		this.defaultModelValueClass = defaultModelValueClass;
	}		
}
