package com.indramahkota.moviecatalogue.data.source.locale.dao;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;

import java.util.List;

@Dao
public interface FavoriteDao {
    /*
     *
     * Favorite Movie
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteMovieEntity.TABLE_NAME)
    LiveData<List<FavoriteMovieEntity>> selectAllMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(FavoriteMovieEntity favoriteMovieEntity);

    @Query("SELECT * FROM " + FavoriteMovieEntity.TABLE_NAME + " WHERE itemId = :itemId")
    LiveData<FavoriteMovieEntity> selectMovieById(long itemId);

    @Query("DELETE FROM " + FavoriteMovieEntity.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteMovieById(long itemId);

    /*
     *
     * Favorite Tv Show
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteTvShowEntity.TABLE_NAME)
    LiveData<List<FavoriteTvShowEntity>> selectAllTvShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTvShow(FavoriteTvShowEntity favoriteTvShowEntity);

    @Query("SELECT * FROM " + FavoriteTvShowEntity.TABLE_NAME + " WHERE itemId = :itemId")
    LiveData<FavoriteTvShowEntity> selectTvShowById(long itemId);

    @Query("DELETE FROM " + FavoriteTvShowEntity.TABLE_NAME + " WHERE itemId = :itemId")
    int deleteTvShowById(long itemId);
}
