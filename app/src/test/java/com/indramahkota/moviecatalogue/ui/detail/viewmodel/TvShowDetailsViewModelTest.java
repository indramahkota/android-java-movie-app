package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.utils.FakeData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Mock
    Observer<Resource<TvShowEntity>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowDetailsViewModel = new TvShowDetailsViewModel(movieCatalogueRepository);
        tvShowDetailsViewModel.tvShowDetail.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        Resource<TvShowEntity> tvShowEntity = FakeData.getResourceTvShow();
        MutableLiveData<Resource<TvShowEntity>> liveData = new MutableLiveData<>();
        liveData.setValue(tvShowEntity);

        when(movieCatalogueRepository.loadTvShowDetails(1L)).thenReturn(liveData);

        tvShowDetailsViewModel.setTvShowId(1L);

        verify(observer).onChanged(tvShowEntity);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowDetailsViewModel = null;
    }
}