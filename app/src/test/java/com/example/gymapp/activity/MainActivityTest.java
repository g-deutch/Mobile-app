package com.example.gymapp.activity;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;

import com.example.gymapp.R;
import com.example.gymapp.activity.MainActivity;
import com.google.firebase.FirebaseApp;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.RobolectricTestRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest extends TestCase {

    @Before
    public void setup() throws IllegalAccessException, InstantiationException {
        FirebaseApp.initializeApp(getApplicationContext());
    }
    @Test
    public void clickingSignUp_shouldStartSignUpActivity() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            MainActivity activity = controller.get();

            activity.findViewById(R.id.back_button7).performClick();
            Intent expectedIntent = new Intent(activity, SignUpActivity.class);
            Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        }
    }
    @Test
    public void signInTestFalse(){
        assertFalse(MainActivity.signIn("", "", new ArrayList<Map<String, Object>>()));
    }
    @Test
    public void SignInTestTrue(){
        Map<String, Object> user = new HashMap<>();
        user.put("username", "gdeutch");
        user.put("password", "gdeutch2");
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        users.add(user);
        assertTrue(MainActivity.signIn("gdeutch", "gdeutch2", users));
    }
}