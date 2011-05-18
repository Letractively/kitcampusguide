package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.io.InputStream;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.apache.log4j.Logger;
import org.apache.myfaces.config.element.MapEntry;

/**
 * Listens to the startup events and calls the initialization method of the
 * KITCampusGuide.
 * 
 * @author Fred
 * 
 */
public class StartupEventListener implements SystemEventListener {

	private static boolean alreadyInitialized = false;

	/**
	 * Processes a SystemEvent by checking if it is a post construct event and
	 * then calling the initializer.
	 */
	@Override
	public void processEvent(SystemEvent event) {
		if (event instanceof PostConstructApplicationEvent) {
			FacesContext context = FacesContext.getCurrentInstance();
			Initializer.main(context.getExternalContext().getResourceAsStream(
					"/WEB-INF/classes/config/Configuration.xml"));
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