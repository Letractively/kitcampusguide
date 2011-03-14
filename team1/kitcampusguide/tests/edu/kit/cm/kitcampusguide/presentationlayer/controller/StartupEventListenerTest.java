package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import javax.faces.component.UICommand;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PostValidateEvent;


import junit.framework.Test;
import junit.framework.TestSuite;
import static org.junit.Assert.*;

import org.apache.shale.test.base.AbstractJsfTestCase;
//import org.springframework.test.annotation.ExpectedException;

public class StartupEventListenerTest extends AbstractJsfTestCase {
	
	StartupEventListener startupEventListener;
	PostConstructApplicationEvent testEvent;
	
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
	}

//	public void testProcessEvent_uninterestingEvent() {
//		
//		startupEventListener.processEvent(new PostValidateEvent(new UICommand())); 
//		//nothing to test here
//	}
//	
//	@ExpectedException(java.net.MalformedURLException.class)
//	public void testProcessEvent_postConstructApplicationEvent () {
//		testEvent = new PostConstructApplicationEvent(application);
//		startupEventListener.processEvent(testEvent);
//		//exception expected since no proper PostConstructApplicationEvent can be thrown when
//		//running this as JUnitTest
//	}
	
}


