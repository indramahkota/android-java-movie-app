package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
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

import static org.junit.Assert.assertNotNull;
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
        when(movieCatalogueRepository.loadTvShowDetails(1L)).thenReturn(FakeData.getTvShowLiveData());

        tvShowDetailsViewModel.setTvShowId(1L);

        Resource<TvShowEntity> tvShowEntity = tvShowDetailsViewModel.tvShowDetail.getValue();

        verify(movieCatalogueRepository).loadTvShowDetails(1L);
        verify(observer).onChanged(tvShowEntity);

        assertNotNull(tvShowEntity);
        assertNotNull(tvShowEntity.data);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowDetailsViewModel = null;
    }
}