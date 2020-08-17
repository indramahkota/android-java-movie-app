package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        Resource<MovieEntity> movieEntity = FakeData.getResourceMovie();
        MutableLiveData<Resource<MovieEntity>> liveData = new MutableLiveData<>();
        liveData.setValue(movieEntity);

        when(movieCatalogueRepository.loadMovieDetails(1L)).thenReturn(liveData);

        movieDetailsViewModel.setMovieId(1L);

        verify(observer).onChanged(movieEntity);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        movieDetailsViewModel = null;
    }
}