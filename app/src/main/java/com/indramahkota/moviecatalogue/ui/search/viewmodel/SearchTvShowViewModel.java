package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import javax.inject.Inject;

public class SearchTvShowViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private String stringQuery;
    private final MutableLiveData<Long> pageHandler = new MutableLiveData<>();
    public LiveData<Resource<TvShowResponse>> searchTvShow = Transformations.switchMap(pageHandler,
            page -> repository.searchListTvShow(stringQuery, page));

    @Inject
    SearchTvShowViewModel(MovieCatalogueRepository repository) {
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
        if (searchTvShow.getValue() != null &&
                searchTvShow.getValue().data != null &&
                searchTvShow.getValue().data.getPage() != null &&
                searchTvShow.getValue().data.getTotalPages() != null) {
            lastPage = searchTvShow.getValue().data.getPage() >= searchTvShow.getValue().data.getTotalPages();
        }
        return lastPage;
    }
}
