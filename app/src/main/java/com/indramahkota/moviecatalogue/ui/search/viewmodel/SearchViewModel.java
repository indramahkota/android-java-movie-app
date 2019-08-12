package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    SearchViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<DiscoverMovieResponse>> getSearchMovies(String query) {
        return repository.searchListMovie(query);
    }

    public LiveData<ApiResponse<DiscoverTvShowResponse>> getSearchTvShows(String query) {
        return repository.searchListTvShow(query);
    }
}
