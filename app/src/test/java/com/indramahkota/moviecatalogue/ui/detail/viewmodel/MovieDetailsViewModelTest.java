package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.MovieResponseState;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private MovieDetailsViewModel movieDetailsViewModel;

    @Mock
    Observer<MovieResponseState> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieDetailsViewModel = new MovieDetailsViewModel(remoteRepository, SingleSchedulers.TEST_SCHEDULER);
        movieDetailsViewModel.getMovieViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(remoteRepository.loadMovieDetails(384018)).thenReturn(null);
        assertNotNull(movieDetailsViewModel.getMovieViewState());
        assertTrue(movieDetailsViewModel.getMovieViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(remoteRepository.loadMovieDetails(384018)).thenReturn(Single.just(new MovieEntity()));
        movieDetailsViewModel.loadMovieDetails(384018);
        verify(observer).onChanged(MovieResponseState.LOADING_STATE);
        verify(observer).onChanged(MovieResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(remoteRepository.loadMovieDetails(0)).thenReturn(Single.error(new Throwable("Api error")));
        movieDetailsViewModel.loadMovieDetails(0);
        verify(observer).onChanged(MovieResponseState.LOADING_STATE);
        verify(observer).onChanged(MovieResponseState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        movieDetailsViewModel = null;
    }
}