package com.ssannttoss.android_challenge.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.service.geocode.GeocodeAddress;
import com.ssannttoss.android_challenge.service.geocode.GeocodeResult;
import com.ssannttoss.framework.logging.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ssannttoss on 1/21/2018.
 */

public class LocationExtIntentService extends IntentService {
    /**
     * Location to search.
     * <p>
     * String
     */
    public static final String LOCATION_NAME_EXTRA = ".LOCATION_NAME_EXTRA";
    /**
     * Max number of results from a Native Api call.
     * <p>
     * Integer
     */
    public static final String MAX_RESULT_EXTRA = ".MAX_RESULT_EXTRA";
    /**
     * ResultReceiver instance to received the result of Geocoding.
     * <p></p>
     * ResultReceiver
     */
    public static final String RECEIVER_EXTRA = ".RECEIVER_EXTRA";

    /**
     * ArrayList<Address> sent as result.
     * <p>
     * ArrayList<Address>
     */
    public static final String RESULT_DATA_EXTRA = ".RESULT_DATA_EXTA";
    /**
     * True for REST API, false for Native API.
     * <p>
     * Boolean
     */
    public static final String API_SOURCE_EXTRA = ".API_SOURCE_EXTRA";

    public static final int ERROR_CODE = 500;
    public static final int SUCCESS_CODE = 200;

    private final String packageName;
    protected ResultReceiver resultReceiver;
    private boolean useRESTApi = false;

    public LocationExtIntentService() {

        super("LocationExtIntentService");
        packageName = BuildConfig.APPLICATION_ID;
    }

    public ArrayList<Address> getAddressFromGoogleNativeApi(String locationName, int maxResults) throws IOException {
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        final ArrayList<Address> addresses = new ArrayList<>(geocoder.getFromLocationName(locationName, maxResults));
        return addresses;
    }

    public void getAddressFromGoogleRestApi(String locationName) throws IOException {
        GsonRequest<GeocodeResult> request = new GsonRequest<>(
                getApplicationContext().getString(R.string.google_maps_api_url, locationName.replace(" ", "%20"),
                        getApplicationContext().getString(R.string.google_maps_api_key)),
                GeocodeResult.class, null,
                new Response.Listener<GeocodeResult>() {
                    @Override
                    public void onResponse(GeocodeResult response) {
                        final ArrayList<Address> addresses = new ArrayList<>();

                        for (GeocodeAddress geoCodeAddress : response.getResults()) {
                            if (TextUtils.isEmpty(geoCodeAddress.getFormatted_address())) {
                                continue;
                            }

                            final Address address = new Address(Locale.ENGLISH);
                            address.setFeatureName(geoCodeAddress.getFormatted_address());
                            address.setLatitude(geoCodeAddress.getGeometry().getLocation().getLat());
                            address.setLongitude(geoCodeAddress.getGeometry().getLocation().getLng());
                            addresses.add(address);
                        }

                        sendResult(SUCCESS_CODE, null, addresses);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogManager.e(this, error.getMessage(), error);
                        sendResult(ERROR_CODE, error.getMessage(), null);
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String locationName = intent.getStringExtra(packageName + LOCATION_NAME_EXTRA);
        final int maxResults = intent.getIntExtra(packageName + MAX_RESULT_EXTRA, 100);
        resultReceiver = intent.getParcelableExtra(packageName + RECEIVER_EXTRA);
        useRESTApi = intent.getBooleanExtra(packageName + API_SOURCE_EXTRA, true);

        final ArrayList<Address> addresses;

        try {
            if (useRESTApi) {
                getAddressFromGoogleRestApi(locationName);
            } else {
                addresses = getAddressFromGoogleNativeApi(locationName, maxResults);
                sendResult(SUCCESS_CODE, null, addresses);
            }
        } catch (IOException e) {
            LogManager.e(this, e.getMessage(), e);
            sendResult(ERROR_CODE, e.getMessage(), null);
        }
    }

    /**
     * Sends the result for the caller.
     *
     * @param resultCode
     * @param error
     * @param addresses
     */
    private void sendResult(int resultCode, String error, ArrayList<Address> addresses) {
        if (resultReceiver != null) {
            final Bundle bundle = new Bundle();

            if (resultCode == SUCCESS_CODE) {
                bundle.putParcelableArrayList(packageName + RESULT_DATA_EXTRA, addresses);
            } else {
                bundle.putString(packageName + RESULT_DATA_EXTRA, error);
            }

            resultReceiver.send(resultCode, bundle);
        }
    }
}
