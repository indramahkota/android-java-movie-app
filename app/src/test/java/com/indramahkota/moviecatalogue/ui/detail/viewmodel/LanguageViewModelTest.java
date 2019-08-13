package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class LanguageViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private LanguageViewModel languageViewModel;

    @Mock
    Observer<Resource<List<LanguageEntity>>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        languageViewModel = new LanguageViewModel(movieCatalogueRepository);
        languageViewModel.getLanguages().observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadLanguage()).thenReturn(null);
        assertNotNull(languageViewModel.getLanguages());
        assertTrue(languageViewModel.getLanguages().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        //when(movieCatalogueRepository.loadLanguage()).thenReturn();
        languageViewModel.getLanguages();
        //verify(observer).onChanged(Resource.loading(new LanguageResponse()));
        //verify(observer).onChanged(Resource.success(languageViewModel.getLanguages().getValue().data));
    }

    @Test
    public void testApiFetchDataError() {
        //when(movieCatalogueRepository.loadLanguage()).thenReturn(Observable.error(new Throwable("Api error")));
        languageViewModel.getLanguages();
        //verify(observer).onChanged(Resource.loading(new LanguageResponse()));
        //verify(observer).onChanged(Resource.error("error", new LanguageResponse()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        languageViewModel = null;
    }
}