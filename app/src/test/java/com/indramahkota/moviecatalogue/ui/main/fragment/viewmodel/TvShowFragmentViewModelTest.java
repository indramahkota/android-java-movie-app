package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private TvShowFragmentViewModel tvShowFragmentViewModel;

    @Mock
    Observer<Resource<DiscoverTvShowResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowFragmentViewModel = new TvShowFragmentViewModel(movieCatalogueRepository);
        tvShowFragmentViewModel.listDiscoverTvShow.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadListTvShow(1L)).thenReturn(FakeData.getListTvShowLiveData());

        tvShowFragmentViewModel.loadMoreTvShows(1L);

        Resource<DiscoverTvShowResponse> discoverTvShow = tvShowFragmentViewModel.listDiscoverTvShow.getValue();

        verify(movieCatalogueRepository).loadListTvShow(1L);
        verify(observer).onChanged(discoverTvShow);

        assertNotNull(discoverTvShow);
        assertNotNull(discoverTvShow.data);
        assertEquals(10, discoverTvShow.data.getResults().size());
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowFragmentViewModel = null;
    }
}