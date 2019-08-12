package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

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
    MovieCatalogueRepository movieCatalogueRepository;

    private LanguageViewModel languageViewModel;

    @Mock
    Observer<Resource<LanguageResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        languageViewModel = new LanguageViewModel(movieCatalogueRepository, ObservableSchedulers.TEST_SCHEDULER);
        languageViewModel.getLanguageViewState().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadLanguages()).thenReturn(null);
        assertNotNull(languageViewModel.getLanguageViewState());
        assertTrue(languageViewModel.getLanguageViewState().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(movieCatalogueRepository.loadLanguages()).thenReturn(Observable.just(new ArrayList<>()));
        languageViewModel.loadLanguages();
        verify(observer).onChanged(Resource.loading(new LanguageResponse()));
        verify(observer).onChanged(Resource.success(languageViewModel.getLanguageViewState().getValue().data));
    }

    @Test
    public void testApiFetchDataError() {
        when(movieCatalogueRepository.loadLanguages()).thenReturn(Observable.error(new Throwable("Api error")));
        languageViewModel.loadLanguages();
        verify(observer).onChanged(Resource.loading(new LanguageResponse()));
        verify(observer).onChanged(Resource.error("error", new LanguageResponse()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        languageViewModel = null;
    }
}