package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<DiscoverTvShowResponse>> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<DiscoverTvShowResponse>> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShow() {
        tvShowViewState.postValue(Resource.loading(new DiscoverTvShowResponse()));
        disposable.add(repository.loadListTvShow()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        tvShowViewState.postValue(Resource.success(discoverTvShowResponse));
    }

    private void onError(Throwable error) {
        tvShowViewState.postValue(Resource.error(String.valueOf(error), new DiscoverTvShowResponse()));
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
