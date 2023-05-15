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

import java.util.Random;

public class SignUpActivityTest {

    @Rule
    public ActivityScenarioRule<SignUp_Activity>
            activityRule = new ActivityScenarioRule<>(SignUp_Activity.class);

    private View decorView;

    @Before
    public void loadDecorView() {
        activityRule.getScenario().onActivity(activity ->
                decorView = activity.getWindow().getDecorView());
    }
    @Test
    public void testCorrectCredentials() {
        String firstname= " abcd";
        String familyname= "    abcd";
        String email= " abcd" + "@gmail.com";
        String password= "  abcd";
        String age= "15";
        String address= "   abcd";
        Espresso.onView(withId(R.id.firstname)).perform(ViewActions.typeText(firstname));
        Espresso.onView(withId(R.id.familyname)).perform(ViewActions.typeText(familyname));
        Espresso.onView(withId(R.id.email)).perform(ViewActions.typeText(email));
        Espresso.onView(withId(R.id.password)).perform(ViewActions.typeText(password));
        Espresso.onView(withId(R.id.age)) .perform(ViewActions.typeText(age));
        Espresso.onView(withId(R.id.address)) .perform(ViewActions.typeText(address));

        Espresso.pressBack();
        onView(withId(R.id.signup_button)).perform(ViewActions.click());

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

            assertEquals(firstname, preferences.getString("first_name", ""));
            assertEquals(familyname, preferences.getString("family_name", ""));
            assertEquals(email,preferences.getString("email", ""));
            assertEquals(Integer.parseInt(age), preferences.getInt("age", 0));
            assertEquals(address, preferences.getString("address", ""));
        });
    }
}
