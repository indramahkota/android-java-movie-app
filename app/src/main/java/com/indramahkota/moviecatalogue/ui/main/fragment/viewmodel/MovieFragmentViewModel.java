package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

import javax.inject.Inject;

public class MovieFragmentViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private MutableLiveData<String> refreshHandler = new MutableLiveData<>();
    public LiveData<Resource<DiscoverMovieResponse>> listDiscoverMovie = Transformations.switchMap(refreshHandler,
            refreshId -> repository.loadListMovie(refreshId));

    @Inject
    MovieFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setRefreshId(String refId) {
        refreshHandler.setValue(refId);
    }
}
