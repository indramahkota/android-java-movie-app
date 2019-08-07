package com.indramahkota.moviecatalogue.data.source.locale.dao;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

@Dao
public interface FavoriteDao {
    /*
     *
     * Movie
     * */

    @WorkerThread
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME)
    LiveData<List<MovieEntity>> selectAllMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<MovieEntity> selectMovieById(long id);

    @Query("DELETE FROM " + MovieEntity.TABLE_NAME + " WHERE itemId = :id")
    int deleteMovieById(long id);

    /*
     *
     * Tv Show
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteTvShowEntity.TABLE_NAME)
    LiveData<List<TvShowEntity>> selectAllTvShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTvShow(TvShowEntity tvShowEntity);

    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<TvShowEntity> selectTvShowById(long id);

    @Query("DELETE FROM " + TvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    int deleteTvShowById(long id);

    /*
     *
     * Favorite Movie
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteMovieEntity.TABLE_NAME)
    LiveData<List<FavoriteMovieEntity>> selectAllFavoriteMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity);

    @Query("SELECT * FROM " + FavoriteMovieEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<FavoriteMovieEntity> selectFavoriteMovieById(long id);

    @Query("DELETE FROM " + FavoriteMovieEntity.TABLE_NAME + " WHERE itemId = :id")
    int deleteFavoriteMovieById(long id);

    /*
     *
     * Favorite Tv Show
     * */

    @WorkerThread
    @Query("SELECT * FROM " + FavoriteTvShowEntity.TABLE_NAME)
    LiveData<List<FavoriteTvShowEntity>> selectAllFavoriteTvShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteTvShow(FavoriteTvShowEntity favoriteTvShowEntity);

    @Query("SELECT * FROM " + FavoriteTvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<FavoriteTvShowEntity> selectFavoriteTvShowById(long id);

    @Query("DELETE FROM " + FavoriteTvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    int deleteFavoriteTvShowById(long id);
}
