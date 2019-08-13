package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */
    //Movie Fragment
    LiveData<Resource<List<MovieEntity>>> loadListMovie(String refresh);

    //Tv Show Fragment
    LiveData<Resource<List<TvShowEntity>>> loadListTvShow(String refresh);

    //Favorite Movie Fragment
    DataSource.Factory<Integer, MovieEntity> getAllMovie();
    void updateMovie(MovieEntity movieEntity);

    //Favorite Tv Show Fragment
    DataSource.Factory<Integer, TvShowEntity> getAllTvShow();
    void updateTvShow(TvShowEntity tvShowEntity);

    /*
    * Detail Activity
    * */
    //Movie Detail Activity
    LiveData<Resource<MovieEntity>> loadMovieDetails(Long movieId);

    //Tv Show Detail Activity
    LiveData<Resource<TvShowEntity>> loadTvShowDetails(Long tvShowId);

    //Languages
    LiveData<Resource<List<LanguageEntity>>> loadLanguage();

    /*
     * Search Activity
     * */
    //Search Activity
    LiveData<Resource<List<MovieEntity>>> searchListMovie(String query);

    //Search Activity
    LiveData<Resource<List<TvShowEntity>>> searchListTvShow(String query);
}
