package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import edu.kit.cm.kitcampusguide.controller.MapListener;
import edu.kit.cm.kitcampusguide.controller.POIListener;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * This class is directly informed about any event happening on the map.#
 * @see MapListener
 * @see POIListener
 * 
 * @author Stefan, Fabian
 * @version 1.2
 */
public class MapEventsListener implements ValueChangeListener {

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {

		PhaseId phaseId = event.getPhaseId();
		
		//Move the event to the correct Phase of the JSF Processing cycle
		if (!phaseId.equals(PhaseId.INVOKE_APPLICATION)) {
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			event.queue();
		} else {
				String id = event.getComponent().getId();
				System.out.println("ID: " + id);
				if (id.equals("mapLocator")) {
					getMapListener().mapLocatorChanged(
							(MapLocator) event.getNewValue());
				} else if (id.equals("markerTo")) {
					getMapListener().setRouteToByContextMenu(
							(MapPosition) event.getNewValue());
				} else if (id.equals("markerFrom")) {
					getMapListener().setRouteFromByContextMenu(
							(MapPosition) event.getNewValue());
				} else if (id.equals("highlightedPOIIDListener")) {
					getMapListener().clickOnPOI((String) event.getNewValue());
				} else if (id.equals("buildingIDListener")) {
					getPOIListener().changeToBuildingMap((Integer) event.getNewValue());
				} else if (id.equals("buildingPOIsListListener")) {
					getPOIListener().showPOIsInBuilding((Integer) event.getNewValue());
				}
		}

	}

	private POIListener getPOIListener() {
			ELContext el = FacesContext.getCurrentInstance().getELContext();
			return (POIListener) el.getELResolver().getValue(el, null,
					"poiListener");
	}

	private MapListener getMapListener() {
		ELContext el = FacesContext.getCurrentInstance().getELContext();
		return (MapListener) el.getELResolver().getValue(el, null,
				"mapListener");
	}
}
