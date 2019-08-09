package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<TvShowEntity>> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowDetailsViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<TvShowEntity>> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShowDetails(Long tvShowId) {
        tvShowViewState.postValue(Resource.loading(new TvShowEntity()));
        disposable.add(repository.loadTvShowDetails(tvShowId)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(TvShowEntity tvShowEntity) {
        tvShowViewState.postValue(Resource.success(tvShowEntity));
    }

    private void onError(Throwable error) {
        tvShowViewState.postValue(Resource.error(String.valueOf(error), new TvShowEntity()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
