package com.indramahkota.moviecatalogue.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indramahkota.moviecatalogue.EspressoIdlingResource;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
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

public class TvShowDetailsActivityTest {
    private final TvShowEntity dummyTvShow = FakeData.getTvShowData(false);

    @Rule
    public ActivityTestRule<TvShowDetailsActivity> activityRule = new ActivityTestRule<TvShowDetailsActivity>(TvShowDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, TvShowDetailsActivity.class);
            result.putExtra(TvShowDetailsActivity.EXTRA_TV_SHOW_ID, dummyTvShow.getId());
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
        onView(withId(R.id.txt_title)).check(matches(withText(dummyTvShow.getName())));

        //Rating bisa berubah-ubah
        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_rating)).check(matches(withText(String.valueOf(dummyTvShow.getVoteAverage()))));

        //Tanggal juga berubah sesuai dengan format bahasa (disini saya menggunakan bahasa indonesia)
        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_release_date)).check(matches(withText(dummyTvShow.getFirstAirDate())));

        //Overview juga kemungkinan bisa berubah
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyTvShow.getOverview())));
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }
}