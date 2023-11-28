package com.example.gymapp.activity;
import android.content.Context;
import android.util.Log;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import com.example.gymapp.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UI_tests {
    @Rule
    public ActivityScenarioRule<SignUpActivity> signUpRule = new ActivityScenarioRule<>(SignUpActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.gymapp", appContext.getPackageName());
    }
    @Test
    public void testSignUpHeaderLabel() {
        String expected = "Sign Up";
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_text))
                .check(matches(withText(expected)));
    }
    @Test
    public void testBackButtonClickDisplayText() {
        String expected = "Back";
        Espresso.onView(ViewMatchers.withId(R.id.back_button5)).check(matches(isDisplayed())).check(matches(withText(expected))).perform(ViewActions.click());
    }
    @Test
    public void testEmailKeyboard() {
        String inputText = "Paul@gmail.com";
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(inputText), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.email_input)).check(matches(withText(inputText)));
    }

}

