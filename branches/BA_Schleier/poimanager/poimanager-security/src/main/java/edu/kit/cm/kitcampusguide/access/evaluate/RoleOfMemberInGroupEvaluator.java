package edu.kit.cm.kitcampusguide.access.evaluate;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.axiomatics.sdk.connections.PDPConnection;
import com.axiomatics.sdk.connections.PDPConnectionFactory;
import com.axiomatics.sdk.context.SDKResponseWithTrace;
import com.axiomatics.sdk.context.XacmlRequestBuilder;
import com.axiomatics.xacml.Constants;
import com.axiomatics.xacml.attr.*;
import com.axiomatics.xacml.reqresp.Request;

import edu.kit.cm.kitcampusguide.model.MemberToGroupMapping;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.service.user.MemberUserDetails;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.Poi;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiWithId;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateRequestComplexType;
import edu.kit.cm.kitcampusguide.dao.PoiDao;
import edu.kit.cm.kitcampusguide.dao.exception.PoiDaoException;

public class RoleOfMemberInGroupEvaluator {

    private static final Logger log = Logger.getLogger(RoleOfMemberInGroupEvaluator.class);
    private PoiDao dao;
    
    public RoleOfMemberInGroupEvaluator(PoiDao dao) {
    	this.dao = dao;
    }
    
