package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

import javax.inject.Inject;

public class TvShowFragmentViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private MutableLiveData<String> refreshHandler = new MutableLiveData<>();
    public LiveData<Resource<List<TvShowEntity>>> listDiscoverTvShow = Transformations.switchMap(refreshHandler,
            refreshId -> repository.loadListTvShow(refreshId));

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setRefreshId(String refId) {
        refreshHandler.setValue(refId);
    }
}
