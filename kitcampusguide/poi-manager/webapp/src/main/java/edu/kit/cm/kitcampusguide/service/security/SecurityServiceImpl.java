package edu.kit.cm.kitcampusguide.service.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 * Offers methods to access security information.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
@Repository
public class SecurityServiceImpl implements SecurityService {

    @Override
    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

}
