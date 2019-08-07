package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.MovieResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<MovieResponseState> movieViewState = new MutableLiveData<>();

    @Inject
    MovieDetailsViewModel(MovieCatalogueRepository repository, SingleSchedulers singleSchedulers) {
        this.repository = repository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovieDetails(Long movieId) {
        disposable.add(repository.loadMovieDetails(movieId)
                .doOnEvent((movieResponse, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(MovieEntity movieEntity) {
        MovieResponseState.SUCCESS_STATE.setData(movieEntity);
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
