package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.Collection;

import javax.el.ELContext;
import javax.faces.component.ValueHolder;
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
			if (containsID(id)) {
				// Is called whenevver the map locator property of the map model changes
				if (id.equals("mapLocator")) {
					getMapListener().mapLocatorChanged(
							(MapLocator) event.getNewValue());
				} else if (id.equals("markerTo")) {
					// Is called whenever the "Set route to" method is used
					getMapListener().setRouteToByContextMenu(
							(MapPosition) event.getNewValue());
				} else if (id.equals("markerFrom")) {
					// Is called whenever the "Set route from" method is used
					getMapListener().setRouteFromByContextMenu(
							(MapPosition) event.getNewValue());
				} else if (id.equals("highlightedPOIIDListener")) {
					// Is used when another POI should be highlighted.
					getMapListener().clickOnPOI((String) event.getNewValue());
				} else if (id.equals("buildingIDListener")) {
					// Is called when the user wants to change into a building
					getPOIListener().changeToBuildingMap((Integer) event.getNewValue());
				} else if (id.equals("buildingPOIsListListener")) {
					// Is called when the user wants to see the poi list of a building poi
					getPOIListener().showPOIsInBuilding((Integer) event.getNewValue());
				}
				if (id.endsWith("Listener")) {
					// the values of listener components must set to null, enabling them to recieve
					// new events afterwards
					((ValueHolder)event.getComponent()).setValue(null);
				}
			}
		}

	}

	boolean containsID(String id) {
		Collection<String> ids = FacesContext.getCurrentInstance()
				.getPartialViewContext().getExecuteIds();
		boolean contains = false;
		for (String executeID : ids) {
			if (executeID.endsWith(id)) {
				contains = true;
			}
		}
		return contains;
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
