package com.indramahkota.moviecatalogue.ui.search.viewmodel;

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

public class SearchTvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private SearchTvShowViewModel searchViewModel;

    @Mock
    Observer<Resource<DiscoverTvShowResponse>> tvShowObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchViewModel = new SearchTvShowViewModel(movieCatalogueRepository);
        searchViewModel.searchTvShow.observeForever(tvShowObserver);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.searchListTvShow("Test", 1L)).thenReturn(FakeData.getListTvShowLiveData());

        searchViewModel.setQuery("Test");
        searchViewModel.loadMoreMovies(1L);

        Resource<DiscoverTvShowResponse> discoverTvShow = searchViewModel.searchTvShow.getValue();

        verify(movieCatalogueRepository).searchListTvShow("Test", 1L);
        verify(tvShowObserver).onChanged(discoverTvShow);

        assertNotNull(discoverTvShow);
        assertNotNull(discoverTvShow.data);
        assertEquals(10, discoverTvShow.data.getResults().size());
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        searchViewModel = null;
    }
}