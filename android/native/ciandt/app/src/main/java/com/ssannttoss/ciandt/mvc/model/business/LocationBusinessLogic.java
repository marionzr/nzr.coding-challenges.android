package com.ssannttoss.ciandt.mvc.model.business;

import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.framework.service.CurrentLocationService;

/**
 * Perform business logic and validations related to the location.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class LocationBusinessLogic {
    /**
     * Checks if the location is valid
     * @param currentLocation The current location of the user
     * @return true if the location is valid.
     */
    public boolean isLocationValid(CurrentLocation currentLocation) {
        return currentLocation != null &&
                (Math.abs(currentLocation.getLatitude()) + Math.abs(currentLocation.getLongitude())) > 1;
    }

    /**
     * Starts a service to retrieve the current location using the GPS
     */
    public void requestCurrentLocation() {
        new Thread(new CurrentLocationService()).start();
    }
}
