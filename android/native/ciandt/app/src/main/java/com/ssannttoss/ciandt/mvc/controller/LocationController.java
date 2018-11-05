package com.ssannttoss.ciandt.mvc.controller;

import android.location.Location;

import com.ssannttoss.ciandt.mvc.model.business.LocationBusinessLogic;
import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.controller.AbstractControllerExt;
import com.ssannttoss.framework.service.CurrentLocationService;

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
public class LocationController extends AbstractControllerExt<CurrentLocation> {
    public LocationController() {
        super();
        EventBus.getDefault().register(this);
    }

    /**
     * Handlers the view state changes or view requests
     * @param action The action to be performed
     * @param model The object used as a model for this request
     * @param extras Extra information not present in the Model attributes
     * @param responseListener A response listener for synchronous operations
     */
    @Override
    protected void onViewStateChanged(String action, CurrentLocation model, Map<String, Object> extras, ResponseListener<CurrentLocation> responseListener) {
        if (CommonActions.LOAD_DATA.equals(action)) {
            getCurrentLocation();
        } else if (CommonActions.ITEM_SELECTED.equals(action)) {
            if (new LocationBusinessLogic().isLocationValid(model)) {
                extras = new HashMap<>();
                extras.put(CurrentLocation.class.getName(), model);
                viewExt.navigate(WeatherController.class, extras);
            }
        }
    }

    private void getCurrentLocation() {
        LogManager.d(this, "Request current location");
        new LocationBusinessLogic().requestCurrentLocation();
    }

    /**
     * Handlers the notification from LocationService of a location change event
     * and notify the View.
     * @param event LocationChangedEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationChanged(CurrentLocationService.LocationChangedEvent<Location> event) {
        LogManager.i(this, "onLocationChanged: " + event.getData());
        viewExt.updateView(CommonActions.LOAD_DATA, new CurrentLocation(event.getData().getLatitude(), event.getData().getLongitude()), event.getExtras());
    }
}
