package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

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

public class MovieFragmentViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private MovieFragmentViewModel movieFragmentViewModel;

    @Mock
    Observer<Resource<List<MovieEntity>>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieFragmentViewModel = new MovieFragmentViewModel(movieCatalogueRepository);
        movieFragmentViewModel.listDiscoverMovie.observeForever(observer);
    }

    @Test
    public void testApiFetchData() {
        when(movieCatalogueRepository.loadListTvShow("")).thenReturn(null);
        assertNotNull(movieFragmentViewModel.listDiscoverMovie);
        assertTrue(movieFragmentViewModel.listDiscoverMovie.hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        //when(movieCatalogueRepository.loadListMovie("")).thenReturn();
        movieFragmentViewModel.setRefreshId("");
        verify(observer).onChanged(Resource.loading(new ArrayList<>()));
        verify(observer).onChanged(Resource.success(new ArrayList<>()));
    }

    @Test
    public void testApiFetchDataError() {
        //when(movieCatalogueRepository.loadListMovie("")).thenReturn();
        movieFragmentViewModel.setRefreshId("");
        verify(observer).onChanged(Resource.loading(new ArrayList<>()));
        verify(observer).onChanged(Resource.error("", new ArrayList<>()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        movieFragmentViewModel = null;
    }
}