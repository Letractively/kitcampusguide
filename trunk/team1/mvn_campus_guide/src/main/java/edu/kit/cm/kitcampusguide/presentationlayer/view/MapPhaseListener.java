package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import edu.kit.cm.kitcampusguide.presentationlayer.view.converters.MapModelConverter;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;

/**
 * Implements a JSF Phase Listener to manage the <code>changedProperties</code> property of {@link MapModel}.
 * The changedProperties have to be reset for each request because they still might be set from
 * the request before. Even more, if the web page is requested for the first time, all properties
 * need to be marked as changed for being converted correctly.
 * 
 * @see MapModel
 * @see MapModelConverter
 * @author Stefan
 */
public class MapPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		MapModel mapModel = (MapModel) elContext.getELResolver().getValue(
				elContext, null, "mapModel");
		
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			// Always add all properties at the beginning of a new event. This is necessary
			// if the page is called for the first time. the update model values phase will
			// not be executed then.
			mapModel.addAllProperties();
		}
		else if (event.getPhaseId().equals(PhaseId.UPDATE_MODEL_VALUES)) {
			// Reset the properties in the MapModel.
		
			if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
				// ajax request ==> there is no need to update all map properties. Only 
				// properties changed by the controller components will be updated
				mapModel.resetChangedProperties();
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// Nothing to do here
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
