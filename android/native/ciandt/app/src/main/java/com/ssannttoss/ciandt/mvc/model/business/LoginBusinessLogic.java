package com.ssannttoss.ciandt.mvc.model.business;

import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.ciandt.service.auth.FacebookAuth;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.ioc.DependencyType;
import com.ssannttoss.framework.util.StringUtil;

import java.security.AccessControlException;

/**
 * Perform business logic and validations related to authentication.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class LoginBusinessLogic {
    private static final String FACEBOOK = "FACEBOOK";

    /**
     * Authenticate the user using a Facebook Service or allowing the use as a Guest
     * @param login The information about the user trying to log in
     * @return true if the authentication is done synchronously or false if it will be done by
     * a asynchronous service
     */
    public boolean login(Login login) {
        if (login == null || StringUtil.isEmpty(login.getUsername()) || StringUtil.isEmpty(login.getPassword())) {
            throw new AccessControlException("Invalid username and/or password");
        }

        if (FACEBOOK.equals(login.getAuthority())) {
            new Thread(new FacebookAuth(login.getUsername(), login.getPassword())).start();
            return false;
        }

        processLogin(login);
        return true;
    }

    /**
     * Stores the logged user in the "Session"
     * @param login
     */
    public void processLogin(Login login) {
        DependencyManager.getInstance().register(Login.class,
                String.class, DependencyType.SINGLETON, login != null ? login.getUsername() : "Guest");
    }
}
