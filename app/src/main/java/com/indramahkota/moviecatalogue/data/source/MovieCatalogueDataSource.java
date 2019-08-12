package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import java.util.List;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */
    //Movie Fragment
    LiveData<Resource<DiscoverMovieResponse>> loadListMovie(String refresh);
    //Tv Show Fragment
    LiveData<Resource<DiscoverTvShowResponse>> loadListTvShow(String refresh);
    //Favorite Movie Fragment
    DataSource.Factory<Integer, MovieEntity> getAllMovie();
    LiveData<MovieEntity> getMovieById(Long id);
    void insertMovie(MovieEntity movieEntity);
    void deleteMovieById(long id);
    //Favorite Tv Show Fragment
    DataSource.Factory<Integer, TvShowEntity> getAllTvShow();
    LiveData<TvShowEntity> getTvShowById(Long id);
    void insertTvShow(TvShowEntity tvShowEntity);
    void deleteTvShowById(long id);

    /*
    * Detail Activity
    * */
    //Movie Detail Activity
    LiveData<Resource<MovieEntity>> loadMovieDetails(Long movieId);
    //Tv Show Detail Activity
    LiveData<Resource<TvShowEntity>> loadTvShowDetails(Long tvShowId);
    //Languages
    /*LiveData<List<LanguageEntity>> loadLanguages();*/

    /*
     * Search Activity
     * */
    //Search Activity
    LiveData<Resource<DiscoverMovieResponse>> searchListMovie(String query);
    //Search Activity
    LiveData<Resource<DiscoverTvShowResponse>> searchListTvShow(String query);


    LiveData<Resource<List<LanguageEntity>>> loadLanguage();
}
