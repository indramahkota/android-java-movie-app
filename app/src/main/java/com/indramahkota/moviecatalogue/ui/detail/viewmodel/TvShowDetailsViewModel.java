package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import javax.inject.Inject;

public class TvShowDetailsViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private final MutableLiveData<Long> stateHandler = new MutableLiveData<>();
    public LiveData<Resource<TvShowEntity>> tvShowDetail = Transformations.switchMap(stateHandler,
            stateId -> repository.loadTvShowDetails(stateId));

    @Inject
    TvShowDetailsViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setTvShowId(Long id) {
        stateHandler.setValue(id);
    }
}
