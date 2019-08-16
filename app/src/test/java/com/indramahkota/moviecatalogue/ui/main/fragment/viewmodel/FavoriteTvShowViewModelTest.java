package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteTvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieCatalogueRepository movieCatalogueRepository;

    private FavoriteTvShowViewModel favoriteTvShowViewModel;

    @Mock
    Observer<Resource<PagedList<TvShowEntity>>> observer;

    @Mock
    PagedList<TvShowEntity> pagedList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        favoriteTvShowViewModel = new FavoriteTvShowViewModel(movieCatalogueRepository);
    }

    @Test
    public void testApiFetchData() {
        Resource<PagedList<TvShowEntity>> tvShowPagedList = Resource.success(pagedList);
        MutableLiveData<Resource<PagedList<TvShowEntity>>> favoriteTvShow = new MutableLiveData<>();
        favoriteTvShow.setValue(tvShowPagedList);

        when(movieCatalogueRepository.getAllTvShow()).thenReturn(favoriteTvShow);

        favoriteTvShowViewModel.getListTvShow().observeForever(observer);

        verify(observer).onChanged(tvShowPagedList);
    }

    @After
    public void tearDown() {
        movieCatalogueRepository = null;
        favoriteTvShowViewModel = null;
    }
}