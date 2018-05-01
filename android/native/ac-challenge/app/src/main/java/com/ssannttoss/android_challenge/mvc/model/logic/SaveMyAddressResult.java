package com.ssannttoss.android_challenge.mvc.model.logic;

/**
 * Known results of saving address operation.
 * Created by ssannttoss on 1/21/2018.
 */

public enum SaveMyAddressResult {
    /**
     * The address was successfully saved.
     */
    SUCCESS,
    /**
     * Could no save the address by an unknown error. See log for more info.
     */
    FAIL,
    /**
     * The address can't be saved because feature name is invalid.
     */
    INVALID_FEATURE_NAME,
    /**
     * The address can't be saved because the location is invalid.
     */
    INVALID_LOCATION
}
