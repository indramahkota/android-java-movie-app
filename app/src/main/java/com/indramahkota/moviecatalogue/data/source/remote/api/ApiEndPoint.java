package com.indramahkota.moviecatalogue.data.source.remote.api;

import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
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
    Single<MovieResponse> getMovie(
            @Path("movie_id") Integer id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("tv/{tv_id}")
    Single<TvShowResponse> getTvShow(
            @Path("tv_id") Integer id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

    @GET("configuration/languages")
    Observable<ArrayList<Language>> getLanguages(@Query("api_key") String apiKey);
}
