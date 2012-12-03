package edu.kit.cm.kitcampusguide.jaxws.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * A callback handler for webservices which loads the password for the
 * requesting user. Can be used for authentication.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class UserPasswordInCallbackHandler implements CallbackHandler {

    @Autowired
    private AuthenticationManager providerManager;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback passwordCallback = getFirstPasswordCallbackFromCallbacks(callbacks);

        if (StringUtils.hasText(passwordCallback.getIdentifier())) {

            authenticateUserToSpringSecurity(passwordCallback.getIdentifier(), passwordCallback.getPassword());

        }
    }

    private WSPasswordCallback getFirstPasswordCallbackFromCallbacks(Callback[] callbacks) {
        WSPasswordCallback passwordCallback = null;
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                passwordCallback = (WSPasswordCallback) callback;
                break;
            }
        }
        return passwordCallback;
    }

    private void authenticateUserToSpringSecurity(Object identifier, Object password) {
        Authentication authentication = providerManager.authenticate(new UsernamePasswordAuthenticationToken(
                identifier, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Used for setting provider manager through dependency injection.
     * 
     * @param providerManager
     */
    public void setProviderManager(AuthenticationManager providerManager) {
        this.providerManager = providerManager;
    }
}
