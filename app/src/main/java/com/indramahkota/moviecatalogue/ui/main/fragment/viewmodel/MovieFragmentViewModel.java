package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.ui.main.fragment.state.MovieViewState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<MovieViewState> movieViewState = new MutableLiveData<>();

    @Inject
    MovieFragmentViewModel(RemoteRepository remoteRepository, SingleSchedulers singleSchedulers) {
        this.remoteRepository = remoteRepository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieViewState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovie() {
        disposable.add(remoteRepository.loadListMovie()
                .doOnEvent((newsList, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
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
