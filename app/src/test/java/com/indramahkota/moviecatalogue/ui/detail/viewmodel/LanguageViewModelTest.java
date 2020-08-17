package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;
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
    }

    @Test
    public void testApiFetchData() {
        Resource<List<LanguageEntity>> languageResources = FakeData.getResourceLanguages();
        MutableLiveData<Resource<List<LanguageEntity>>> liveData = new MutableLiveData<>();
        liveData.setValue(languageResources);

        when(movieCatalogueRepository.loadLanguage()).thenReturn(liveData);

        languageViewModel.getLanguages().observeForever(observer);

        verify(observer).onChanged(languageResources);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        languageViewModel = null;
    }
}