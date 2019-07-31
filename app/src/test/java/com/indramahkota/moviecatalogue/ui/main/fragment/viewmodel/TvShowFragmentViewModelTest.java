package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.ui.main.fragment.state.TvShowViewState;

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

public class TvShowFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private TvShowFragmentViewModel tvShowFragmentViewModel;

    @Mock
    Observer<TvShowViewState> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowFragmentViewModel = new TvShowFragmentViewModel(remoteRepository, SingleSchedulers.TEST_SCHEDULER);
        tvShowFragmentViewModel.getTvShowViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(remoteRepository.loadListTvShow()).thenReturn(null);
        assertNotNull(tvShowFragmentViewModel.getTvShowViewState());
        assertTrue(tvShowFragmentViewModel.getTvShowViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(remoteRepository.loadListTvShow()).thenReturn(Single.just(new DiscoverTvShowResponse()));
        tvShowFragmentViewModel.loadTvShow();
        verify(observer).onChanged(TvShowViewState.LOADING_STATE);
        verify(observer).onChanged(TvShowViewState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(remoteRepository.loadListTvShow()).thenReturn(Single.error(new Throwable("Api error")));
        tvShowFragmentViewModel.loadTvShow();
        verify(observer).onChanged(TvShowViewState.LOADING_STATE);
        verify(observer).onChanged(TvShowViewState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        tvShowFragmentViewModel = null;
    }
}