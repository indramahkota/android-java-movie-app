package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<MovieEntity>> movieViewState = new MutableLiveData<>();

    @Inject
    MovieDetailsViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<MovieEntity>> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovieDetails(Long movieId) {
        movieViewState.postValue(Resource.loading(new MovieEntity()));
        disposable.add(repository.loadMovieDetails(movieId)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(MovieEntity movieEntity) {
        movieViewState.postValue(Resource.success(movieEntity));
    }

    private void onError(Throwable error) {
        movieViewState.postValue(Resource.error(String.valueOf(error), new MovieEntity()));
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
