package com.ssannttoss.android_challenge.mvc.model.entity;

import android.support.annotation.NonNull;

import com.ssannttoss.framework.mvc.model.Entity;

/**
 * Represents a location saved by the user using the map view.
 *
 * Created by ssannttoss on 1/21/2018.
 */

public class MyAddress extends Entity<MyAddress> {
    private double latitude;
    private double longitude;
    private String featureName;

    /**
     * Constructor
     */
    public MyAddress() {
        super();
    }

    /**
     * Constructor
     * @param latitude
     * @param longitude
     * @param fetureName
     */
    public MyAddress(double latitude, double longitude, @NonNull String fetureName) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.featureName = fetureName;
    }

    /**
     * Constructor
     * @param id
     * @param latitude
     * @param longitude
     * @param fetureName
     */
    public MyAddress(long id, double latitude, double longitude, @NonNull String fetureName) {
        this(latitude, longitude, fetureName);
        setId(id);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String toString() {
        return String.format("Id: %d | FeatureName: %s | Location: %.6f %.6f", getId(), featureName, latitude, longitude);
    }

    @Override
    public MyAddress clone() {
        MyAddress clone = new MyAddress(latitude, longitude, featureName);
        clone.setId(getId());
        return clone;
    }
}
