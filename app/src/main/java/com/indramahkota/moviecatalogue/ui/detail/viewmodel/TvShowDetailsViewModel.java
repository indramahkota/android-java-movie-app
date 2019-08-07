package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.TvShowResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<TvShowResponseState> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowDetailsViewModel(MovieCatalogueRepository repository, SingleSchedulers singleSchedulers) {
        this.repository = repository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<TvShowResponseState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShowDetails(Long tvShowId) {
        disposable.add(repository.loadTvShowDetails(tvShowId)
                .doOnEvent((tvShowResponse, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(TvShowEntity tvShowEntity) {
        TvShowResponseState.SUCCESS_STATE.setData(tvShowEntity);
        tvShowViewState.postValue(TvShowResponseState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        TvShowResponseState.ERROR_STATE.setError(error);
        tvShowViewState.postValue(TvShowResponseState.ERROR_STATE);
    }

    private void onLoading() {
        tvShowViewState.postValue(TvShowResponseState.LOADING_STATE);
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
