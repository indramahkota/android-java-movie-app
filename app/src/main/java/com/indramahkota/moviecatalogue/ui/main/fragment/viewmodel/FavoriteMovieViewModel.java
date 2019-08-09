package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;

import java.util.List;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel {
    private final MovieCatalogueRepository repository;
    private LiveData<List<MovieEntity>> listMovie;

    @Inject
    FavoriteMovieViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
        listMovie = repository.getAllMovie();
    }

    public LiveData<List<MovieEntity>> getListMovie() {
        return listMovie;
    }

    public LiveData<MovieEntity> getMovie(Long ln) {
        return repository.getMovieById(ln);
    }

    public void insertMovie(MovieEntity movieEntity) {
        repository.insertMovie(movieEntity);
    }

    public void deleteMovie(Long itemId) {
        repository.deleteMovieById(itemId);
    }
}
