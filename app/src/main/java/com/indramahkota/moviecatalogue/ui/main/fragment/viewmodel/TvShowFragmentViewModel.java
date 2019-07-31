package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.ui.main.fragment.state.TvShowViewState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<TvShowViewState> tvShowViewState = new MutableLiveData<>();

    @Inject
    TvShowFragmentViewModel(RemoteRepository remoteRepository, SingleSchedulers singleSchedulers) {
        this.remoteRepository = remoteRepository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<TvShowViewState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void loadTvShow() {
        disposable.add(remoteRepository.loadListTvShow()
                .doOnEvent((newsList, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        TvShowViewState.SUCCESS_STATE.setData(discoverTvShowResponse);
        tvShowViewState.postValue(TvShowViewState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        TvShowViewState.ERROR_STATE.setError(error);
        tvShowViewState.postValue(TvShowViewState.ERROR_STATE);
    }

    private void onLoading() {
        tvShowViewState.postValue(TvShowViewState.LOADING_STATE);
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
