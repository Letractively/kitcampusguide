package edu.kit.cm.kitcampusguide.controller;

import org.apache.log4j.Logger;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.model.SelectItem;

import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.TestPOISource;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategy;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategyImpl;

import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;

import java.util.ArrayList;
import java.util.List;

@ManagedBean (name="inputListener")
@SessionScoped
public class InputListenerImpl implements InputListener {	
	
	private Logger logger = Logger.getLogger(InputListenerImpl.class);

	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();	
	private MapModel mapModel = (MapModel) FacesContext.getCurrentInstance().getApplication()
	        .getELResolver().getValue(elContext, null, "mapModel");
	private InputModel inputModel = (InputModel) FacesContext.getCurrentInstance().getApplication()
    		.getELResolver().getValue(elContext, null, "inputModel");
	private TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
			.getELResolver().getValue(elContext, null, "translationModel");
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();	
	private POISource poiSource = POISourceImpl.getInstance();	
	//private POISource poiSource = new TestPOISource();	
	private RoutingStrategy routing = RoutingStrategyImpl.getInstance();		
	
	public boolean isCalculateRoute() {
		UIInput routeFromFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:routeFromField");
		String routeFromField = (String) routeFromFieldComponent.getValue();
		if (routeFromField == null) {
			routeFromField = "";
			routeFromFieldComponent.setValue("");
		}
		routeFromField = routeFromField.trim();
		if (routeFromField.equals("")) {
			inputModel.setRouteFromSearchFailed(false);
		}
		UIInput routeToFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:routeToField");
		String routeToField = (String) routeToFieldComponent.getValue();
		if (routeToField == null) {
			routeToField = "";
			routeToFieldComponent.setValue("");
		}
		routeToField = routeToField.trim();
		if (routeToField.equals("")) {
			inputModel.setRouteToSearchFailed(false);
		}
		if ((inputModel.isRouteFromProposalListIsVisible() || !routeFromField.equals("")) 
				&& (inputModel.isRouteToProposalListIsVisible() || !routeToField.equals(""))) {
			return true;
		} else {
			return false;
		}
	}
		
	public void refreshInputArea(ValueChangeEvent ve) {			
		//nothing to do here since the appropriate parts will automatically be refreshed when being rendered
	}
	
	public void setSearchButtonLabel(ValueChangeEvent ve) {		
		//nothing to do here, since the searchButtonLabel will be updated while being rendered
	}	

	public void searchButtonPressed(ActionEvent ae) {
		if (inputModel.isRouteFromProposalListIsVisible()) {
			inputModel.setRouteFromField(inputModel.getRouteFromSelection());
		} 
		String routeFromField = inputModel.getRouteFromField();
		if (routeFromField == null) {
				routeFromField = "";
		}
		routeFromField.trim();
		if (inputModel.isRouteToProposalListIsVisible()) {
			inputModel.setRouteToField(inputModel.getRouteToSelection());
		} 
		String routeToField = inputModel.getRouteToField();
		if (routeToField == null) {
			routeToField = "";
		}
		routeToField.trim();
		resetInputArea();
		if (!(routeFromField.equals("")) && !( routeToField.equals(""))) {
			logger.info("calculate route from " + routeFromField + " to " + routeToField);
			routeTriggered(routeFromField, routeToField);
		} else if (!routeFromField.equals("")) {
			logger.info("search for " + routeFromField);
			searchTriggered(routeFromField, InputFields.ROUTE_FROM);
		} else if (!routeToField.equals("")) {
			logger.info("search for " + routeToField);
			searchTriggered(routeToField, InputFields.ROUTE_TO);
		} else {
			logger.info("search button has been pressed but the input fields are empty");
		}
	}	
	
	private void resetInputArea() {
		inputModel.setRouteFromProposalListIsVisible(false);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToProposalListIsVisible(false);
		inputModel.setRouteToSearchFailed(false);
	}
	
