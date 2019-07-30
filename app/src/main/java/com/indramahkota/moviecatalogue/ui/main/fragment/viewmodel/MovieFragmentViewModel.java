package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.RxSingleSchedulers;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final RxSingleSchedulers rxSingleSchedulers;
    private final MutableLiveData<MovieViewState> movieViewState = new MutableLiveData<>();

    @Inject
    public MovieFragmentViewModel(RemoteRepository remoteRepository, RxSingleSchedulers rxSingleSchedulers) {
        this.remoteRepository = remoteRepository;
        this.rxSingleSchedulers = rxSingleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieViewState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovie() {
        disposable.add(remoteRepository.loadListMovie()
                .doOnEvent((newsList, throwable) -> onLoading())
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverMovieResponse discoverMovieResponse) {
        MovieViewState.SUCCESS_STATE.setData(discoverMovieResponse);
        movieViewState.postValue(MovieViewState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        MovieViewState.ERROR_STATE.setError(error);
        movieViewState.postValue(MovieViewState.ERROR_STATE);
    }

    private void onLoading() {
        movieViewState.postValue(MovieViewState.LOADING_STATE);
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
