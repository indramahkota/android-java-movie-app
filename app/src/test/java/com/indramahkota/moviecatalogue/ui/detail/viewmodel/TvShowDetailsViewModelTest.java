package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.ui.detail.datastate.TvShowResponseState;

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

public class TvShowDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Mock
    Observer<TvShowResponseState> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowDetailsViewModel = new TvShowDetailsViewModel(remoteRepository, SingleSchedulers.TEST_SCHEDULER);
        tvShowDetailsViewModel.getTvShowViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(remoteRepository.loadListTvShow()).thenReturn(null);
        assertNotNull(tvShowDetailsViewModel.getTvShowViewState());
        assertTrue(tvShowDetailsViewModel.getTvShowViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(remoteRepository.loadTvShowDetails(60735)).thenReturn(Single.just(new TvShowEntity()));
        tvShowDetailsViewModel.loadTvShowDetails(60735);
        verify(observer).onChanged(TvShowResponseState.LOADING_STATE);
        verify(observer).onChanged(TvShowResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(remoteRepository.loadTvShowDetails(0)).thenReturn(Single.error(new Throwable("Api error")));
        tvShowDetailsViewModel.loadTvShowDetails(0);
        verify(observer).onChanged(TvShowResponseState.LOADING_STATE);
        verify(observer).onChanged(TvShowResponseState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        tvShowDetailsViewModel = null;
    }
}