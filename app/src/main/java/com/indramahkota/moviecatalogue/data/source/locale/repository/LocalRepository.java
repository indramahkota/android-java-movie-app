package com.indramahkota.moviecatalogue.data.source.locale.repository;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;

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

    public LiveData<List<FavoriteMovieEntity>> getAllFavoriteMovie() {
        return dao.selectAllMovie();
    }

    public LiveData<FavoriteMovieEntity> getFavoriteMovie(long itemId) {
        return dao.selectMovieById(itemId);
    }

    public Long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity) {
        return dao.insertMovie(favoriteMovieEntity);
    }

    public Integer deleteFavoriteMovie(long itemId) {
        return dao.deleteMovieById(itemId);
    }

    /*
     *
     * Favorite Tv Show
     * */

    public LiveData<List<FavoriteTvShowEntity>> getAllFavoriteTvShow() {
        return dao.selectAllTvShow();
    }

    public LiveData<FavoriteTvShowEntity> getFavoriteTvShow(long itemId) {
        return dao.selectTvShowById(itemId);
    }

    public Long insertFavoriteTvShow(FavoriteTvShowEntity favoriteTvShowEntity) {
        return dao.insertTvShow(favoriteTvShowEntity);
    }

    public Integer deleteFavoriteTvShow(long itemId) {
        return dao.deleteTvShowById(itemId);
    }
}
