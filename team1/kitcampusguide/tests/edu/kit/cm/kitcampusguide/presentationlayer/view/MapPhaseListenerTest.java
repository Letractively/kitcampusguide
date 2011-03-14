package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import junit.framework.Test;
import junit.framework.TestSuite;
import static org.junit.Assert.*;

import org.apache.shale.test.base.AbstractJsfTestCase;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;

public class MapPhaseListenerTest extends AbstractJsfTestCase {
	
	MapPhaseListener mapPhaseListener;
	PhaseEvent testPhaseEvent1;
	PhaseEvent testPhaseEvent2;
	MapModel mapModel;
	
	public MapPhaseListenerTest(String name) {
		 super(name);
	}
	
	public static Test suite() {
	      return new TestSuite(MapPhaseListenerTest.class);
	  }
	
	public void setUp() throws Exception {
	    super.setUp();
	    
	    File root = new File("WebContent");
	    servletContext.setDocumentRoot(root);
	    MapModel mapModel2 = new MapModel();	        
	    // simulate a managed bean
	    facesContext.getExternalContext().getRequestMap().put("mapModel", mapModel2);
	    
	    mapPhaseListener = new MapPhaseListener();
	    testPhaseEvent1 = new PhaseEvent(facesContext, PhaseId.RESTORE_VIEW, lifecycle);
	    testPhaseEvent2 = new PhaseEvent(facesContext, PhaseId.ANY_PHASE, lifecycle);
	    mapModel = mapModel2;
	    
	}
	
	public void tearDown() throws Exception {
	    mapPhaseListener = null;
		
	    super.tearDown();
	}
	
	public void testAfterPhase() {
		mapPhaseListener.afterPhase(testPhaseEvent1);
		Set<MapProperty> allProperties = new HashSet<MapProperty>(Arrays.asList(MapProperty.values()));
		assertEquals(allProperties, mapModel.getChangedProperties());
		
		mapPhaseListener.afterPhase(testPhaseEvent2);
		//nothing to test here
		
		//missing test case: mapPhaseListener.afterPhase() with a PhaseEvent with PhaseId UPDATE_MODEL_VALUES
	}
		
	public void testBeforePhase() {
		//nothing to test here
	}	
	
	public void testGetPhaseId() {
		assertEquals(PhaseId.ANY_PHASE, mapPhaseListener.getPhaseId());
	}
	
}
