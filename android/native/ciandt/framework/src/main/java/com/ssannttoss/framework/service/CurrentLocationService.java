package com.ssannttoss.framework.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.ssannttoss.framework.BuildConfig;
import com.ssannttoss.framework.event.ChangeEvent;
import com.ssannttoss.framework.ioc.DependencyManager;

import org.greenrobot.eventbus.EventBus;

public class CurrentLocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, Runnable {
    private final static int UPDATE_INTERVAL = 10000; // 10 sec
    private final static int FASTEST_INTERVAL = 5000; // 5 sec
    private final static int DISPLACEMENT = 10; // 10 meters

    private final String packageName;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Context context;

    public CurrentLocationService() {
        packageName = BuildConfig.APPLICATION_ID;
        context = DependencyManager.getInstance().get(Context.class);
    }

    @Override
    public void run() {
        client = buildGoogleClient();
        locationRequest = buildLocationRequest();

        if (!client.isConnected()) {
            client.connect();
        }
    }

    /**
     * Builds the client of {@link GoogleApiClient}
     */
    private GoogleApiClient buildGoogleClient() {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setMaxWaitTime(2 * UPDATE_INTERVAL);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        final int accessFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        final int accessCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocation == PackageManager.PERMISSION_GRANTED && accessCoarseLocation == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, new MyLocationCallback(), null);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class MyLocationCallback extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            EventBus.getDefault().post(new LocationChangedEvent<>(locationResult.getLastLocation()));
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            final boolean locationAvailable = locationAvailability.isLocationAvailable();
            if (locationAvailable) {
                EventBus.getDefault().post(new LocationChangedEvent<>(requestPosition()));
            }
        }
    }

    private Location requestPosition() {
        final int accessFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        final int accessCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocation == PackageManager.PERMISSION_GRANTED && accessCoarseLocation == PackageManager.PERMISSION_GRANTED) {
            return LocationServices.FusedLocationApi.getLastLocation(client);
        }

        return null;
    }

    public final class LocationChangedEvent<Location> extends ChangeEvent<Location> {
        LocationChangedEvent(Location model) {
            super(null, model, null);
        }
    }
}
