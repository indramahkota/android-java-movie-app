package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LanguageViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<LanguageResponse>> languageViewState = new MutableLiveData<>();

    @Inject
    LanguageViewModel(MovieCatalogueRepository repository, ObservableSchedulers observableSchedulers) {
        this.repository = repository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource<LanguageResponse>> getLanguageViewState() {
        return languageViewState;
    }

    public void loadLanguages() {
        languageViewState.postValue(Resource.loading(new LanguageResponse()));
        disposable.add(repository.loadLanguages()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(List<Language> language) {
        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setResults(language);

        languageViewState.postValue(Resource.success(languageResponse));
    }

    private void onError(Throwable error) {
        languageViewState.postValue(Resource.error(String.valueOf(error), new LanguageResponse()));
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
