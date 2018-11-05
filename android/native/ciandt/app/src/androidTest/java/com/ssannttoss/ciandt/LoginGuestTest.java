package com.ssannttoss.ciandt;

import android.content.ComponentName;
import android.os.SystemClock;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.ciandt.mvc.view.LocationActView;
import com.ssannttoss.ciandt.mvc.view.LoginActView;
import com.ssannttoss.ciandt.mvc.view.WeatherActView;
import com.ssannttoss.framework.ioc.DependencyManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginGuestTest {
    @Rule
    public IntentsTestRule<LoginActView> mActivityRule = new IntentsTestRule<>(LoginActView.class);

    @Test
    public void loginGuest_shouldPresentWeatheActivity() {
        onView(withId(R.id.btnGuest)).perform(click());

        intended(hasComponent(new ComponentName(getTargetContext(), WeatherActView.class)));
    }
}
