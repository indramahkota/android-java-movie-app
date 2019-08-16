package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteMovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private FavoriteMovieViewModel favoriteMovieViewModel;

    @Mock
    Observer<Resource<PagedList<MovieEntity>>> observer;

    @Mock
    PagedList<MovieEntity> pagedList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        favoriteMovieViewModel = new FavoriteMovieViewModel(movieCatalogueRepository);
    }

    @Test
    public void testApiFetchData() {
        Resource<PagedList<MovieEntity>> moviePagedList = Resource.success(pagedList);
        MutableLiveData<Resource<PagedList<MovieEntity>>> favoriteMovie = new MutableLiveData<>();
        favoriteMovie.setValue(moviePagedList);

        when(movieCatalogueRepository.getAllMovie()).thenReturn(favoriteMovie);

        favoriteMovieViewModel.getListMovie().observeForever(observer);

        verify(observer).onChanged(moviePagedList);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        favoriteMovieViewModel = null;
    }
}