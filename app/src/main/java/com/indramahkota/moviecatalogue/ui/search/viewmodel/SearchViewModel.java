package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<DiscoverMovieResponse>> movieViewState = new MutableLiveData<>();
    private final MutableLiveData<Resource<DiscoverTvShowResponse>> tvShowViewState = new MutableLiveData<>();

    @Inject
    SearchViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<DiscoverMovieResponse>> getMovieViewState() {
        return movieViewState;
    }

    public MutableLiveData<Resource<DiscoverTvShowResponse>> getTvShowViewState() {
        return tvShowViewState;
    }

    public void searchMovie(String query) {
        movieViewState.postValue(Resource.loading(new DiscoverMovieResponse()));
        disposable.add(repository.searchListMovie(query)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSearchMovieSuccess,
                        this::onSearchMovieError));
    }

    public void searchTvShow(String query) {
        tvShowViewState.postValue(Resource.loading(new DiscoverTvShowResponse()));
        disposable.add(repository.searchListTvShow(query)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSearchTvShowSuccess,
                        this::onSearchTvShowError));
    }

    private void onSearchMovieSuccess(DiscoverMovieResponse discoverMovieResponse) {
        movieViewState.postValue(Resource.success(discoverMovieResponse));
    }

    private void onSearchMovieError(Throwable error) {
        movieViewState.postValue(Resource.error(String.valueOf(error), new DiscoverMovieResponse()));
    }

    private void onSearchTvShowSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        tvShowViewState.postValue(Resource.success(discoverTvShowResponse));
    }

    private void onSearchTvShowError(Throwable error) {
        tvShowViewState.postValue(Resource.error(String.valueOf(error), new DiscoverTvShowResponse()));
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
