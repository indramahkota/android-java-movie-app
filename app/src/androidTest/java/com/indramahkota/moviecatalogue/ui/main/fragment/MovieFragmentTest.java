package com.indramahkota.moviecatalogue.ui.main.fragment;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.EspressoIdlingResource;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.testing.SingleFragmentActivity;
import com.indramahkota.moviecatalogue.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
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
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
        activityRule.getActivity().setFragment(movieFragment);
    }

    @Test
    public void loadData() {
        //Data awal jumlahnya tergantung hasil pencarian
        //minimal tidak ada, maksimal data awal 20 untuk 1 page jika hasil ada
        onView(withId(R.id.rv_fragment_category)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_fragment_category)).check(new RecyclerViewItemCountAssertion(20));
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }
}