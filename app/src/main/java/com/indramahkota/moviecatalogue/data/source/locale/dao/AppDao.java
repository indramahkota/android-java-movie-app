package com.indramahkota.moviecatalogue.data.source.locale.dao;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

@Dao
public interface AppDao {
    /*
     * Languages
     * */

    @Query("SELECT * FROM " + LanguageEntity.TABLE_NAME)
    LiveData<List<LanguageEntity>> selectAllLanguage();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLanguages(List<LanguageEntity> languages);

    /*
     * Movies
     * */

    @WorkerThread
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " WHERE favorite = 1")
    DataSource.Factory<Integer, MovieEntity> selectAllFavoriteMovie();

    @WorkerThread
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME+ " WHERE page = :currentPage")
    List<MovieEntity> selectAllMovie(Long currentPage);

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " WHERE itemId = :id")
    MovieEntity selectMovieById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movieEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntity movieEntity);

    /*
     * Tv Shows
     * */

    @WorkerThread
    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME + " WHERE favorite = 1")
    DataSource.Factory<Integer, TvShowEntity> selectAllTvShow();

    @WorkerThread
    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME)
    LiveData<List<TvShowEntity>> selectAllTvShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShowEntity tvShowEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShows(List<TvShowEntity> tvShowEntities);

    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    TvShowEntity selectTvShowById(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTvShow(TvShowEntity tvShowEntity);
}
