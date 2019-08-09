package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

import javax.inject.Inject;

public class FavoriteTvShowViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;
    private LiveData<List<TvShowEntity>> listTvShow;

    @Inject
    FavoriteTvShowViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
        listTvShow = repository.getAllTvShow();
    }

    public LiveData<List<TvShowEntity>> getListTvShow() {
        return listTvShow;
    }

    public LiveData<TvShowEntity> getTvShow(Long ln) {
        return repository.getTvShowById(ln);
    }

    public void insertTvShow(TvShowEntity tvShowEntity) {
        repository.insertTvShow(tvShowEntity);
    }

    public void deleteTvShow(Long itemId) {
        repository.deleteTvShowById(itemId);
    }
}
