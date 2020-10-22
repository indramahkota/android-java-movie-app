package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;

import javax.inject.Inject;

public class MovieFragmentViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private final MutableLiveData<Long> pageHandler = new MutableLiveData<>();
    public LiveData<Resource<MovieResponse>> listDiscoverMovie = Transformations.switchMap(pageHandler,
            page -> repository.loadListMovie(page));

    @Inject
    MovieFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void loadMoreMovies(Long currentPage) {
        pageHandler.setValue(currentPage);
    }

    public boolean isLastPage() {
        boolean lastPage = false;
        if (listDiscoverMovie.getValue() != null &&
                listDiscoverMovie.getValue().data != null &&
                listDiscoverMovie.getValue().data.getPage() != null &&
                listDiscoverMovie.getValue().data.getTotalPages() != null) {
            lastPage = listDiscoverMovie.getValue().data.getPage() >= listDiscoverMovie.getValue().data.getTotalPages();
        }
        return lastPage;
    }
}
