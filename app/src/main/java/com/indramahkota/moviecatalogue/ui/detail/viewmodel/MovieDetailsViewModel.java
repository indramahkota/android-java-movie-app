package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.MovieResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<MovieResponseState> movieViewState = new MutableLiveData<>();

    @Inject
    MovieDetailsViewModel(RemoteRepository remoteRepository, SingleSchedulers singleSchedulers) {
        this.remoteRepository = remoteRepository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovieDetails(Integer movieId) {
        disposable.add(remoteRepository.loadMovieDetails(movieId)
                .doOnEvent((movieResponse, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(MovieResponse movieResponse) {
        MovieResponseState.SUCCESS_STATE.setData(movieResponse);
        movieViewState.postValue(MovieResponseState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        MovieResponseState.ERROR_STATE.setError(error);
        movieViewState.postValue(MovieResponseState.ERROR_STATE);
    }

    private void onLoading() {
        movieViewState.postValue(MovieResponseState.LOADING_STATE);
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
