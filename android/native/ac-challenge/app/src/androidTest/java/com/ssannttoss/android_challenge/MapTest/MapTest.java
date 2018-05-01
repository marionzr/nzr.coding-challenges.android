package com.ssannttoss.android_challenge.MapTest;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.mvc.view.map.MapActView;
import com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel;
import com.ssannttoss.android_challenge.mvc.view.search.SearchActView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapTest {
    private static final String MULTIPLE_RESULT_LOCATION = "Nova";
    private static final int SLEEP = 3000;
    @Rule
    public ActivityTestRule<SearchActView> mActivityRule = new ActivityTestRule<>(SearchActView.class);
    private String displayAllOnMap;
    private String noResults;

    @Before
    public void initExpectedString() {
        displayAllOnMap = mActivityRule.getActivity().getString(R.string.display_all_on_map);
        noResults = mActivityRule.getActivity().getString(R.string.no_results);
    }

    @Test
    public void open_from_display_multiple_results() {
        onView(withId(R.id.edt_location)).perform(typeText(MULTIPLE_RESULT_LOCATION));
        onView(withId(R.id.btn_search)).perform(click());
        SystemClock.sleep(SLEEP); //TODO: Improve
        onView(withId(R.id.txt_search_result)).perform(click());

        List<AddressViewModel> model = mActivityRule.getActivity().getModel();
        assertTrue(model.size() > 1);

        AddressViewModel addressViewModelSelected = model.get(0);

        for (AddressViewModel addressViewModel : mActivityRule.getActivity().getModel()) {
            addressViewModel.setSelected(addressViewModel == addressViewModelSelected); // checking reference
        }
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MapActView.class);
        intent.putExtra(BuildConfig.APPLICATION_ID + MapActView.ADDRESS_VIEW_DISPLAY_ALL_MODE, addressViewModelSelected == null);
        intent.putParcelableArrayListExtra(BuildConfig.APPLICATION_ID + MapActView.ADDRESS_VIEW_MODEL_EXTRA,
                new ArrayList<Parcelable>(model));
        mActivityRule.launchActivity(intent);

    }
}
