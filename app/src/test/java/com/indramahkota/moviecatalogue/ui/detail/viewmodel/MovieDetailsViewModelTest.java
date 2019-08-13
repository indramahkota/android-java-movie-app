package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private MovieDetailsViewModel movieDetailsViewModel;

    @Mock
    Observer<Resource<MovieEntity>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieDetailsViewModel = new MovieDetailsViewModel(movieCatalogueRepository);
        movieDetailsViewModel.movieDetail.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadMovieDetails(384018L)).thenReturn(null);
        assertNotNull(movieDetailsViewModel.movieDetail);
        assertTrue(movieDetailsViewModel.movieDetail.hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        //when(movieCatalogueRepository.loadMovieDetails(384018L)).thenReturn();
        movieDetailsViewModel.setMovieId(384018L);
        verify(observer).onChanged(Resource.loading(new MovieEntity()));
        verify(observer).onChanged(Resource.success(new MovieEntity()));
    }

    @Test
    public void testApiFetchDataError() {
        //when(movieCatalogueRepository.loadMovieDetails(0L)).thenReturn();
        movieDetailsViewModel.setMovieId(0L);
        verify(observer).onChanged(Resource.loading(new MovieEntity()));
        verify(observer).onChanged(Resource.error("", new MovieEntity()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        movieDetailsViewModel = null;
    }
}