package edu.kit.cm.kitcampusguide.service.security;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityService {

    SecurityContext getSecurityContext();

}