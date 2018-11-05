package com.ssannttoss.ciandt.mvc.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssannttoss.framework.mvc.model.Entity;

public class CurrentLocation extends Entity<CurrentLocation> implements Parcelable {
    public static final Parcelable.Creator<CurrentLocation> CREATOR = new Parcelable.Creator<CurrentLocation>() {
        @Override
        public CurrentLocation createFromParcel(Parcel source) {
            return new CurrentLocation(source);
        }

        @Override
        public CurrentLocation[] newArray(int size) {
            return new CurrentLocation[size];
        }
    };

    private double latitude;
    private double longitude;

    public CurrentLocation() {
        super();
    }

    public CurrentLocation(double latitude, double longitude) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CurrentLocation(Parcel source) {
        readFromParcel(source);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public CurrentLocation clone() {
        return new CurrentLocation(latitude, longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
}
