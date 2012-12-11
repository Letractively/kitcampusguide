package edu.kit.cm.kitcampusguide.access.evaluate;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import edu.kit.cm.kitcampusguide.service.user.MemberUserDetails;
import edu.kit.tm.cm.kitcampusguide.poiservice.Poi;

public class RoleOfMemberInGroupEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return true;
//    	boolean hasPermission = false;
//        if (authentication instanceof MemberUserDetails) {
//            MemberUserDetails user = (MemberUserDetails) authentication;
//            hasPermission = hasUserReadPermissionFor(targetDomainObject, user);
//        }
//        return hasPermission;
    }

    private boolean hasUserReadPermissionFor(Object targetDomainObject, MemberUserDetails user) {
        boolean hasPermission = false;
        if (targetDomainObject instanceof Poi) {
            Poi poi = (Poi) targetDomainObject;
            hasPermission = isPoiPublicOrHasUserReadPermission(poi, user);
        }
        return hasPermission;
    }

    private boolean isPoiPublicOrHasUserReadPermission(Poi poi, MemberUserDetails user) {
        boolean hasPermission;
        if (poi.isPublicly()) {
            hasPermission = true;
        } else {
            hasPermission = hasUserReadPermissionForPoi(user, poi);
        }
        return hasPermission;
    }

    private boolean hasUserReadPermissionForPoi(MemberUserDetails user, Poi poi) {
        boolean hasPermission = false;

        for (String groupId : poi.getGroupIds().getId()) {
            if (user.hasAdminRoleForGroupWithId(groupId) || user.hasUserRoleForGroupWithId(groupId)) {

                hasPermission = true;
            }
        }
        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        // Object should be loaded before authentication
        return false;
    }

}
