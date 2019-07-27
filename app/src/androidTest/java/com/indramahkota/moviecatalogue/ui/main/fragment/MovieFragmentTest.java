package com.indramahkota.moviecatalogue.ui.main.fragment;

import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.testing.SingleFragmentActivity;
import com.indramahkota.moviecatalogue.utils.RecyclerViewItemCountAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MovieFragmentTest {

    @Rule
    public final ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private final MovieFragment movieFragment = new MovieFragment();

    @Before
    public void setUp() {
        activityRule.getActivity().setFragment(movieFragment);
    }

    @Test
    public void loadData() {
        onView(withId(R.id.rv_fragment_category)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_fragment_category)).check(new RecyclerViewItemCountAssertion(10));
    }
}