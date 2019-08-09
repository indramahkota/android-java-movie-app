package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverMovieResponseState;

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

public class MovieFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private MovieFragmentViewModel movieFragmentViewModel;

    @Mock
    Observer<DiscoverMovieResponseState> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieFragmentViewModel = new MovieFragmentViewModel(remoteRepository, SingleSchedulers.TEST_SCHEDULER);
        movieFragmentViewModel.getMovieViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(remoteRepository.loadListTvShow()).thenReturn(null);
        assertNotNull(movieFragmentViewModel.getMovieViewState());
        assertTrue(movieFragmentViewModel.getMovieViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(remoteRepository.loadListMovie()).thenReturn(Single.just(new DiscoverMovieResponse()));
        movieFragmentViewModel.loadMovie();
        verify(observer).onChanged(DiscoverMovieResponseState.LOADING_STATE);
        verify(observer).onChanged(DiscoverMovieResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(remoteRepository.loadListMovie()).thenReturn(Single.error(new Throwable("Api error")));
        movieFragmentViewModel.loadMovie();
        verify(observer).onChanged(DiscoverMovieResponseState.LOADING_STATE);
        verify(observer).onChanged(DiscoverMovieResponseState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        movieFragmentViewModel = null;
    }
}