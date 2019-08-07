package com.indramahkota.moviecatalogue.data.source.remote.repository;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
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

    public Single<DiscoverMovieResponse> searchListMovie(String query) {
        return api.searchMovies(BuildConfig.TMDB_API_KEY, query);
    }

    public Single<DiscoverTvShowResponse> loadListTvShow() {
        return api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
    }

    public Single<DiscoverTvShowResponse> searchListTvShow(String query) {
        return api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
    }

    public Single<MovieEntity> loadMovieDetails(Long movieId) {
        return api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
    }

    public Single<TvShowEntity> loadTvShowDetails(Long tvShowId) {
        return api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
    }

    public Observable<ArrayList<Language>> loadLanguages() {
        return api.getLanguages(BuildConfig.TMDB_API_KEY);
    }
}
