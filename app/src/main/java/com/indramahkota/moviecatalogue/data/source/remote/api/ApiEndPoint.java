package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Call<MovieResponse> getDiscoverMovies(@Query("api_key") String apiKey,
                                          @Query("page") long page);

    @GET("discover/tv")
    Call<TvShowResponse> getDiscoverTvShows(@Query("api_key") String apiKey,
                                            @Query("page") long page);

    @GET("movie/{movie_id}")
    Call<MovieEntity> getMovie(
            @Path("movie_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("tv/{tv_id}")
    Call<TvShowEntity> getTvShow(
            @Path("tv_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") long page);

    @GET("search/tv")
    Call<TvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") long page);

    @GET("configuration/languages")
    Call<List<LanguageEntity>> getLanguages(@Query("api_key") String apiKey);
}
