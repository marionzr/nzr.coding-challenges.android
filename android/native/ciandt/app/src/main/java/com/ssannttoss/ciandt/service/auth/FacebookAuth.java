package com.ssannttoss.ciandt.service.auth;

import com.ssannttoss.framework.event.ChangeEvent;
import com.ssannttoss.framework.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.security.AccessControlException;

public class FacebookAuth implements Runnable{
    private String username;
    private String password;

    public class Response {
        private String username;
        private String token;
        private Exception error;

        public Response(Exception error) {
            this.error = error;
        }

        public Response(String username, String token) {
            this.username = username;
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public String getToken() {
            return token;
        }

        public Exception getError() {
            return error;
        }
    }

    public final class FacebookAuthEvent<FacebookUser> extends ChangeEvent<FacebookUser> {
        public FacebookAuthEvent(FacebookUser user) {
            super(null, user, null);
        }
    }

    public FacebookAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            EventBus.getDefault()
                    .post(new FacebookAuth.FacebookAuthEvent<>(new Response(new AccessControlException("Invalid username and/or password"))));
        } else {
            EventBus.getDefault().post(new FacebookAuth.FacebookAuthEvent<>(new Response(username, "token")));
        }

    }
}
