package edu.kit.cm.kitcampusguide.service.security;

import org.springframework.security.core.context.SecurityContext;

/**
 * Offers access to spring security context, thus the context can be mocked for
 * tests.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface SecurityService {

    SecurityContext getSecurityContext();

    SecurityContext createEmptyContext();

    void setSecurityContext(SecurityContext context);
}