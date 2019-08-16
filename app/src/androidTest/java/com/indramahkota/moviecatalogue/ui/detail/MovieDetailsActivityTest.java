package com.indramahkota.moviecatalogue.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.EspressoIdlingResource;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieDetailsActivityTest {
    private final MovieEntity dummyMovie = FakeData.getMovieData(false);

    @Rule
    public ActivityTestRule<MovieDetailsActivity> activityRule = new ActivityTestRule<MovieDetailsActivity>(MovieDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, MovieDetailsActivity.class);
            result.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, dummyMovie.getId());
            return result;
        }
    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadData() {
        //menunggu 3 detik lagi baru ready
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.txt_title)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_title)).check(matches(withText(dummyMovie.getTitle())));

        //Rating bisa berubah-ubah setiap harinya
        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_rating)).check(matches(withText(String.valueOf(dummyMovie.getVoteAverage()))));

        //Tanggal juga berubah sesuai dengan format bahasa (disini saya menggunakan bahasa indonesia)
        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_release_date)).check(matches(withText(dummyMovie.getReleaseDate())));

        //Overview juga kemungkinan bisa berubah
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyMovie.getOverview())));
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }
}