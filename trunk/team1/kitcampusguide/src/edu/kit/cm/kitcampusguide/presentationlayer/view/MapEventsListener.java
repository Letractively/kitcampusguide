package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import edu.kit.cm.kitcampusguide.controller.MapListener;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;

/**
 * This class is directly informed about any event happening on the map
 * 
 * @author Stefan
 * @version 1.0
 */
public class MapEventsListener implements ValueChangeListener {

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {

		String id = event.getComponent().getId();
		if (id.equals("mapSection")) {
			getMapListener()
					.mapSectionChanged((MapSection) event.getNewValue());
		} else if (id.equals("markerTo")) {
			getMapListener().setRouteToByContextMenu(
					(MapPosition) event.getNewValue());
		} else if (id.equals("markerFrom")) {
			getMapListener().setRouteFromByContextMenu(
					(MapPosition) event.getNewValue());
		} else if (id.equals("highlightedPOI")) {
			getMapListener().clickOnPOI((String) event.getNewValue());
		}
	}

	private MapListener getMapListener() {
		ELContext el = FacesContext.getCurrentInstance().getELContext();
		return (MapListener) el.getELResolver().getValue(el, null,
				"mapListener");
	}
}