	public void searchTriggered(String searchTerm, InputFields inputField) {
		MapPosition position = positionRepresentedBySearchTerm(searchTerm);
		if (position != null) {
			if (inputField == InputFields.ROUTE_FROM) {
				logger.info("set markerFrom to " + searchTerm);
				mapModel.setMarkerFrom(position);
			} else {
				logger.info("set markerTo to " + searchTerm);
				mapModel.setMarkerTo(position);
			}
		} else {
			POI poi = performSearch(searchTerm, inputField);
			if (poi != null) {
				mapModel.setHighlightedPOI(poi);
				mapModel.setMapLocator(new MapLocator (new MapPosition(poi.getPosition().getLatitude(),
						poi.getPosition().getLongitude(), poi.getMap())));
				mapModel.addChangedProperty(MapProperty.highlightedPOI);
				mapModel.addChangedProperty(MapProperty.mapLocator);
			}			
		}
	}
		
	public void routeTriggered(String routeFrom, String routeTo) {
		MapPosition from = positionRepresentedBySearchTerm(routeFrom);
		if (from == null) {
			POI poi = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poi != null) {
				from = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		}
		MapPosition to = positionRepresentedBySearchTerm(routeTo);
		if (to == null) {
			POI poi = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poi != null) {
				to = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		}
		if (from != null && to != null) {
			Route route = routing.calculateRoute(from, to);
			mapModel.setRoute(route);
			mapModel.setMapLocator(new MapLocator (route.getBoundingBox()));
		}		
	}
	
	private MapPosition positionRepresentedBySearchTerm (String searchTerm) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else {
			return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), Map.getMapByID(1));
		}
	}

	private POI performSearch(String searchTerm, InputFields inputField) {
		List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);	
		if (searchResults == null || searchResults.size() == 0) {
			logger.info("no search results for " + searchTerm);
			if (inputField == InputFields.ROUTE_FROM) {
				inputModel.setRouteFromSearchFailed(true);
			} else {
				inputModel.setRouteToSearchFailed(true);
			}
			return null;
		} else if (searchResults.size() == 1) {
			logger.info("unique search result for " + searchTerm + " : " + searchResults.get(0).getName());
			return searchResults.get(0);

		} else {
			logger.info("multiple search results for " + searchTerm);	
			List<SelectItem> proposalList = createProposalList(searchResults);
			if (inputField == InputFields.ROUTE_FROM) {
				inputModel.setRouteFromField("");
				inputModel.setRouteFromProposalList(proposalList);
				inputModel.setRouteFromProposalListIsVisible(true);			
			} else {
				inputModel.setRouteToField("");
				inputModel.setRouteToProposalList(proposalList);
				inputModel.setRouteToProposalListIsVisible(true);			
			}
			return null;
		}
	}	
	
	private List<SelectItem> createProposalList(List<POI> searchResults) {
		List<SelectItem> proposalList = new ArrayList<SelectItem>();
		for (POI poi : searchResults) {
			SelectItem item = new SelectItem();
			item.setLabel(poi.getName());
			item.setValue(poi.getName());
			proposalList.add(item);	
		}
		return proposalList;
	}
	
	public void resetRouteFromProposalList(ActionEvent ae) {
		inputModel.setRouteFromField("");
		inputModel.setRouteFromProposalListIsVisible(false);
	}
	
	public void resetRouteToProposalList(ActionEvent ae) {
		inputModel.setRouteToField("");
		inputModel.setRouteToProposalListIsVisible(false);
	}
	
	public void changeLanguageToEnglish(ActionEvent ae) {
		logger.info("change language to english");
		translationModel.setCurrentLanguage("English");
	}
	
	public void changeLanguageToGerman(ActionEvent ae) {
		logger.info("change language to german");
		translationModel.setCurrentLanguage("Deutsch");
	}
	
	public void languageChangeTriggered(String language) {
		logger.info("change language to " + language);
		translationModel.setCurrentLanguage(language);
	}
	
	//mittlerweile überflüssig?
	public void choiceProposalTriggered(List<POI> proposalList) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeToMapViewTriggered() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeFloorTriggered() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeCategoryFilterTriggered() {
		// TODO Auto-generated method stub

	}

}
