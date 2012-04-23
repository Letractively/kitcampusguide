package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import edu.kit.cm.kitcampusguide.standardtypes.MapSection;

/**
 * This class is informed if the map's {@link MapSection} changes.
 * @author Stefan
 * @version 1.0
 */
public class MapSectionChangedListener implements ValueChangeListener{

	@Override
	public void processValueChange(ValueChangeEvent arg0)
			throws AbortProcessingException {
		// MapSection newSection = (MapSection) arg0.getNewValue();
		//ELContext el = FacesContext.getCurrentInstance().getELContext();
		// Controller controller = (MapModel) el.getELResolver().getValue(el, null, "controller");
		// TODO: Inform a controller bean about the map change
	}

}
