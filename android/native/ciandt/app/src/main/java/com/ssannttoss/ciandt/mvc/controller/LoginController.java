package com.ssannttoss.ciandt.mvc.controller;

import com.ssannttoss.ciandt.mvc.model.business.LoginBusinessLogic;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.ciandt.service.auth.FacebookAuth;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.controller.AbstractControllerExt;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Handlers the requests from the View and notify the View in case of
 * response from other services.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class LoginController extends AbstractControllerExt<Login> {

    public LoginController() {
        super();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onViewStateChanged(String action, Login model, Map<String, Object> extras, ResponseListener<Login> responseListener) {
        switch (action) {
            case CommonActions.LOGIN:
                login(model, extras, responseListener);
                break;
        }
    }

    /**
     * Checks if the user is logging in as a Guest or using an account and perform
     * the appropriated routing.
     * @param model The object used as a model for this request
     * @param extras Extra information not present in the Model attributes
     * @param responseListener A response listener for synchronous operations
     */
    private void login(Login model, Map<String, Object> extras, ResponseListener<Login> responseListener) {
        try {
            if (model == null) {
                viewExt.navigate(WeatherController.class, extras);
                new LoginBusinessLogic().processLogin(null);
            } else if(new LoginBusinessLogic().login(model)) {
                viewExt.navigate(LocationController.class, extras);
            }
        } catch (Exception e) {
            LogManager.e(this, "login failed", e);
            responseListener.onResponse(e, model, null);
        }
    }

    /**
     * Handlers the notification from FacebookAuth of a authentication request
     * and notify the View.
     * @param event FacebookAuthEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFacebookAuthEvent(FacebookAuth.FacebookAuthEvent<FacebookAuth.Response> event) {
        if (event.getData().getError() == null) {
            LogManager.i(this, "Facebook user authenticated");
            Map<String, Object> extras = new HashMap<>();
            extras.put(Login.class.getName(), event.getData().getUsername());
            new LoginBusinessLogic().processLogin(new Login().withUsername(event.getData().getUsername()));
            viewExt.navigate(LocationController.class, extras);
        } else {
            String error = "Facebook Authentication Error";
            LogManager.e(this, error);
            viewExt.showException(error, event.getData().getError());
        }
    }
}
