package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
        when(movieCatalogueRepository.loadTvShowDetails(60735L)).thenReturn(null);
        assertNotNull(tvShowDetailsViewModel.tvShowDetail);
        assertTrue(tvShowDetailsViewModel.tvShowDetail.hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        //when(movieCatalogueRepository.loadTvShowDetails(60735L)).thenReturn();
        tvShowDetailsViewModel.setTvShowId(60735L);
        verify(observer).onChanged(Resource.loading(new TvShowEntity()));
        verify(observer).onChanged(Resource.success(new TvShowEntity()));
    }

    @Test
    public void testApiFetchDataError() {
        //when(movieCatalogueRepository.loadTvShowDetails(0L)).thenReturn();
        tvShowDetailsViewModel.setTvShowId(0L);
        verify(observer).onChanged(Resource.loading(new TvShowEntity()));
        verify(observer).onChanged(Resource.error("", new TvShowEntity()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowDetailsViewModel = null;
    }
}