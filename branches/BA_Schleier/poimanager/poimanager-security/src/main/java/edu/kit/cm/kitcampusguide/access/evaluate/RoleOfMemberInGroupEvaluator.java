package edu.kit.cm.kitcampusguide.access.evaluate;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.axiomatics.sdk.connections.PDPConnection;
import com.axiomatics.sdk.connections.PDPConnectionFactory;
import com.axiomatics.sdk.context.SDKResponseWithTrace;
import com.axiomatics.sdk.context.XacmlRequestBuilder;
import com.axiomatics.xacml.Constants;
import com.axiomatics.xacml.reqresp.Request;

import edu.kit.cm.kitcampusguide.model.MemberToGroupMapping;
import edu.kit.cm.kitcampusguide.service.user.MemberUserDetails;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateRequestComplexType;

public class RoleOfMemberInGroupEvaluator {

    private static final Logger log = Logger.getLogger(RoleOfMemberInGroupEvaluator.class);
    
    public boolean hasPermission(Object targetDomainObject) {
    	boolean decision = false;
    	SecurityContext authentication = SecurityContextHolder.getContext();
    	/*if(authentication.isAuthenticated())
    		log.info("ABC AUTHOK");
    	else
    		log.info("ABC AUTHNOKE");
    	
    	MemberUserDetails user = (MemberUserDetails) authentication.getPrincipal();
    	
    	log.info("XACML: Build request for: " + targetDomainObject.getClass().getName());
    	if(targetDomainObject instanceof UpdateRequestComplexType) {
    		//PoiWithId poi = ((UpdateRequestComplexType) targetDomainObject).getPoi();
    		decision = pdpRequest(user, "update");
    	} else if(targetDomainObject instanceof CreateRequestComplexType) {
    		// Returns Poi instead of PoiWithId :-(
    		decision = pdpRequest(user, "create");
    	} else if(targetDomainObject instanceof DeleteRequestComplexType) {
    		// Doesn't have any method to get poi. Only knows the ID (need the groups)
    		decision = pdpRequest(user, "delete");
    	} else if(targetDomainObject instanceof ReadRequestComplexType) {
    		decision = pdpRequest(user, "read");
    	}*/
    	return decision;
    }
    
    private boolean pdpRequest(MemberUserDetails user, String action) {
    	SDKResponseWithTrace resp = null;
    	try {
			InputStream props = Thread.currentThread().getContextClassLoader().getResourceAsStream("pdp.properties");
			
    		PDPConnection pdpConnection = PDPConnectionFactory.getPDPConnection(props);
			XacmlRequestBuilder reqBuild = new XacmlRequestBuilder();
    		
			log.info("XACML: Adding action-id \"" + action + "\"");
			reqBuild.addActionAttribute(Constants.ACTION_ID, action);
			
    		for(MemberToGroupMapping mapping : user.getGroupMappings()) {
    			String role = mapping.getRole();
    			String group = mapping.getGroup().getName();
    			log.info("XACML: Adding group \"" + group + "\" to role " + role);
    			//reqBuild.addSubjectAttribute("urn:kit:roles:"+role, group);
    			reqBuild.addSubjectAttribute("urn:kit:roles:"+role, "hasRole");
    		}
    		
    		/*Strings groupIds = poi.getGroupIds();
    		for(String groupId : groupIds.getId()) {
    			System.out.println("adding resource group: " + groupId);
    			reqBuild.addResourceAttribute(Constants.RESOURCE_ID, groupId);
    		}*/

			Request req = reqBuild.buildRequest();
							
			resp = pdpConnection.evaluateWithTrace(req);

			log.info("XACML: Answer = "+resp.getResponse().toString());
			
	    	return resp.getBiasedDecision();

    	} catch(Exception e) {
			e.printStackTrace();
		}
    	
    	return false;
    }

}
