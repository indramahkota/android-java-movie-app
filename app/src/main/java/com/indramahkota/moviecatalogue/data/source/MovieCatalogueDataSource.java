package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import java.util.List;

public interface MovieCatalogueDataSource {
    LiveData<Resource<MovieResponse>> loadListMovie(Long page);

    LiveData<Resource<TvShowResponse>> loadListTvShow(Long page);

    LiveData<Resource<MovieEntity>> loadMovieDetails(Long movieId);

    LiveData<Resource<TvShowEntity>> loadTvShowDetails(Long tvShowId);

    void updateMovie(MovieEntity movieEntity);

    void updateTvShow(TvShowEntity tvShowEntity);

    LiveData<Resource<PagedList<MovieEntity>>> getAllMovie();

    LiveData<Resource<PagedList<TvShowEntity>>> getAllTvShow();

    LiveData<Resource<MovieResponse>> searchListMovie(String query, Long page);

    LiveData<Resource<TvShowResponse>> searchListTvShow(String query, Long page);

    LiveData<Resource<List<LanguageEntity>>> loadLanguage();
}
