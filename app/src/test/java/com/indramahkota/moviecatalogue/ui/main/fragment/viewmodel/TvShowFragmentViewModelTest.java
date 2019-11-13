package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovieCatalogueRepository movieCatalogueRepository;

    private TvShowFragmentViewModel tvShowFragmentViewModel;

    @Mock
    Observer<Resource<TvShowResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowFragmentViewModel = new TvShowFragmentViewModel(movieCatalogueRepository);
        tvShowFragmentViewModel.listDiscoverTvShow.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        Resource<TvShowResponse> discoverTvShow = FakeData.getResourceListTvShow();
        MutableLiveData<Resource<TvShowResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(discoverTvShow);

        when(movieCatalogueRepository.loadListTvShow(1L)).thenReturn(liveData);

        tvShowFragmentViewModel.loadMoreTvShows(1L);

        verify(observer).onChanged(discoverTvShow);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowFragmentViewModel = null;
    }
}