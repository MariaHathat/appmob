package com.example.appmobtp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<Login_Activity>
            activityRule = new ActivityScenarioRule<>(Login_Activity.class);

    private View decorView;

    @Before
    public void loadDecorView() {
        activityRule.getScenario().onActivity(activity ->
                decorView = activity.getWindow().getDecorView());
    }

    @Test
    public void testCorrectCredentials() {
        Espresso.onView(withId(R.id.login_email))
                .perform(ViewActions.typeText("a@gmail.com"));

        onView(withId(R.id.login_password))
                .perform(ViewActions.typeText("f"));
        Espresso.pressBack();
        onView(withId(R.id.login_button)).perform(ViewActions.click());

        // Check if user info is saved to shared preferences
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if HomeActivity is launched after successful login
        onView(withId(R.id.textView))
                .check(ViewAssertions.matches(isDisplayed()));

        // Assert that the HomeActivity is started
        ActivityScenario<FoodMenuActivity>
                scenario = ActivityScenario.launch(FoodMenuActivity.class);

        scenario.onActivity(activity -> {
            // Assert that the user information is saved to SharedPreferences
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(activity);

            assertEquals("b", preferences.getString("family_name", ""));
            assertEquals("a", preferences.getString("first_name", ""));
            assertEquals("a@gmail.com",preferences.getString("email", ""));
            assertEquals(1, preferences.getInt("age", 0));
            assertEquals("ff", preferences.getString("address", ""));
        });
    }

    @Test
    public void testIncorrectCredentials() {
        onView(withId(R.id.login_email)).perform(
                ViewActions.typeText("incorrect@example.com"));

        onView(withId(R.id.login_password)).perform(
                ViewActions.typeText("another_password"));

        Espresso.pressBack();
        onView(withId(R.id.login_button)).perform(ViewActions.click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if error message is displayed after unsuccessful login
        activityRule.getScenario().onActivity(activity -> {
            assertEquals("error", activity.getLastStatus());
        });
    }

}
