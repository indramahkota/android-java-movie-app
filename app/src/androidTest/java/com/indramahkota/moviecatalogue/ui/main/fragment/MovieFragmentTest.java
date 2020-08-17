package com.indramahkota.moviecatalogue.ui.main.fragment;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MovieFragmentTest {
    @Rule
    public final ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    //private MutableLiveData<Resource<DiscoverMovieResponse>> data = new MutableLiveData<>();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());

        /*MovieFragmentViewModel viewModel = mock(MovieFragmentViewModel.class);
        viewModel.listDiscoverMovie = data;
        doNothing().when(viewModel).loadMoreMovies(any());*/

        MovieFragment movieFragment = new MovieFragment();
        //movieFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityRule.getActivity().setFragment(movieFragment);
    }

    @Test
    public void loadData() {
        /*List<MovieEntity> expectedData = getListMovie(false);
        DiscoverMovieResponse discoverMovieResponse = new DiscoverMovieResponse();
        discoverMovieResponse.setResults(expectedData);
        data.postValue(Resource.success(discoverMovieResponse));*/

        //menunggu 3 detik lagi baru ready
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.rv_fragment_category)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_fragment_category)).check(new RecyclerViewItemCountAssertion(20));
        onView(withId(R.id.rv_fragment_category)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }
}