package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import javax.inject.Inject;

public class FavoriteTvShowViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    FavoriteTvShowViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> getListTvShow() {
        return repository.getAllTvShow();
    }

    public void updateTvShow(TvShowEntity tvShowEntity) {
        repository.updateTvShow(tvShowEntity);
    }
}
