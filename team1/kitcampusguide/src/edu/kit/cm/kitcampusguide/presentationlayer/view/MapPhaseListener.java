package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;

// TODO
public class MapPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		MapModel mapModel = (MapModel) elContext.getELResolver().getValue(
				elContext, null, "mapModel");
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			mapModel.addAllProperties();
		}
		else if (event.getPhaseId().equals(PhaseId.UPDATE_MODEL_VALUES)) {
			// Reset the properties in the MapModel.
		
			if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
				// ajax request ==> there is no need to update all map properties. Only 
				// properties changed by the controller components will be updated
				mapModel.resetChangedProperties();
			}
//			else {
//				System.out.println("AddAll");
//				mapModel.addAllProperties();
//			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
