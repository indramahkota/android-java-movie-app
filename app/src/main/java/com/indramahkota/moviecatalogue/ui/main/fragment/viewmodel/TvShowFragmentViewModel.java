package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverTvShowResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<DiscoverTvShowResponseState> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<DiscoverTvShowResponseState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShow() {
        tvShowViewState.postValue(DiscoverTvShowResponseState.LOADING_STATE);
        disposable.add(repository.loadListTvShow()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        DiscoverTvShowResponseState.SUCCESS_STATE.setData(discoverTvShowResponse);
        tvShowViewState.postValue(DiscoverTvShowResponseState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        DiscoverTvShowResponseState.ERROR_STATE.setError(error);
        tvShowViewState.postValue(DiscoverTvShowResponseState.ERROR_STATE);
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
