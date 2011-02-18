package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListener;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListener;
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
			List<MapEvent> events = (List<MapEvent>) event.getNewValue();
			for (MapEvent mapEvent: events) {
				String type = mapEvent.getType();
				if (type.equals("mapLocatorChanged")) {
					// Is called whenever the map locator property of the map model changes
					getMapListener().mapLocatorChanged(
							(MapLocator) mapEvent.getData());
				} else if (type.equals("setRouteToByContextMenu")) {
					// Is called whenever the "Set route to" method is used
					getMapListener().setRouteToByContextMenu(
							(MapPosition) mapEvent.getData());
				} else if (type.equals("setRouteFromByContextMenu")) {
					// Is called whenever the "Set route from" method is used
					getMapListener().setRouteFromByContextMenu(
							(MapPosition) mapEvent.getData());
				} else if (type.equals("clickOnPOI")) {
					// Is used when another POI should be highlighted.
					getMapListener().clickOnPOI((String) mapEvent.getData());
				} else if (type.equals("changeToBuildingMap")) {
					// Is called when the user wants to change into a building
					getPOIListener().changeToBuildingMap((Integer) mapEvent.getData());
				} else if (type.equals("showPOIsInBuilding")) {
					// Is called when the user wants to see the poi list of a building poi
					getPOIListener().showPOIsInBuilding((Integer) mapEvent.getData());
				} else if (type.equals("listEntryClicked")) {
					getPOIListener().listEntryClicked((String) mapEvent.getData());
				}
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
