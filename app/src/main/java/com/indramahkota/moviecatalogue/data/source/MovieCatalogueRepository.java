package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MovieCatalogueRepository implements MovieCatalogueDataSource {
    private final FavoriteDao dao;
    private final ApiEndPoint api;

    @Inject
    public MovieCatalogueRepository(FavoriteDao favoriteDao, ApiEndPoint apiEndPoint){
        this.dao = favoriteDao;
        this.api = apiEndPoint;
    }

    /*
     * Main Activity
     * */

    //MovieFragmentViewModel
    @Override
    public Observable<DiscoverMovieResponse> loadListMovie() {
        return api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
    }

    //TvShowFragmentViewModel
    @Override
    public Observable<DiscoverTvShowResponse> loadListTvShow() {
        return api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<List<MovieEntity>> getAllMovie() {
        return dao.selectAllMovie();
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<MovieEntity> getMovieById(Long id) {
        return dao.selectMovieById(id);
    }

    //FavoriteMovieViewModel
    @Override
    public long insertMovie(MovieEntity movieEntity) {
        return dao.insertMovie(movieEntity);
    }

    //FavoriteMovieViewModel
    @Override
    public int deleteMovieById(long id) {
        return dao.deleteMovieById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<List<TvShowEntity>> getAllTvShow() {
        return dao.selectAllTvShow();
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<TvShowEntity> getTvShowById(Long id) {
        return dao.selectTvShowById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public long insertTvShow(TvShowEntity tvShowEntity) {
        return dao.insertTvShow(tvShowEntity);
    }

    //FavoriteTvShowViewModel
    @Override
    public int deleteTvShowById(long id) {
        return dao.deleteTvShowById(id);
    }

    /*
     * Detail Activity
     * */

    @Override
    public Observable<MovieEntity> loadMovieDetails(Long movieId) {
        return api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
    }

    @Override
    public Observable<TvShowEntity> loadTvShowDetails(Long tvShowId) {
        return api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
    }

    @Override
    public Observable<ArrayList<Language>> loadLanguages() {
        return api.getLanguages(BuildConfig.TMDB_API_KEY);
    }

    /*
     * Search Activity
     * */

    @Override
    public Observable<DiscoverMovieResponse> searchListMovie(String query) {
        return api.searchMovies(BuildConfig.TMDB_API_KEY, query);
    }

    @Override
    public Observable<DiscoverTvShowResponse> searchListTvShow(String query) {
        return api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
    }
}
