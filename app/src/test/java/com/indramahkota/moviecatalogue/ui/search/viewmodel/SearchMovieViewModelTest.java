package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
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

public class SearchMovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private SearchMovieViewModel searchViewModel;

    @Mock
    Observer<Resource<DiscoverMovieResponse>> movieObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchViewModel = new SearchMovieViewModel(movieCatalogueRepository);
        searchViewModel.searchMovie.observeForever(movieObserver);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.searchListMovie("Test", 1L)).thenReturn(FakeData.getListMovieLiveData());

        searchViewModel.setQuery("Test");
        searchViewModel.loadMoreMovies(1L);

        Resource<DiscoverMovieResponse> discoverMovie = searchViewModel.searchMovie.getValue();

        verify(movieCatalogueRepository).searchListMovie("Test", 1L);
        verify(movieObserver).onChanged(discoverMovie);

        assertNotNull(discoverMovie);
        assertNotNull(discoverMovie.data);
        assertEquals(10, discoverMovie.data.getResults().size());
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        searchViewModel = null;
    }
}