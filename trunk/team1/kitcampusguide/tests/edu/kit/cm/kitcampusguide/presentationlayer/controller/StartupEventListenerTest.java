package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.io.File;

import javax.faces.component.UICommand;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PostValidateEvent;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;

public class StartupEventListenerTest extends AbstractJsfTestCase {
	
	StartupEventListener startupEventListener;
	PostConstructApplicationEvent testEvent;
	//this String must be modified according to where the glassfish-directory is located in your file-system 
	private final static String PATH = "C:/Users/Internet/.eclipse/eclipse-helios-PSE/server/glassfishv3/glassfish/domains/domain1/eclipseApps/kitcampusguide";
	
	public StartupEventListenerTest(String name) {
		 super(name);
	}
	
	public static Test suite() {
	      return new TestSuite(StartupEventListenerTest.class);
	  }
	
	public void setUp() throws Exception {
	    super.setUp();
	    
	    startupEventListener = new StartupEventListener();	    	    
	}
	
	public void tearDown() throws Exception {
	    startupEventListener = null;	    
		
	    super.tearDown();
	}
	
	public void testIsListenerForSource() {
		assertTrue(startupEventListener.isListenerForSource(application));
		assertFalse(startupEventListener.isListenerForSource("source"));
		assertFalse(startupEventListener.isListenerForSource(null));
	}

	public void testProcessEvent_uninterestingEvent() {
	
		startupEventListener.processEvent(new PostValidateEvent(new UICommand())); 
		//nothing to test here
	}
	
	public void testProcessEvent_postConstructApplicationEvent () {
		servletContext.setDocumentRoot(new File(PATH)); 
		testEvent = new PostConstructApplicationEvent(application);		
		startupEventListener.processEvent(testEvent);
		assertNotNull(Map.getMapByID(1));
		assertNotNull(Category.getCategoryByID(1));
		assertNotNull(Building.getBuildingByID(1));
		assertNotNull(POISourceImpl.getInstance().getPOIByID("1"));
		assertNotNull(LanguageManager.getInstance().getLanguageByString("Deutsch"));
		DefaultModelValues defaultModelValues = new DefaultModelValues();
		assertEquals(1, defaultModelValues.getDefaultMap().getID());
	}	
}


