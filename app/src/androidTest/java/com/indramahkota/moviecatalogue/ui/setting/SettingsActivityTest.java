package com.indramahkota.moviecatalogue.ui.setting;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SettingsActivityTest {
    @Rule
    public final ActivityTestRule<SettingsActivity> activityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void toSettingsActivityTest() {
        onView(withId(R.id.card_change_lang)).check(matches(isDisplayed()));
        onView(withId(R.id.card_change_lang)).perform(ViewActions.click());
    }
}