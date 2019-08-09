package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverMovieResponseState;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverTvShowResponseState;

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

public class SearchViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private SearchViewModel searchViewModel;

    @Mock
    Observer<DiscoverMovieResponseState> movieObserver;

    @Mock
    Observer<DiscoverTvShowResponseState> tvShowObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchViewModel = new SearchViewModel(remoteRepository, SingleSchedulers.TEST_SCHEDULER);
        searchViewModel.getMovieViewState().observeForever(movieObserver);
        searchViewModel.getTvShowViewState().observeForever(tvShowObserver);
    }

    @Test
    public void testApiFetchDataMovie() {
        when(remoteRepository.searchListMovie("Test")).thenReturn(null);
        assertNotNull(searchViewModel.getMovieViewState());
        assertTrue(searchViewModel.getMovieViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataMovieSuccess() {
        when(remoteRepository.searchListMovie("Test")).thenReturn(Single.just(new DiscoverMovieResponse()));
        searchViewModel.searchMovie("Test");
        verify(movieObserver).onChanged(DiscoverMovieResponseState.LOADING_STATE);
        verify(movieObserver).onChanged(DiscoverMovieResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataMovieError() {
        when(remoteRepository.searchListMovie("Test")).thenReturn(Single.error(new Throwable("Api error")));
        searchViewModel.searchMovie("Test");
        verify(movieObserver).onChanged(DiscoverMovieResponseState.LOADING_STATE);
        verify(movieObserver).onChanged(DiscoverMovieResponseState.ERROR_STATE);
    }

    @Test
    public void testApiFetchDataTvShow() {
        when(remoteRepository.searchListTvShow("Test")).thenReturn(null);
        assertNotNull(searchViewModel.getMovieViewState());
        assertTrue(searchViewModel.getMovieViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataTvShowSuccess() {
        when(remoteRepository.searchListTvShow("Test")).thenReturn(Single.just(new DiscoverTvShowResponse()));
        searchViewModel.searchTvShow("Test");
        verify(tvShowObserver).onChanged(DiscoverTvShowResponseState.LOADING_STATE);
        verify(tvShowObserver).onChanged(DiscoverTvShowResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataTvShowError() {
        when(remoteRepository.searchListTvShow("Test")).thenReturn(Single.error(new Throwable("Api error")));
        searchViewModel.searchTvShow("Test");
        verify(tvShowObserver).onChanged(DiscoverTvShowResponseState.LOADING_STATE);
        verify(tvShowObserver).onChanged(DiscoverTvShowResponseState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        searchViewModel = null;
    }
}