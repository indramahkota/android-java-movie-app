package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    FavoriteMovieViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<PagedList<MovieEntity>> getListMovie() {
        return new LivePagedListBuilder<>(repository.getAllMovie(), 20).build();
    }

    public void updateMovie(MovieEntity movieEntity) {repository.updateMovie(movieEntity);}
}
