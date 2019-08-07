package com.indramahkota.moviecatalogue.data.source.locale.repository;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalRepository {
    private final FavoriteDao dao;

    @Inject
    public LocalRepository(FavoriteDao dao) {
        this.dao = dao;
    }

    /*
     *
     * Favorite Movie
     * */

    public LiveData<List<MovieEntity>> getAllMovie() {
        return dao.selectAllMovie();
    }

    public LiveData<MovieEntity> getMovieById(long itemId) {
        return dao.selectMovieById(itemId);
    }

    public Long insertMovie(MovieEntity movieEntity) {
        return dao.insertMovie(movieEntity);
    }

    public Integer deleteMovie(long id) {
        return dao.deleteMovieById(id);
    }

    /*
     *
     * Tv Show
     * */

    public LiveData<List<TvShowEntity>> getAllTvShow() {
        return dao.selectAllTvShow();
    }

    public LiveData<TvShowEntity> getTvShowById(long id) {
        return dao.selectTvShowById(id);
    }

    public Long insertTvShow(TvShowEntity tvShowEntity) {
        return dao.insertTvShow(tvShowEntity);
    }

    public Integer deleteTvShow(long id) {
        return dao.deleteTvShowById(id);
    }

    /*
     *
     * Favorite Movie
     * */

    public LiveData<List<FavoriteMovieEntity>> getAllFavoriteMovie() {
        return dao.selectAllFavoriteMovie();
    }

    public LiveData<FavoriteMovieEntity> getFavoriteMovieById(long id) {
        return dao.selectFavoriteMovieById(id);
    }

    public Long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity) {
        return dao.insertFavoriteMovie(favoriteMovieEntity);
    }

    public Integer deleteFavoriteMovie(long id) {
        return dao.deleteFavoriteMovieById(id);
    }

    /*
     *
     * Favorite Tv Show
     * */

    public LiveData<List<FavoriteTvShowEntity>> getAllFavoriteTvShow() {
        return dao.selectAllFavoriteTvShow();
    }

    public LiveData<FavoriteTvShowEntity> getFavoriteTvShowById(long id) {
        return dao.selectFavoriteTvShowById(id);
    }

    public Long insertFavoriteTvShow(FavoriteTvShowEntity favoriteTvShowEntity) {
        return dao.insertFavoriteTvShow(favoriteTvShowEntity);
    }

    public Integer deleteFavoriteTvShow(long id) {
        return dao.deleteFavoriteTvShowById(id);
    }
}
