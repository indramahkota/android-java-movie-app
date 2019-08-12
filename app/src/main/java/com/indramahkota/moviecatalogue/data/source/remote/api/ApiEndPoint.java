package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Observable<DiscoverMovieResponse> getDiscoverMovies(@Query("api_key") String apiKey);

    @GET("discover/tv")
    Observable<DiscoverTvShowResponse> getDiscoverTvShows(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Observable<MovieEntity> getMovie(
            @Path("movie_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("tv/{tv_id}")
    Observable<TvShowEntity> getTvShow(
            @Path("tv_id") Long id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("search/movie")
    Observable<DiscoverMovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query);

    @GET("search/tv")
    Observable<DiscoverTvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query);

    @GET("configuration/languages")
    Observable<List<LanguageEntity>> getLanguages(@Query("api_key") String apiKey);
}
