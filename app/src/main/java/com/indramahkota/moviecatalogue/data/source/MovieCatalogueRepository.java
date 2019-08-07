package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
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
import io.reactivex.Single;

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
    public Single<DiscoverMovieResponse> loadListMovie() {
        return api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
    }

    //TvShowFragmentViewModel
    @Override
    public Single<DiscoverTvShowResponse> loadListTvShow() {
        return api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<List<FavoriteMovieEntity>> getAllFavoriteMovie() {
        return dao.selectAllFavoriteMovie();
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<FavoriteMovieEntity> getFavoriteMovieById(Long id) {
        return dao.selectFavoriteMovieById(id);
    }

    //FavoriteMovieViewModel
    @Override
    public long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity) {
        return dao.insertFavoriteMovie(favoriteMovieEntity);
    }

    //FavoriteMovieViewModel
    @Override
    public int deleteFavoriteMovieById(long id) {
        return dao.deleteFavoriteMovieById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<List<FavoriteTvShowEntity>> getAllFavoriteTvShow() {
        return dao.selectAllFavoriteTvShow();
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<FavoriteTvShowEntity> getFavoriteTvShowById(Long id) {
        return dao.selectFavoriteTvShowById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public long insertFavoriteTvShow(FavoriteTvShowEntity favoriteTvShowEntity) {
        return dao.insertFavoriteTvShow(favoriteTvShowEntity);
    }

    //FavoriteTvShowViewModel
    @Override
    public int deleteFavoriteTvShowById(long id) {
        return dao.deleteFavoriteTvShowById(id);
    }

    /*
     * Detail Activity
     * */

    @Override
    public Single<MovieEntity> loadMovieDetails(Long movieId) {
        return api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
    }

    @Override
    public Single<TvShowEntity> loadTvShowDetails(Long tvShowId) {
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
    public Single<DiscoverMovieResponse> searchListMovie(String query) {
        return api.searchMovies(BuildConfig.TMDB_API_KEY, query);
    }

    @Override
    public Single<DiscoverTvShowResponse> searchListTvShow(String query) {
        return api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
    }
}
