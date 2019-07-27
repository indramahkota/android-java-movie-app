package com.indramahkota.moviecatalogue.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.model.DiscoverTvShow;
import com.indramahkota.moviecatalogue.utils.FakeDataDummy;

import org.junit.After;
import org.junit.Rule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

class TvShowDetailsActivityTest {
    private final DiscoverTvShow dummyTvShow = FakeDataDummy.generateDummyTvShows().get(0);

    @Rule
    public ActivityTestRule<TvShowDetailsActivity> activityRule = new ActivityTestRule<TvShowDetailsActivity>(TvShowDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, TvShowDetailsActivity.class);
            result.putExtra(TvShowDetailsActivity.EXTRA_TV_SHOW, dummyTvShow);
            return result;
        }
    };

    @After
    public void tearDown() {
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_title)).check(matches(withText(dummyTvShow.getName())));

        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_rating)).check(matches(withText(String.valueOf(dummyTvShow.getVoteAverage()))));

        onView(withId(R.id.txt_language)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_language)).check(matches(withText(dummyTvShow.getLanguage())));

        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_release_date)).check(matches(withText(dummyTvShow.getFirstAirDate())));

        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyTvShow.getOverview())));
    }
}