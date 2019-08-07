package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.ui.detail.datastate.LanguageResponseState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LanguageViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final RemoteRepository remoteRepository;
    private final ObservableSchedulers observableSchedulers;
    private final MutableLiveData<LanguageResponseState> languageViewState = new MutableLiveData<>();

    @Inject
    LanguageViewModel(RemoteRepository remoteRepository, ObservableSchedulers observableSchedulers) {
        this.remoteRepository = remoteRepository;
        this.observableSchedulers = observableSchedulers;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<LanguageResponseState> getLanguageViewState() {
        return languageViewState;
    }

    public void loadLanguages() {
        languageViewState.postValue(LanguageResponseState.LOADING_STATE);
        disposable.add(remoteRepository.loadLanguages()
                .compose(observableSchedulers.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError));
    }

    private void onSuccess(List<Language> language) {
        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setResults(language);

        LanguageResponseState.SUCCESS_STATE.setData(languageResponse);
        languageViewState.postValue(LanguageResponseState.SUCCESS_STATE);
    }

    private void onError(Throwable error) {
        LanguageResponseState.ERROR_STATE.setError(error);
        languageViewState.postValue(LanguageResponseState.ERROR_STATE);
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
