package com.indramahkota.moviecatalogue.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.model.DiscoverMovie;
import com.indramahkota.moviecatalogue.utils.FakeDataDummy;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieDetailsActivityTest {
    private final DiscoverMovie dummyMovie = FakeDataDummy.generateDummyMovies().get(0);

    @Rule
    public ActivityTestRule<MovieDetailsActivity> activityRule = new ActivityTestRule<MovieDetailsActivity>(MovieDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, MovieDetailsActivity.class);
            result.putExtra(MovieDetailsActivity.EXTRA_MOVIE, dummyMovie);
            return result;
        }
    };

    @Test
    public void loadData() {
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_title)).check(matches(withText(dummyMovie.getTitle())));

        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_rating)).check(matches(withText(String.valueOf(dummyMovie.getVoteAverage()))));

        onView(withId(R.id.txt_language)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_language)).check(matches(withText(dummyMovie.getLanguage())));

        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_release_date)).check(matches(withText(dummyMovie.getReleaseDate())));

        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyMovie.getOverview())));
    }
}