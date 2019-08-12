package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;

import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    MovieDetailsViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<MovieEntity>> getMovieDetails(Long id) {
        return repository.loadMovieDetails(id);
    }
}
