package com.ssannttoss.android_challenge.mvc.model.logic;

/**
 * Known results of delete a saved address operation.
 * Created by ssannttoss on 1/21/2018.
 */

public enum DeleteMyAddressResult {
    /**
     * The address was successfully deleted.
     */
    SUCCESS,
    /**
     * The address can't be deleted due an unknown error.
     */
    UNKNOWN_ERROR,
    /**
     * The address can't be deleted because multiples address was found with the same id.
     */
    MULTIPLE_ADDRESS_FOUND,
    /**
     * The address can't be deleted because it is not saved.
     */
    NOT_FOUND,
    /**
     * The address can't be deleted because the object ID is invalid.
     */
    INVALID_ID,
}
