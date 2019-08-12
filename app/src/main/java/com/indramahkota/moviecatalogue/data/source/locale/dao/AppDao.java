package com.indramahkota.moviecatalogue.data.source.locale.dao;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

@Dao
public interface AppDao {
    /*
     *
     * Languages
     * */

    @Query("SELECT * FROM " + LanguageEntity.TABLE_NAME)
    LiveData<List<LanguageEntity>> selectAllLanguage();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLanguages(List<LanguageEntity> languages);

    /*
     *
     * Movies
     * */

    @WorkerThread
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME)
    DataSource.Factory<Integer, MovieEntity> selectAllMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<MovieEntity> selectMovieById(long id);

    @Query("DELETE FROM " + MovieEntity.TABLE_NAME + " WHERE itemId = :id")
    void deleteMovieById(long id);

    /*@Update(onConflict = OnConflictStrategy.REPLACE)
    int updateMovie(MovieEntity movieEntity);*/

    /*
     *
     * Tv Shows
     * */

    @WorkerThread
    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME)
    DataSource.Factory<Integer, TvShowEntity> selectAllTvShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShowEntity tvShowEntity);

    @Query("SELECT * FROM " + TvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    LiveData<TvShowEntity> selectTvShowById(long id);

    @Query("DELETE FROM " + TvShowEntity.TABLE_NAME + " WHERE itemId = :id")
    void deleteTvShowById(long id);

    /*@Update(onConflict = OnConflictStrategy.REPLACE)
    int updateTvShow(TvShowEntity tvShowEntity);*/
}
