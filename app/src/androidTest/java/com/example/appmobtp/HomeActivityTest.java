package com.example.appmobtp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    // This method is called before each test method to set up the test environment
    @Before
    public void setUp() {
        // Launch the activity and set the shared preferences
        ActivityScenario<FoodMenuActivity> activityScenario =
                ActivityScenario.launch(FoodMenuActivity.class);

        activityScenario.onActivity(activity -> {
            SharedPreferences.Editor editor = PreferenceManager.
                    getDefaultSharedPreferences(activity).edit();

            editor.putString("first_name", "a");
            editor.putString("family_name", "b");
            editor.apply();
        });
    }

    // Test the welcome message displayed in the activity
    @Test
    public void testFirstNameAndFamilyName() {
        // Launch the activity and get the welcome message TextView
        ActivityScenario<FoodMenuActivity> activityScenario =
                ActivityScenario.launch(FoodMenuActivity.class);

        activityScenario.onActivity(activity -> {
            TextView welcomeTextView =
                    activity.findViewById(R.id.textView);

            // Check that the message is "Hello John Doe"
            String expected = "Hello a b";
            String actual = welcomeTextView.getText().toString();
            assertEquals(expected, actual);
        });
    }

    // Test the shared preferences values set in the setUp method
    @Test
    public void testSharedPreferences() {
        // Launch the activity and get the shared preferences values
        ActivityScenario<FoodMenuActivity> activityScenario =
                ActivityScenario.launch(FoodMenuActivity.class);

        activityScenario.onActivity(activity -> {
            SharedPreferences preferences = PreferenceManager.
                    getDefaultSharedPreferences(activity);

            String firstName = preferences.getString("first_name", "");
            String familyName = preferences.getString("family_name", "");
            // Check that the values are as expected ("John" and "Doe")
            String expected = "a";
            String actual = firstName;
            assertEquals(expected, actual);
            expected = "b";
            actual = familyName;
            assertEquals(expected, actual);
        });
    }

}
