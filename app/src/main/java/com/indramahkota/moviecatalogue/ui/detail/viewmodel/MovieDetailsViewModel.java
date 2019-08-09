package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.MovieResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<MovieResponseState> movieViewState = new MutableLiveData<>();

    @Inject
    MovieDetailsViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovieDetails(Long movieId) {
        movieViewState.postValue(MovieResponseState.LOADING_STATE);
        disposable.add(repository.loadMovieDetails(movieId)
                .compose(observableSchedulers.applySchedulers())
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

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
