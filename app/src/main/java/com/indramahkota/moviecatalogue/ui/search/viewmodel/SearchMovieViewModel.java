package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;

import javax.inject.Inject;

public class SearchMovieViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private String stringQuery;
    private final MutableLiveData<Long> pageHandler = new MutableLiveData<>();
    public LiveData<Resource<MovieResponse>> searchMovie = Transformations.switchMap(pageHandler,
            page -> repository.searchListMovie(stringQuery, page));

    @Inject
    SearchMovieViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setQuery(String query) {
        stringQuery = query;
    }

    public void loadMoreMovies(Long currentPage) {
        pageHandler.setValue(currentPage);
    }

    public boolean isLastPage() {
        boolean lastPage = false;
        if (searchMovie.getValue() != null &&
                searchMovie.getValue().data != null &&
                searchMovie.getValue().data.getPage() != null &&
                searchMovie.getValue().data.getTotalPages() != null) {
            lastPage = searchMovie.getValue().data.getPage() >= searchMovie.getValue().data.getTotalPages();
        }
        return lastPage;
    }
}
