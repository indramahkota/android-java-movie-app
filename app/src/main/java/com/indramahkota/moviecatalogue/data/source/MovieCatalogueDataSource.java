package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import java.util.List;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */
    //Movie Fragment
    LiveData<ApiResponse<DiscoverMovieResponse>> loadListMovie();
    //Tv Show Fragment
    LiveData<ApiResponse<DiscoverTvShowResponse>> loadListTvShow();
    //Favorite Movie Fragment
    LiveData<List<MovieEntity>> getAllMovie();
    LiveData<MovieEntity> getMovieById(Long id);
    void insertMovie(MovieEntity movieEntity);
    void deleteMovieById(long id);
    //Favorite Tv Show Fragment
    LiveData<List<TvShowEntity>> getAllTvShow();
    LiveData<TvShowEntity> getTvShowById(Long id);
    void insertTvShow(TvShowEntity tvShowEntity);
    void deleteTvShowById(long id);

    /*
    * Detail Activity
    * */
    //Movie Detail Activity
    LiveData<ApiResponse<MovieEntity>> loadMovieDetails(Long movieId);
    //Tv Show Detail Activity
    LiveData<ApiResponse<TvShowEntity>> loadTvShowDetails(Long tvShowId);
    //Languages
    /*LiveData<List<LanguageEntity>> loadLanguages();*/

    /*
     * Search Activity
     * */
    //Search Activity
    LiveData<ApiResponse<DiscoverMovieResponse>> searchListMovie(String query);
    //Search Activity
    LiveData<ApiResponse<DiscoverTvShowResponse>> searchListTvShow(String query);


    LiveData<Resource<List<LanguageEntity>>> loadLanguage();
}
