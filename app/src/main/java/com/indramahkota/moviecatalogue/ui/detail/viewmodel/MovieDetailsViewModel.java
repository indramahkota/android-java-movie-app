package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private final MutableLiveData<Long> stateHandler = new MutableLiveData<>();
    public LiveData<Resource<MovieEntity>> movieDetail = Transformations.switchMap(stateHandler,
            stateId -> repository.loadMovieDetails(stateId));

    @Inject
    MovieDetailsViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setMovieId(Long id) {
        stateHandler.setValue(id);
    }
}
