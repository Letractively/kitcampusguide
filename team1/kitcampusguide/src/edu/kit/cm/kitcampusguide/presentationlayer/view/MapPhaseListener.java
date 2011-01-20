package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;

public class MapPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// Nothing to do here
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// Reset the properties in the mapModel.
		((MapModel) FacesContext
				.getCurrentInstance()
				.getELContext()
				.getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(),
						null, "mapModel")).resetChangedProperties();
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.PROCESS_VALIDATIONS;
	}

}
