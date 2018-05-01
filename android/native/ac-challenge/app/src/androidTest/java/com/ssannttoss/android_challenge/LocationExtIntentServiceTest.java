package com.ssannttoss.android_challenge;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssannttoss.android_challenge.mvc.view.search.SearchActView;
import com.ssannttoss.android_challenge.service.LocationExtIntentService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocationExtIntentServiceTest {
    @Rule
    public ActivityTestRule<SearchActView> mActivityRule = new ActivityTestRule<>(SearchActView.class);
    private String locationName;
    private String packageName;

    @Before
    public void init() {
        locationName = "Belo Horizonte";
        packageName = BuildConfig.APPLICATION_ID;
    }

    @Test
    public void start_location_service_intent() {
        Intent intent = new Intent(mActivityRule.getActivity(), LocationExtIntentService.class);
        intent.putExtra(packageName + LocationExtIntentService.LOCATION_NAME_EXTRA, locationName);
        intent.putExtra(packageName + LocationExtIntentService.RECEIVER_EXTRA, new LocationServiceExtResultReceiver(null));
        mActivityRule.getActivity().startService(intent);
    }

    class LocationServiceExtResultReceiver extends ResultReceiver {
        public LocationServiceExtResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == LocationExtIntentService.SUCCESS_CODE) {
                // TODO: implement async testing
            } else {
                // TODO: implement async testing
            }
        }
    }
}
