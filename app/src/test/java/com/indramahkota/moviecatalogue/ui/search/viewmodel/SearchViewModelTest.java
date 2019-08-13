package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
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

public class SearchViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private SearchViewModel searchViewModel;

    @Mock
    Observer<Resource<List<MovieEntity>>> movieObserver;

    @Mock
    Observer<Resource<List<TvShowEntity>>> tvShowObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchViewModel = new SearchViewModel(movieCatalogueRepository);
        searchViewModel.searchMovie.observeForever(movieObserver);
        searchViewModel.searchTvShow.observeForever(tvShowObserver);
    }

    @Test
    public void testApiFetchDataMovie() {
        when(movieCatalogueRepository.searchListMovie("Test")).thenReturn(null);
        assertNotNull(searchViewModel.searchMovie);
        assertTrue(searchViewModel.searchTvShow.hasObservers());
    }

    @Test
    public void testApiFetchDataMovieSuccess() {
        //when(movieCatalogueRepository.searchListMovie("Test")).thenReturn();
        searchViewModel.setQuery("Test");
        verify(movieObserver).onChanged(Resource.loading(new ArrayList<>()));
        verify(movieObserver).onChanged(Resource.success(new ArrayList<>()));
    }

    @Test
    public void testApiFetchDataMovieError() {
        //when(movieCatalogueRepository.searchListMovie("Test")).thenReturn();
        searchViewModel.setQuery("Test");
        verify(movieObserver).onChanged(Resource.loading(new ArrayList<>()));
        verify(movieObserver).onChanged(Resource.error("", new ArrayList<>()));
    }

    @Test
    public void testApiFetchDataTvShow() {
        when(movieCatalogueRepository.searchListTvShow("Test")).thenReturn(null);
        assertNotNull(searchViewModel.searchTvShow);
        assertTrue(searchViewModel.searchTvShow.hasObservers());
    }

    @Test
    public void testApiFetchDataTvShowSuccess() {
        //when(movieCatalogueRepository.searchListTvShow("Test")).thenReturn();
        searchViewModel.setQuery("Test");
        verify(movieObserver).onChanged(Resource.loading(new ArrayList<>()));
        verify(movieObserver).onChanged(Resource.success(new ArrayList<>()));
    }

    @Test
    public void testApiFetchDataTvShowError() {
        //when(movieCatalogueRepository.searchListTvShow("Test")).thenReturn();
        searchViewModel.setQuery("Test");
        verify(movieObserver).onChanged(Resource.loading(new ArrayList<>()));
        verify(movieObserver).onChanged(Resource.error("", new ArrayList<>()));
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        searchViewModel = null;
    }
}