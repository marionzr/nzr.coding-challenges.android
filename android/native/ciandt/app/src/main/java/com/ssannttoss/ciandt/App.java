package com.ssannttoss.ciandt;

import android.content.Context;
import android.util.Log;

import com.ssannttoss.ciandt.mvc.controller.LocationController;
import com.ssannttoss.ciandt.mvc.controller.LoginController;
import com.ssannttoss.ciandt.mvc.controller.WeatherController;
import com.ssannttoss.ciandt.mvc.view.LocationActView;
import com.ssannttoss.ciandt.mvc.view.LoginActView;
import com.ssannttoss.ciandt.mvc.view.WeatherActView;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.ioc.DependencyType;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.MvcApplication;

/**
 * Created by ssannttoss on Nov/03/2018.
 */
public class App extends MvcApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        final DependencyManager manager = DependencyManager.getInstance();
        LogManager.i(this, "Application created. Version: " + BuildConfig.VERSION_NAME);

        // Register the association of Views and Controllers
        manager.register(LoginActView.class, LoginController.class, DependencyType.TRANSIENT, null);
        manager.register(LocationActView.class, LocationController.class, DependencyType.TRANSIENT, null);
        manager.register(WeatherActView.class, WeatherController.class, DependencyType.TRANSIENT, null);
    }
}
