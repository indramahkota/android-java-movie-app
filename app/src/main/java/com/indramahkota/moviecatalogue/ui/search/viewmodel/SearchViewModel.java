package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverMovieResponseState;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverTvShowResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<DiscoverMovieResponseState> movieViewState = new MutableLiveData<>();
    private final MutableLiveData<DiscoverTvShowResponseState> tvShowViewState = new MutableLiveData<>();

    @Inject
    SearchViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<DiscoverMovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public MutableLiveData<DiscoverTvShowResponseState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void searchMovie(String query) {
        movieViewState.postValue(DiscoverMovieResponseState.LOADING_STATE);
        disposable.add(repository.searchListMovie(query)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSearchMovieSuccess,
                        this::onSearchMovieError));
    }

    public void searchTvShow(String query) {
        tvShowViewState.postValue(DiscoverTvShowResponseState.LOADING_STATE);
        disposable.add(repository.searchListTvShow(query)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSearchTvShowSuccess,
                        this::onSearchTvShowError));
    }

    private void onSearchMovieSuccess(DiscoverMovieResponse discoverMovieResponse) {
        DiscoverMovieResponseState.SUCCESS_STATE.setData(discoverMovieResponse);
        movieViewState.postValue(DiscoverMovieResponseState.SUCCESS_STATE);
    }

    private void onSearchMovieError(Throwable error) {
        DiscoverMovieResponseState.ERROR_STATE.setError(error);
        movieViewState.postValue(DiscoverMovieResponseState.ERROR_STATE);
    }

    private void onSearchTvShowSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        DiscoverTvShowResponseState.SUCCESS_STATE.setData(discoverTvShowResponse);
        tvShowViewState.postValue(DiscoverTvShowResponseState.SUCCESS_STATE);
    }

    private void onSearchTvShowError(Throwable error) {
        DiscoverTvShowResponseState.ERROR_STATE.setError(error);
        tvShowViewState.postValue(DiscoverTvShowResponseState.ERROR_STATE);
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
