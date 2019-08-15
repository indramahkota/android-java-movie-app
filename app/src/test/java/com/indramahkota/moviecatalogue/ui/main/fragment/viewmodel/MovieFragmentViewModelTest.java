package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

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

public class MovieFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private MovieFragmentViewModel movieFragmentViewModel;

    @Mock
    Observer<Resource<DiscoverMovieResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieFragmentViewModel = new MovieFragmentViewModel(movieCatalogueRepository);
        movieFragmentViewModel.listDiscoverMovie.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadListMovie(1L)).thenReturn(FakeData.getListMovieLiveData());

        movieFragmentViewModel.loadMoreMovies(1L);

        Resource<DiscoverMovieResponse> discoverMovie = movieFragmentViewModel.listDiscoverMovie.getValue();

        verify(movieCatalogueRepository).loadListMovie(1L);
        verify(observer).onChanged(discoverMovie);

        assertNotNull(discoverMovie);
        assertNotNull(discoverMovie.data);
        assertEquals(10, discoverMovie.data.getResults().size());
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        movieFragmentViewModel = null;
    }
}