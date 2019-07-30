package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Call<DiscoverMovieResponse> getDiscoverMovies(@Query("api_key") String apiKey);

    @GET("discover/tv")
    Call<DiscoverTvShowResponse> getDiscoverTvShows(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovie(
            @Path("movie_id") Integer id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("tv/{tv_id}")
    Call<TvShowResponse> getTvShow(
            @Path("tv_id") Integer id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("configuration/languages")
    Call<ArrayList<Language>> getLanguages(@Query("api_key") String apiKey);
}