    public boolean hasPermission(Object targetDomainObject) {
    	boolean decision = false;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	MemberUserDetails user = (MemberUserDetails) auth.getPrincipal();
    	
    	log.info("XACML: START REQUEST: " + targetDomainObject.getClass().getName());
    	HashMap<String, AttributeValue> resourceAttributes = new HashMap<String, AttributeValue>();

    	if (targetDomainObject instanceof CreateRequestComplexType) {
    		CreateRequestComplexType request = (CreateRequestComplexType) targetDomainObject;
    		Poi poi = request.getPoi();
    		resourceAttributes.put("urn:poimanager:poi:group", new StringAttribute(poi.getGroupId()));
    		if(poi.getParentId() == 0) {
    			resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getFalseInstance());
    		} else {
    			POI parent;
				try {
					parent = (POI) this.dao.findByUid(poi.getParentId());
					resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getTrueInstance());
	    			resourceAttributes.put("urn:poimanager:poi:parent:group", new StringAttribute(parent.getGroupId()));
				} catch (PoiDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		decision = pdpRequest(resourceAttributes, user, "create");

    	// TODO
    	} else if (targetDomainObject instanceof ReadRequestComplexType) {
    		ReadRequestComplexType request = (ReadRequestComplexType) targetDomainObject;
    		Poi poi = new Poi();
			try {
				poi = (Poi) this.dao.findByUid(request.getId());
			} catch (PoiDaoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		resourceAttributes.put("urn:poimanager:poi:group", new StringAttribute(poi.getGroupId()));
    		if(poi.getParentId() == 0) {
    			resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getFalseInstance());
    		} else {
    			POI parent;
				try {
					parent = (POI) this.dao.findByUid(poi.getParentId());
					resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getTrueInstance());
	    			resourceAttributes.put("urn:poimanager:poi:parent:group", new StringAttribute(parent.getGroupId()));
				} catch (PoiDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		decision = pdpRequest(resourceAttributes, user, "read");

    	} else if (targetDomainObject instanceof UpdateRequestComplexType) {
    		UpdateRequestComplexType request = (UpdateRequestComplexType) targetDomainObject;
    		PoiWithId poi = request.getPoi();
    		
    		resourceAttributes.put("urn:poimanager:poi:group", new StringAttribute(poi.getGroupId()));
    		if(poi.getParentId() == 0) {
    			resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getFalseInstance());
    		} else {
    			POI parent;
				try {
					parent = (POI) this.dao.findByUid(poi.getParentId());
					resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getTrueInstance());
	    			resourceAttributes.put("urn:poimanager:poi:parent:group", new StringAttribute(parent.getGroupId()));
				} catch (PoiDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		decision = pdpRequest(resourceAttributes, user, "update");
    	
    	// TODO
    	} else if (targetDomainObject instanceof DeleteRequestComplexType) {
    		DeleteRequestComplexType request = (DeleteRequestComplexType) targetDomainObject;
    		
    		Poi poi = new Poi();
			
    		try {
				poi = (Poi) this.dao.findByUid(request.getId());
			} catch (PoiDaoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		resourceAttributes.put("urn:poimanager:poi:group", new StringAttribute(poi.getGroupId()));
    		if(poi.getParentId() == 0) {
    			resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getFalseInstance());
    		} else {
    			POI parent;
				try {
					parent = (POI) this.dao.findByUid(poi.getParentId());
					resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getTrueInstance());
	    			resourceAttributes.put("urn:poimanager:poi:parent:group", new StringAttribute(parent.getGroupId()));
				} catch (PoiDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		decision = pdpRequest(resourceAttributes, user, "delete");
    		
    	// TODO
    	} else if (targetDomainObject instanceof SelectRequestComplexType) {
//    		SelectRequestComplexType request = (SelectRequestComplexType) targetDomainObject;
//    		Poi poi = new Poi();
//			try {
//				poi = (Poi)this.dao.findByUid(request.getFindByIds().getId().get(0));
//			} catch (PoiDaoException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//    		
//    		resourceAttributes.put("urn:poimanager:poi:group", new StringAttribute(poi.getGroupId()));
//    		if(poi.getParentId() == 0) {
//    			resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getFalseInstance());
//    		} else {
//    			POI parent;
//				try {
//					parent = (POI) this.dao.findByUid(poi.getParentId());
//					resourceAttributes.put("urn:poimanager:poi:parent", BooleanAttribute.getTrueInstance());
//	    			resourceAttributes.put("urn:poimanager:poi:parent:group", new StringAttribute(parent.getGroupId()));
//				} catch (PoiDaoException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//    		}
//    		
//    		decision = pdpRequest(resourceAttributes, user, "select");
    		
    	}
    	return decision;
    	
    }

    /* create and evaluate a pdp request for the respective action */
    /**
     * 
     * @param targetDomainObject
     * @param user
     * @param action
     * @return
     */
	private boolean pdpRequest(HashMap<String,AttributeValue> resourceAttributes, MemberUserDetails user, String action) {
    	SDKResponseWithTrace resp = null;
    	try {
			InputStream props = Thread.currentThread().getContextClassLoader().getResourceAsStream("pdp.properties");
    		PDPConnection pdpConnection = PDPConnectionFactory.getPDPConnection(props);
    		XacmlRequestBuilder reqBuild = new XacmlRequestBuilder();
    		
			// add subject (roles in groups)
    		for (MemberToGroupMapping mapping : user.getGroupMappings()) {
    			String role = mapping.getRole();
    			AttributeValue group = new StringAttribute(mapping.getGroup().getName());
    			
    			// No need for group assignments. User is admin.
    			if(role == "ROLE_ADMIN") {
    				log.info("XACML.SUBJECT: Adding admin role");
    				reqBuild.addSubjectAttribute("urn:poimanager:roles", role);
    			} else {
	    			log.info("XACML.SUBJECT: Adding \"" + group + "\" to role " + role);
	    			reqBuild.addSubjectAttribute("urn:poimanager:roles", role);
	    			reqBuild.addSubjectAttribute("urn:poimanager:roles:"+role, group);
    			}
    		}
    		// User belongs to no groups => Student role
    		if(user.getGroupMappings().size() == 0) {
    			log.info("XACML.SUBJECT: Adding student role");
    			reqBuild.addSubjectAttribute("urn:poimanager:roles", "ROLE_STUDENT");
    		}
    		
    		// add action (CRUD)
			log.info("XACML.ACTION: action-id = \"" + action + "\"");
			reqBuild.addActionAttribute(Constants.ACTION_ID, action);
    		
			// add previously defined resource attributes
			//addResourcesToRequest(targetDomainObject, reqBuild);
			for(String key : resourceAttributes.keySet()) {
				log.info("XACML.RESOURCE: " + key + " = " + resourceAttributes.get(key));
				reqBuild.addResourceAttribute(key, resourceAttributes.get(key));
			}
			
			// build request and evaluate
    		Request req = reqBuild.buildRequest();		
			resp = pdpConnection.evaluateWithTrace(req);
			log.info("XACML: Answer = "+resp.getResponse().toString() + " - " + resp.getBiasedDecision() + " - " + resp.getTrace());
	    	return resp.getBiasedDecision();

    	} catch(Exception e) {
			e.printStackTrace();
			
		}
    	return false;
    	
    }

	/* adds resource attributes to the request for the respective action */
	/*private void addResourcesToRequest(Object targetDomainObject, XacmlRequestBuilder reqBuild) {
		try {
			if (targetDomainObject instanceof CreateRequestComplexType) {
	    		Poi poi = ((CreateRequestComplexType) targetDomainObject).getPoi();
	    		
	    		// TODO get parent poi and its groupId
	    		
//	    		log.info("XACML: Adding parentId \"" + parentId + "\"");
//	    		reqBuild.addResourceAttribute("urn:kit:parentId", parentId);
//	    		log.info("XACML: Adding parentGroupId \"" + parentGroupId + "\"");
//	    		reqBuild.addResourceAttribute("urn:kit:parentGroupId", parentGroupId);
	    		
			} else if (targetDomainObject instanceof UpdateRequestComplexType) {
				// TODO get poi groupId and publicly attr
				
			} else if (targetDomainObject instanceof UpdateRequestComplexType) {
				PoiWithId poi = ((UpdateRequestComplexType) targetDomainObject).getPoi();
	    		String groupId = poi.getGroupId();
	    		log.info("XACML: Adding poi-group-id \"" + groupId + "\"");
	    		reqBuild.addResourceAttribute("urn:kit:poigroupid", groupId);
	    	
	    	} else if (targetDomainObject instanceof DeleteRequestComplexType) {
	    		// TODO eeeehrmm... now let me think
	    	
	    	} else if (targetDomainObject instanceof SelectRequestComplexType) {
	    		// TODO something
	    		
	    	}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}*/
}
