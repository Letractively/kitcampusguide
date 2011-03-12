package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

/**
 * Listens to the startup events and calls the initialization method of the KITCampusGuide.
 * @author Fred
 *
 */
public class StartupEventListener implements SystemEventListener {
	 
	/**
	 * Processes a SystemEvent by checking if it is a post construct event and then calling the initializer.
	 */
	  @Override
	  public void processEvent(SystemEvent event) {
		  if (event instanceof PostConstructApplicationEvent) {
			  FacesContext context = FacesContext.getCurrentInstance();
				Initializer.main(context.getExternalContext().getResourceAsStream(
						"/resources/config/Configuration.xml"));  
		  }
	  }

	  /**
	   * {@inheritDoc}
	   */
	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}
	 
}