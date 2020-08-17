package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private MovieFragmentViewModel movieFragmentViewModel;

    @Mock
    Observer<Resource<MovieResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieFragmentViewModel = new MovieFragmentViewModel(movieCatalogueRepository);
        movieFragmentViewModel.listDiscoverMovie.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        Resource<MovieResponse> discoverMovie = FakeData.getResourceListMovie();
        MutableLiveData<Resource<MovieResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(discoverMovie);

        when(movieCatalogueRepository.loadListMovie(1L)).thenReturn(liveData);

        movieFragmentViewModel.loadMoreMovies(1L);

        verify(observer).onChanged(discoverMovie);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        movieFragmentViewModel = null;
    }
}