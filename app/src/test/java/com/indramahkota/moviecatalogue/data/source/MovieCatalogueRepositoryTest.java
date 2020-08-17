package com.indramahkota.moviecatalogue.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.utils.InstantAppExecutors;
import com.indramahkota.moviecatalogue.utils.LiveDataTestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.indramahkota.moviecatalogue.utils.FakeData.getLanguages;
import static com.indramahkota.moviecatalogue.utils.FakeData.getListMovie;
import static com.indramahkota.moviecatalogue.utils.FakeData.getListTvShow;
import static com.indramahkota.moviecatalogue.utils.FakeData.getMovie;
import static com.indramahkota.moviecatalogue.utils.FakeData.getTvShow;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieCatalogueRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private AppDao dao = mock(AppDao.class);
    private ApiEndPoint api = mock(ApiEndPoint.class);
    private InstantAppExecutors instantAppExecutors = mock(InstantAppExecutors.class);
    private FakeMovieCatalogueRepository fakeMovieCatalogueRepository = new FakeMovieCatalogueRepository(dao, api, instantAppExecutors);

    @Test
    public void loadListMovie() {
        List<MovieEntity> dummyData = getListMovie();
        when(dao.selectAllMovie(1L)).thenReturn(dummyData);

        //skip load remote data
        Resource<MovieResponse> result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.loadListMovie(1L));

        verify(dao).selectAllMovie(1L);
        assertNotNull(result.data);
    }

    @Test
    public void loadListTvShow() {
        List<TvShowEntity> dummyData = getListTvShow();
        when(dao.selectAllTvShow(1L)).thenReturn(dummyData);

        //skip load remote data
        Resource<TvShowResponse> result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.loadListTvShow(1L));

        verify(dao).selectAllTvShow(1L);
        assertNotNull(result.data);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllMovie() {
        DataSource.Factory<Integer, MovieEntity> movieEntitySourceFactory = mock(DataSource.Factory.class);

        when(dao.selectAllFavoriteMovie()).thenReturn(movieEntitySourceFactory);

        fakeMovieCatalogueRepository.getAllMovie();

        verify(dao).selectAllFavoriteMovie();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllTvShow() {
        DataSource.Factory<Integer, TvShowEntity> tvShowEntitySourceFactory = mock(DataSource.Factory.class);

        when(dao.selectAllFavoriteTvShow()).thenReturn(tvShowEntitySourceFactory);

        fakeMovieCatalogueRepository.getAllTvShow();

        verify(dao).selectAllFavoriteTvShow();
    }

    @Test
    public void loadMovieDetails() {
        MovieEntity dummyData = getMovie();
        when(dao.selectMovieById(1L)).thenReturn(dummyData);

        //skip load remote data
        Resource<MovieEntity> result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.loadMovieDetails(1L));

        verify(dao).selectMovieById(1L);
        assertNotNull(result.data);
    }

    @Test
    public void loadTvShowDetails() {
        TvShowEntity dummyData = getTvShow();
        when(dao.selectTvShowById(1L)).thenReturn(dummyData);

        //skip load remote data
        Resource<TvShowEntity> result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.loadTvShowDetails(1L));

        verify(dao).selectTvShowById(1L);
        assertNotNull(result.data);
    }

    @Test
    public void loadLanguage() {
        List<LanguageEntity> dummyData = getLanguages();
        MutableLiveData<List<LanguageEntity>> dummyDataLiveData = new MutableLiveData<>();
        dummyDataLiveData.setValue(dummyData);
        when(dao.selectAllLanguage()).thenReturn(dummyDataLiveData);

        //skip load remote data
        Resource<List<LanguageEntity>> result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.loadLanguage());

        verify(dao).selectAllLanguage();
        assertNotNull(result.data);
    }
}