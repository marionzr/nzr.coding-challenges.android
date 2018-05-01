package com.ssannttoss.android_challenge.MapTest;


import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.mvc.view.search.SearchActView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowMapTest {
    @Rule
    public ActivityTestRule<SearchActView> mActivityRule = new ActivityTestRule<>(SearchActView.class);
    private String displayAllOnMap;
    private String noResults;
    private static final String NO_RESULT_LOCATION = "0000000000000";
    private static final String ONE_RESULT_LOCATION = "Rua Doutor Lunds, 618";
    private static final String MULTIPLE_RESULT_LOCATION = "Nova";
    private static final int SLEEP = 3000;

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

        //TODO: implement specific map view features
    }
}
