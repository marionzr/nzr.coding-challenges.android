package com.ssannttoss.ciandt;

import android.content.ComponentName;
import android.os.SystemClock;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;


import android.support.test.rule.ActivityTestRule;

import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.ciandt.mvc.view.LocationActView;
import com.ssannttoss.ciandt.mvc.view.LoginActView;
import com.ssannttoss.ciandt.mvc.view.WeatherActView;
import com.ssannttoss.framework.ioc.DependencyManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginFacebookTest {
    @Rule
    public IntentsTestRule<LoginActView> mActivityRule = new IntentsTestRule<>(LoginActView.class);

    @Test
    public void loginFacebook_withUsernameOrPasswordEmpty_shouldDisplayError() {
        onView(withId(R.id.btnLogin)).perform(click());

        SystemClock.sleep(500); // TODO: change to IdlingResources

        onView(withText("Error")).check(matches(isDisplayed()));
        assertNull(DependencyManager.getInstance().get(Login.class));
    }

    @Test
    public void loginFacebook_withUsernameAndPasswordNotEmpty_shouldPresentLocationActView() {
        onView(withId(R.id.edtEmail)).perform(typeText("ssannttoss@gmail.com"));
        onView(withId(R.id.edtPassword)).perform(typeText("secret"));
        onView(withId(R.id.btnLogin)).perform(click());

        SystemClock.sleep(1000); // TODO: change to IdlingResources

        assertNotNull(DependencyManager.getInstance().get(Login.class));
        intended(hasComponent(new ComponentName(getTargetContext(), LocationActView.class)));

        onView(allOf(
                withId(android.support.design.R.id.snackbar_text),
                withText(getTargetContext().getString(R.string.logged_as, "ssannttoss@gmail.com"))))
                .check(matches(isDisplayed()));
    }
}
