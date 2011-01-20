package edu.kit.cm.kitcampusguide.controller;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class StartupEventListener implements SystemEventListener{
	 
	  @Override
	  public void processEvent(SystemEvent event) {
		  if (event instanceof PostConstructApplicationEvent) {
			  FacesContext context = FacesContext.getCurrentInstance();
				Initializer.main(context.getExternalContext().getResourceAsStream(
						"/resources/config/Configuration.xml"));  
		  }
	  }

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}
	 
	}