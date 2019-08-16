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

public class TvShowFragmentTest {
    @Rule
    public final ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    //private MutableLiveData<Resource<DiscoverTvShowResponse>> data = new MutableLiveData<>();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());

        /*TvShowFragmentViewModel viewModel = mock(TvShowFragmentViewModel.class);
        viewModel.listDiscoverTvShow = data;
        doNothing().when(viewModel).loadMoreTvShows(any());*/

        TvShowFragment tvShowFragment = new TvShowFragment();
        //tvShowFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityRule.getActivity().setFragment(tvShowFragment);
    }

    @Test
    public void loadData() {
        /*List<TvShowEntity> expectedData = getListTvShow(false);
        DiscoverTvShowResponse discoverTvShowResponse = new DiscoverTvShowResponse();
        discoverTvShowResponse.setResults(expectedData);
        data.postValue(Resource.success(discoverTvShowResponse));*/

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