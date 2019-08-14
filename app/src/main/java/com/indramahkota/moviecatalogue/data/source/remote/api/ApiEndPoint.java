package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Call<DiscoverMovieResponse> getDiscoverMovies(@Query("api_key") String apiKey,
                                                  @Query("page") long page);

    @GET("discover/tv")
    Call<DiscoverTvShowResponse> getDiscoverTvShows(@Query("api_key") String apiKey,
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
    Call<DiscoverMovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") long page);

    @GET("search/tv")
    Call<DiscoverTvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") long page);

    @GET("configuration/languages")
    Call<List<LanguageEntity>> getLanguages(@Query("api_key") String apiKey);
}
