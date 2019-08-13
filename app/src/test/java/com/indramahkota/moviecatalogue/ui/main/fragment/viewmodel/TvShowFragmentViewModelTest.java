package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private TvShowFragmentViewModel tvShowFragmentViewModel;

    @Mock
    Observer<Resource<List<TvShowEntity>>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tvShowFragmentViewModel = new TvShowFragmentViewModel(movieCatalogueRepository);
        tvShowFragmentViewModel.listDiscoverTvShow.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadListTvShow("")).thenReturn(null);
        assertNotNull(tvShowFragmentViewModel.listDiscoverTvShow);
        assertTrue(tvShowFragmentViewModel.listDiscoverTvShow.hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        //when(movieCatalogueRepository.loadListTvShow("")).thenReturn();
        tvShowFragmentViewModel.setRefreshId("");
        verify(observer).onChanged(Resource.loading(new ArrayList<>()));
        verify(observer).onChanged(Resource.success(new ArrayList<>()));
    }

    @Test
    public void testApiFetchDataError() {
        //when(movieCatalogueRepository.loadListTvShow("")).thenReturn();
        tvShowFragmentViewModel.setRefreshId("");
        verify(observer).onChanged(Resource.loading(new ArrayList<>()));
        verify(observer).onChanged(Resource.error("", new ArrayList<>()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        tvShowFragmentViewModel = null;
    }
}