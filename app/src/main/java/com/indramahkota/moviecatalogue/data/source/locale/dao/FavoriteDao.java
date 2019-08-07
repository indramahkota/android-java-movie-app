package com.indramahkota.moviecatalogue.data.source.locale.dao;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovie;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;

import java.util.List;

@Dao
public interface FavoriteDao {
    /*
     *
     * Favorite Movie
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME)
    LiveData<List<FavoriteMovie>> selectAllMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME + " WHERE itemId = :itemId")
    LiveData<FavoriteMovie> selectMovieById(long itemId);

    @Query("DELETE FROM " + FavoriteMovie.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteMovieById(long itemId);

    /*
     *
     * Favorite Tv Show
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteTvShow.TABLE_NAME)
    LiveData<List<FavoriteTvShow>> selectAllTvShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTvShow(FavoriteTvShow favoriteTvShow);

    @Query("SELECT * FROM " + FavoriteTvShow.TABLE_NAME + " WHERE itemId = :itemId")
    LiveData<FavoriteTvShow> selectTvShowById(long itemId);

    @Query("DELETE FROM " + FavoriteTvShow.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteTvShowById(long itemId);
}
