package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverMovieResponseState;
import com.indramahkota.moviecatalogue.ui.main.fragment.datastate.DiscoverTvShowResponseState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final SingleSchedulers singleSchedulers;
    private final MutableLiveData<DiscoverMovieResponseState> movieViewState = new MutableLiveData<>();
    private final MutableLiveData<DiscoverTvShowResponseState> tvShowViewState = new MutableLiveData<>();

    @Inject
    SearchViewModel(RemoteRepository remoteRepository, SingleSchedulers singleSchedulers) {
        this.remoteRepository = remoteRepository;
        this.singleSchedulers = singleSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<DiscoverMovieResponseState> getMovieViewState() {
        return movieViewState;
    }

    public MutableLiveData<DiscoverTvShowResponseState> getTvShowViewState() {
        return tvShowViewState;
    }

    public void searchMovie(String query) {
        disposable.add(remoteRepository.searchListMovie(query)
                .doOnEvent((movieResponse, throwable) -> onSearchMovieLoading())
                .compose(singleSchedulers.applySchedulers())
                .subscribe(this::onSearchMovieSuccess,
                        this::onSearchMovieError));
    }

    public void searchTvShow(String query) {
        disposable.add(remoteRepository.searchListTvShow(query)
                .doOnEvent((tvShowResponse, throwable) -> onSearchTvShowLoading())
                .compose(singleSchedulers.applySchedulers())
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

    private void onSearchMovieLoading() {
        movieViewState.postValue(DiscoverMovieResponseState.LOADING_STATE);
    }

    private void onSearchTvShowSuccess(DiscoverTvShowResponse discoverTvShowResponse) {
        DiscoverTvShowResponseState.SUCCESS_STATE.setData(discoverTvShowResponse);
        tvShowViewState.postValue(DiscoverTvShowResponseState.SUCCESS_STATE);
    }

    private void onSearchTvShowError(Throwable error) {
        DiscoverTvShowResponseState.ERROR_STATE.setError(error);
        tvShowViewState.postValue(DiscoverTvShowResponseState.ERROR_STATE);
    }

    private void onSearchTvShowLoading() {
        tvShowViewState.postValue(DiscoverTvShowResponseState.LOADING_STATE);
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
