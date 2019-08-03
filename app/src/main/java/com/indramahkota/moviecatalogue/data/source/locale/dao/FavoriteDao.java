package com.indramahkota.moviecatalogue.data.source.locale.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovie;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;

@Dao
public interface FavoriteDao {
    /*
     *
     * Favorite Movie
     * */

    @Insert
    long insertMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME)
    Cursor selectAllMovie();

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME + " WHERE itemId = :itemId")
    Cursor selectMovieById(long itemId);

    @Query("DELETE FROM " + FavoriteMovie.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteMovieById(long itemId);

    @Update
    int updateMovie(FavoriteMovie favoriteMovie);

    /*
     *
     * Favorite Tv Show
     * */

    @Insert
    long insertTvShow(FavoriteTvShow favoriteTvShow);

    @Query("SELECT * FROM " + FavoriteTvShow.TABLE_NAME)
    Cursor selectAllTvShow();

    @Query("SELECT * FROM " + FavoriteTvShow.TABLE_NAME + " WHERE itemId = :itemId")
    Cursor selectTvShowById(long itemId);

    @Query("DELETE FROM " + FavoriteTvShow.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteTvShowById(long itemId);

    @Update
    int updateTvShow(FavoriteTvShow favoriteTvShow);
}
