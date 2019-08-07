package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Single<DiscoverMovieResponse> getDiscoverMovies(@Query("api_key") String apiKey);

    @GET("discover/tv")
    Single<DiscoverTvShowResponse> getDiscoverTvShows(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Single<MovieEntity> getMovie(
            @Path("movie_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("tv/{tv_id}")
    Single<TvShowEntity> getTvShow(
            @Path("tv_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("search/movie")
    Single<DiscoverMovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query);

    @GET("search/tv")
    Single<DiscoverTvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query);

    @GET("configuration/languages")
    Observable<ArrayList<Language>> getLanguages(@Query("api_key") String apiKey);
}
