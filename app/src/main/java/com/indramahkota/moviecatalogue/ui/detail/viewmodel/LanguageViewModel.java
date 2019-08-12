package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LanguageViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final MovieCatalogueRepository repository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<Resource<LanguageResponse>> languageViewState = new MutableLiveData<>();
    private final MutableLiveData<Resource<LanguageResponse>> languageData = new MutableLiveData<>();

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
        languageViewState.postValue(Resource.loading(new LanguageResponse(new ArrayList<>())));
        disposable.add(repository.loadLanguages()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(List<LanguageEntity> language) {
        LanguageResponse languageResponse = new LanguageResponse(new ArrayList<>());
        languageResponse.setResults(language);

        languageViewState.postValue(Resource.success(languageResponse));
    }

    private void onError(Throwable error) {
        languageViewState.postValue(Resource.error(String.valueOf(error), new LanguageResponse(new ArrayList<>())));
    }

    public MutableLiveData<Resource<LanguageResponse>> getLanguageData() {
        return languageData;
    }

    public void fetchLanguage(String iso) {
        disposable.add(repository.loadLanguage(iso)
                .compose(observableSchedulers.applySchedulers())
                .subscribe(resource -> {
                            if(resource.isLoading()) {
                                languageData.postValue(Resource.loading(new LanguageResponse(new ArrayList<>())));
                            } else if(resource.isSuccess()){
                                languageData.postValue(resource);
                            }
                        }));
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
