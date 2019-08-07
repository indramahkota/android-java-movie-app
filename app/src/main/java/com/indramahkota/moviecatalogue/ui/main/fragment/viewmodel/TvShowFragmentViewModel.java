package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverTvShowResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<DiscoverTvShowResponseState> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository, SingleSchedulers singleSchedulers) {
        this.repository = repository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<DiscoverTvShowResponseState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShow() {
        disposable.add(repository.loadListTvShow()
                .doOnEvent((tvShowResponse, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
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

    private void onLoading() {
        tvShowViewState.postValue(DiscoverTvShowResponseState.LOADING_STATE);
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
