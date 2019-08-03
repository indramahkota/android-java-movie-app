package com.indramahkota.moviecatalogue.data.source.remote.repository;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RemoteRepository {
    private final ApiEndPoint api;

    @Inject
    public RemoteRepository(ApiEndPoint api) {
        this.api = api;
    }

    public Single<DiscoverMovieResponse> loadListMovie() {
        return api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
    }

    public Single<DiscoverTvShowResponse> loadListTvShow() {
        return api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
    }

    public Single<MovieResponse> loadMovieDetails(Integer movieId) {
        return api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
    }

    public Single<TvShowResponse> loadTvShowDetails(Integer tvShowId) {
        return api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
    }
}
