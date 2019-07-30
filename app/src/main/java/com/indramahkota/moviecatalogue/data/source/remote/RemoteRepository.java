package com.indramahkota.moviecatalogue.data.source.remote;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import javax.inject.Inject;

import io.reactivex.Single;

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
}
