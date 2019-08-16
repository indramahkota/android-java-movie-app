package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

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
    LiveData<Resource<DiscoverMovieResponse>> loadListMovie(Long page);

    //Tv Show Fragment
    LiveData<Resource<DiscoverTvShowResponse>> loadListTvShow(Long page);

    //Favorite Movie Fragment
    LiveData<Resource<PagedList<MovieEntity>>> getAllMovie();
    void updateMovie(MovieEntity movieEntity);

    //Favorite Tv Show Fragment
    LiveData<Resource<PagedList<TvShowEntity>>> getAllTvShow();
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
    LiveData<Resource<DiscoverMovieResponse>> searchListMovie(String query, Long page);

    //Search Activity
    LiveData<Resource<DiscoverTvShowResponse>> searchListTvShow(String query, Long page);
}
