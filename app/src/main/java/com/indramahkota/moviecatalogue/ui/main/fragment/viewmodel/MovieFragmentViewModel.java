package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieFragmentViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<DiscoverMovieResponse>> movieViewState = new MutableLiveData<>();

    @Inject
    MovieFragmentViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<DiscoverMovieResponse>> getMovieViewState() {
        return movieViewState;
    }

    public void loadMovie() {
        movieViewState.postValue(Resource.loading(new DiscoverMovieResponse()));
        disposable.add(repository.loadListMovie()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(DiscoverMovieResponse discoverMovieResponse) {
        movieViewState.postValue(Resource.success(discoverMovieResponse));
    }

    private void onError(Throwable error) {
        movieViewState.postValue(Resource.error(String.valueOf(error), new DiscoverMovieResponse()));
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
