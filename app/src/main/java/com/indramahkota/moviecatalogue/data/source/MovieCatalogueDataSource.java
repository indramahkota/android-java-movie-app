package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */
    //Movie Fragment
    Observable<DiscoverMovieResponse> loadListMovie();
    //Tv Show Fragment
    Observable<DiscoverTvShowResponse> loadListTvShow();
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
    Observable<MovieEntity> loadMovieDetails(Long movieId);
    //Tv Show Detail Activity
    Observable<TvShowEntity> loadTvShowDetails(Long tvShowId);
    //Languages
    Observable<ArrayList<Language>> loadLanguages();

    /*
     * Search Activity
     * */
    //Search Activity
    Observable<DiscoverMovieResponse> searchListMovie(String query);
    //Search Activity
    Observable<DiscoverTvShowResponse> searchListTvShow(String query);
}
