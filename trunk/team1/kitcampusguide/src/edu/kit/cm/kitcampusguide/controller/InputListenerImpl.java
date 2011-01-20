package edu.kit.cm.kitcampusguide.controller;

import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

@ManagedBean (name = "inputListener")
public class InputListenerImpl implements InputListener {

	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();	
	private MapModel mapModel = (MapModel) FacesContext.getCurrentInstance().getApplication()
	        .getELResolver().getValue(elContext, null, "mapModel");
	private InputModel inputModel = (InputModel) FacesContext.getCurrentInstance().getApplication()
    .getELResolver().getValue(elContext, null, "inputModel");
	private TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
    .getELResolver().getValue(elContext, null, "translationModel");
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();
	private POISource poiSource = POISourceImpl.getInstance();
	
	/**
	 * Returns the actual appropriate searchButtonLabel
	 * @return
	 */
	public String getSearchButtonLabel() {
		UIInput routeFromFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:routeFromField");
		String routeFromField = (String) routeFromFieldComponent.getValue();
		if (routeFromField == null) {
			routeFromField = "";
			routeFromFieldComponent.setValue("");
		}
		routeFromField = routeFromField.trim();
		UIInput routeToFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:routeToField");
		String routeToField = (String) routeToFieldComponent.getValue();
		if (routeToField == null) {
			routeToField = "";
			routeToFieldComponent.setValue("");
		}
		routeToField = routeToField.trim();
		if (!(routeFromField.equals("")) && !(routeToField.equals(""))) {
			return "calculateRoute";
		} else {
			return "search";
		}
	}
	
	public void setSearchButtonLabel(ValueChangeEvent ve) {		
		String label = translationModel.tr(getSearchButtonLabel());
		((UICommand) FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:searchButton")).setValue(label);
	}
			
	public void searchButtonPressed(ActionEvent ae) {
		String routeFromField = inputModel.getRouteFromField();
		if (routeFromField == null) {
			routeFromField = "";
		}
		routeFromField.trim();
		String routeToField = inputModel.getRouteToField();
		if (routeToField == null) {
			routeToField = "";
		}
		routeToField.trim();
		if (!(routeFromField.equals("")) && !( routeToField.equals(""))) {
			System.out.println("Berechne Route von: " + routeFromField
					+ " nach: " + routeToField);
			routeTriggered(routeFromField, routeToField);
		} else if (!routeFromField.equals("")) {
			System.out.println("Suche " + routeFromField);
			searchTriggered(routeFromField, "routeFromField");
		} else if (!routeToField.equals("")) {
			System.out.println("Suche " + routeToField);
			searchTriggered(routeToField, "routeToField");
		} else {
			System.out.println("Keine Eingabe");
		}
	}

	public void searchTriggered(String searchTerm, String inputField) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate != null) {
			if (inputField.equals("routeFromField")) {
				System.out.println("Markiere " + cm.coordinateToString(coordinate) + " als Anfangspunkt");
				mapModel.setMarkerFrom(new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), Map.getMapByID(1)));
			} else {
				System.out.println("Markiere " + cm.coordinateToString(coordinate) + " als Endpunkt");
				mapModel.setMarkerTo(new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), Map.getMapByID(1)));
			}
		} else {
			/*
			List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);
			if (searchResults.size() == 1) {
				System.out.println("Eindeutiges Suchergebnis: " + searchResults.get(0).getName());
				searchPerformed(searchResults.get(0), inputField);
			} else if (searchResults.size() > 1) {
				System.out.println("Vorschlagsliste anzeigen");
				//TODO: Vorschlagsliste anzeigen
			} else {
				System.out.println("Keine Suchergebnisse gefunden");
				//TODO: Anzeige einer entsprechenden Meldung
			}
			*/
		}
	}
	
	public void choiceProposalTriggered(List<POI> proposalList) {
		// TODO Auto-generated method stub

	}
	
	public void searchPerformed(POI poi, String inputField) {
		mapModel.setHighlightedPOIID(poi.getID());
		if (inputField.equals("routeFromField")) {
			inputModel.setRouteFromField(poi.getName());
		} else {
			inputModel.setRouteToField(poi.getName());
		}
		//TODO: MapSection so setzen, dass auf den POI zentriert wird
	}

	public void routeTriggered(String from, String to) {
		//TODO
	}	
	
	public void changeLanguageToEnglish(ActionEvent ae) {
		translationModel.setCurrentLanguage("English");
	}
	
	public void changeLanguageToGerman(ActionEvent ae) {
		translationModel.setCurrentLanguage("Deutsch");
	}
	
	public void languageChangeTriggered(String language) {
		
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
