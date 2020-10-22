package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;

    @Inject
    FavoriteMovieViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getListMovie() {
        return repository.getAllMovie();
    }

    public void updateMovie(MovieEntity movieEntity) {
        repository.updateMovie(movieEntity);
    }
}
