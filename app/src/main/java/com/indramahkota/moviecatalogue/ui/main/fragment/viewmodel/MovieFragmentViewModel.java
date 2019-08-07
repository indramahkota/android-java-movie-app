package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverMovieResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<DiscoverMovieResponseState> movieViewState = new MutableLiveData<>();

    @Inject
    MovieFragmentViewModel(MovieCatalogueRepository repository, SingleSchedulers singleSchedulers) {
        this.repository = repository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<DiscoverMovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovie() {
        disposable.add(repository.loadListMovie()
                .doOnEvent((movieResponse, throwable) -> onLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverMovieResponse discoverMovieResponse) {
        DiscoverMovieResponseState.SUCCESS_STATE.setData(discoverMovieResponse);
        movieViewState.postValue(DiscoverMovieResponseState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        DiscoverMovieResponseState.ERROR_STATE.setError(error);
        movieViewState.postValue(DiscoverMovieResponseState.ERROR_STATE);
    }

    private void onLoading() {
        movieViewState.postValue(DiscoverMovieResponseState.LOADING_STATE);
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
