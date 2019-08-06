package com.indramahkota.moviecatalogue.data.source.locale.repository;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovie;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;

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

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovie() {
        return dao.selectAllMovie();
    }

    public LiveData<FavoriteMovie> getFavoriteMovie(long itemId) {
        return dao.selectMovieById(itemId);
    }

    public void insertFavoriteMovies(List<FavoriteMovie> favoriteMovies) {
        dao.insertMovies(favoriteMovies);
    }

    public Long insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        return dao.insertMovie(favoriteMovie);
    }

    public void deleteFavoriteMovie(long itemId) {
        dao.deleteMovieById(itemId);
    }

    public void updateFavoriteMovie(FavoriteMovie favoriteMovie) {
        dao.updateMovie(favoriteMovie);
    }

    /*
     *
     * Favorite Tv Show
     * */

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShow() {
        return dao.selectAllTvShow();
    }

    public LiveData<FavoriteTvShow> getFavoriteTvShow(long itemId) {
        return dao.selectTvShowById(itemId);
    }

    public void insertFavoriteTvShows(List<FavoriteTvShow> favoriteTvShows) {
        dao.insertTvShows(favoriteTvShows);
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        dao.insertTvShow(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(long itemId) {
        dao.deleteTvShowById(itemId);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        dao.updateTvShow(favoriteTvShow);
    }
}
