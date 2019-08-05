package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.LanguageResponseState;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LanguageViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    RemoteRepository remoteRepository;

    private LanguageViewModel languageViewModel;

    @Mock
    Observer<LanguageResponseState> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        languageViewModel = new LanguageViewModel(remoteRepository, ObservableSchedulers.TEST_SCHEDULER);
        languageViewModel.getLanguageViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(remoteRepository.loadLanguages()).thenReturn(null);
        assertNotNull(languageViewModel.getLanguageViewState());
        assertTrue(languageViewModel.getLanguageViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(remoteRepository.loadLanguages()).thenReturn(Observable.just(new ArrayList<>()));
        languageViewModel.loadLanguages();
        verify(observer).onChanged(LanguageResponseState.LOADING_STATE);
        verify(observer).onChanged(LanguageResponseState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(remoteRepository.loadLanguages()).thenReturn(Observable.error(new Throwable("Api error")));
        languageViewModel.loadLanguages();
        verify(observer).onChanged(LanguageResponseState.LOADING_STATE);
        verify(observer).onChanged(LanguageResponseState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        remoteRepository = null;
        languageViewModel = null;
    }
}