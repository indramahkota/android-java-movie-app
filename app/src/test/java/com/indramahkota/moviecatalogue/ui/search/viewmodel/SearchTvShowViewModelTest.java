package com.indramahkota.moviecatalogue.ui.search.viewmodel;

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

public class SearchTvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private SearchTvShowViewModel searchViewModel;

    @Mock
    Observer<Resource<TvShowResponse>> tvShowObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchViewModel = new SearchTvShowViewModel(movieCatalogueRepository);
        searchViewModel.searchTvShow.observeForever(tvShowObserver);
    }

    @Test
    public void testApiFetchData() {
        Resource<TvShowResponse> discoverTvShow = FakeData.getResourceListTvShow();
        MutableLiveData<Resource<TvShowResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(discoverTvShow);

        when(movieCatalogueRepository.searchListTvShow("Test", 1L)).thenReturn(liveData);

        searchViewModel.setQuery("Test");
        searchViewModel.loadMoreMovies(1L);

        verify(tvShowObserver).onChanged(discoverTvShow);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        searchViewModel = null;
    }
}