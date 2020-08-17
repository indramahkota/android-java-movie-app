package com.indramahkota.moviecatalogue.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;

import java.util.List;

import javax.inject.Inject;

public class LanguageViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    LanguageViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<LanguageEntity>>> getLanguages() {
        return repository.loadLanguage();
    }
}
