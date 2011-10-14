package edu.kit.cm.kitcampusguide.jaxws.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import edu.kit.cm.kitcampusguide.model.Member;
import edu.kit.cm.kitcampusguide.service.security.SecurityService;
import edu.kit.cm.kitcampusguide.service.user.MemberService;

/**
 * A callback handler for webservices which adds the users identifier and
 * password to the webservice request. Can be used for authentication.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class UserPasswordOutCallbackHandler implements CallbackHandler {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SecurityService securityService;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        final WSPasswordCallback passwordCallback = (WSPasswordCallback) callbacks[0];

        loadPasswordForUserAndSetToCallback(passwordCallback);
    }

    private void loadPasswordForUserAndSetToCallback(final WSPasswordCallback passwordCallback) {
        UserDetails userDetails = getIdentifierOfRequestingUser();
        Member requestingUser = memberService.getUser(userDetails.getUsername());

        passwordCallback.setIdentifier(requestingUser.getName());
        passwordCallback.setPassword(requestingUser.getPassword());
    }

    private UserDetails getIdentifierOfRequestingUser() {
        return (UserDetails) securityService.getSecurityContext().getAuthentication().getPrincipal();
    }

    /**
     * Used for setting security service through dependency injection.
     * 
     * @param securityService
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Used for setting member service through dependency injection.
     * 
     * @param memberService
     */
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}
