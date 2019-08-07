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
    LocalRepository(FavoriteDao dao) {
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

    public Long insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        return dao.insertMovie(favoriteMovie);
    }

    public Integer deleteFavoriteMovie(long itemId) {
        return dao.deleteMovieById(itemId);
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

    public Long insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        return dao.insertTvShow(favoriteTvShow);
    }

    public Integer deleteFavoriteTvShow(long itemId) {
        return dao.deleteTvShowById(itemId);
    }
}
