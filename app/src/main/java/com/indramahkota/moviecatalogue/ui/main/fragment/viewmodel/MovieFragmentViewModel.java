package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

import javax.inject.Inject;

public class MovieFragmentViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    MovieFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<DiscoverMovieResponse>> getDiscoverMovies() {
        return repository.loadListMovie();
    }
}
