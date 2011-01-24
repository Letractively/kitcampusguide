package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import edu.kit.cm.kitcampusguide.controller.MapListener;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * This class is directly informed about any event happening on the map
 * 
 * @author Stefan, Fabian
 * @version 1.2
 */
public class MapEventsListener implements ValueChangeListener {

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {

		PhaseId phaseId = event.getPhaseId();
		if (!phaseId.equals(PhaseId.INVOKE_APPLICATION)) { //Geaendert nach Tests. Sollte so funktionieren hoffentlich.
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
				}
		}

	}

	private MapListener getMapListener() {
		ELContext el = FacesContext.getCurrentInstance().getELContext();
		return (MapListener) el.getELResolver().getValue(el, null,
				"mapListener");
	}
}