package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import javax.inject.Inject;

public class TvShowDetailsViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    TvShowDetailsViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<TvShowEntity>> getTvShowDetails(Long id) {
        return repository.loadTvShowDetails(id);
    }
}
