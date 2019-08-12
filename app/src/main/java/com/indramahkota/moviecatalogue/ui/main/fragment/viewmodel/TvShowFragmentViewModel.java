package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import javax.inject.Inject;

public class TvShowFragmentViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<DiscoverTvShowResponse>> getDiscoverTvShows() {
        return repository.loadListTvShow();
    }
}
