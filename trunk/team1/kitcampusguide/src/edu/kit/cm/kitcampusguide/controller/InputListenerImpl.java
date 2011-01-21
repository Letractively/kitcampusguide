package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.TestPOISource;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

@ManagedBean (name = "inputListener")
@SessionScoped
public class InputListenerImpl implements InputListener {
	
	private boolean routeFromProposalListIsVisible = false;
	private boolean routeFromInformationIsVisible = false;	
	private String routeFromInformation;
	private boolean routeToProposalListIsVisible = false;
	private boolean routeToInformationIsVisible = false;	
	private String routeToInformation;

	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();	
	private MapModel mapModel = (MapModel) FacesContext.getCurrentInstance().getApplication()
	        .getELResolver().getValue(elContext, null, "mapModel");
	private InputModel inputModel = (InputModel) FacesContext.getCurrentInstance().getApplication()
    .getELResolver().getValue(elContext, null, "inputModel");
	private TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
    .getELResolver().getValue(elContext, null, "translationModel");
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();	
	//private POISource poiSource = POISourceImpl.getInstance();	
	private POISource poiSource = new TestPOISource();
		
	private void resetInputArea() {
		setRouteFromProposalListIsVisible(false);
		setRouteFromInformationIsVisible(false);
		setRouteToProposalListIsVisible(false);
		setRouteToInformationIsVisible(false);
	}
	
	public void updateRouteFromField(ValueChangeEvent ve) {
		inputModel.setRouteFromField((String)ve.getNewValue());
		System.out.println("update routeFrom-Field");
	}
	
	public void updateRouteToField(ValueChangeEvent ve) {
		inputModel.setRouteToField((String)ve.getNewValue());
	}
	
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
		((UICommand) FacesContext.getCurrentInstance().getViewRoot().findComponent("searchButtonForm:searchButton")).setValue(label);
	}
	
			
	public void setRouteFromProposalListIsVisible(
			boolean routeFromProposalListIsVisible) {
		this.routeFromProposalListIsVisible = routeFromProposalListIsVisible;
	}

	public boolean isRouteFromProposalListIsVisible() {
		return routeFromProposalListIsVisible;
	}

	public void searchButtonPressed(ActionEvent ae) {
		System.out.println("Suchebutton gedrückt");
		String routeFromField;
		/*if (routeFromProposalListIsVisible) {
			System.out.println("routeFromProposalListIsVisible");
			routeFromField = (String) ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:routeFromProposalList")).getValue();
			System.out.println(routeFromField);
		} else {
			routeFromField = inputModel.getRouteFromField();
		} */
		routeFromField = inputModel.getRouteFromField();
		if (routeFromField == null) {
				routeFromField = "";
		}
		routeFromField.trim();
		String routeToField;
		/*if (routeToProposalListIsVisible) {
			routeToField = (String) ((UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:routeToProposalList")).getValue();
		} else {
			routeToField = inputModel.getRouteToField();
		}*/
		routeToField = inputModel.getRouteToField();
		if (routeToField == null) {
			routeToField = "";
		}
		routeToField.trim();
		resetInputArea();
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
			List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);	
		if (searchResults == null) {
			System.out.println("Keine Suchergebnisse gefunden");
			if (inputField.equals("routeFromField")) {
				setRouteFromInformation(translationModel.tr("Kein passendes Suchergebnis"));
				setRouteFromInformationIsVisible(true);
			} else {
				setRouteToInformation(translationModel.tr("Kein passendes Suchergebnis"));
				setRouteToInformationIsVisible(true);
			}
		} else {
			if (searchResults.size() == 1) {
				POI poi = searchResults.get(0);
				System.out.println("Eindeutiges Suchergebnis: " + poi.getName());
				mapModel.setHighlightedPOIID(poi.getID());
			} else if (searchResults.size() > 1) {
				System.out.println("Vorschlagsliste anzeigen für " + inputField);	
				List<SelectItem> proposalList = createProposalList(searchResults);
				if (inputField.equals("routeFromField")) {
					inputModel.setRouteFromField("");
					setRouteFromProposalListIsVisible(true);
					setRouteFromInformation(translationModel.tr("Bitte auswählen"));
					setRouteFromInformationIsVisible(true);
					inputModel.setRouteFromProposalList(proposalList);
				} else {
					inputModel.setRouteToField("");
					setRouteToProposalListIsVisible(true);
					setRouteToInformation(translationModel.tr("Bitte auswählen"));
					setRouteToInformationIsVisible(true);
					inputModel.setRouteToProposalList(proposalList);
				}
			} else {
				System.out.println("Keine Suchergebnisse gefunden");
				if (inputField.equals("routeFromField")) {
					setRouteFromInformation(translationModel.tr("Kein passendes Suchergebnis"));
					setRouteFromInformationIsVisible(true);
				} else {
					setRouteToInformation(translationModel.tr("Kein passendes Suchergebnis"));
					setRouteToInformationIsVisible(true);
				}
			}
		}
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
		setRouteFromProposalListIsVisible(false);
		setRouteFromInformationIsVisible(false);
	}
	
	public void resetRouteToProposalList(ActionEvent ae) {
		inputModel.setRouteToField("");
		setRouteToProposalListIsVisible(false);
		setRouteToInformationIsVisible(false);
	}
	
	public void choiceProposalTriggered(List<POI> proposalList) {
		// TODO Auto-generated method stub

	}
	
	public void searchPerformed(POI poi, String inputField) {
		
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

	public void setRouteFromInformationIsVisible(
			boolean routeFromInformationIsVisible) {
		this.routeFromInformationIsVisible = routeFromInformationIsVisible;
	}

	public boolean isRouteFromInformationIsVisible() {
		return routeFromInformationIsVisible;
	}

	public void setRouteFromInformation(String routeFromInformation) {
		this.routeFromInformation = routeFromInformation;
	}

	public String getRouteFromInformation() {
		return routeFromInformation;
	}

	public void setRouteToProposalListIsVisible(boolean routeToProposalListIsVisible) {
		this.routeToProposalListIsVisible = routeToProposalListIsVisible;
	}

	public boolean isRouteToProposalListIsVisible() {
		return routeToProposalListIsVisible;
	}

	public void setRouteToInformationIsVisible(boolean routeToInformationIsVisible) {
		this.routeToInformationIsVisible = routeToInformationIsVisible;
	}

	public boolean isRouteToInformationIsVisible() {
		return routeToInformationIsVisible;
	}

	public void setRouteToInformation(String routeToInformation) {
		this.routeToInformation = routeToInformation;
	}

	public String getRouteToInformation() {
		return routeToInformation;
	}

}
