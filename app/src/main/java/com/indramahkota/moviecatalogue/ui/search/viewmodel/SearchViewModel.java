package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private MutableLiveData<String> queryHandler = new MutableLiveData<>();

    public LiveData<Resource<DiscoverMovieResponse>> searchMovie = Transformations.switchMap(queryHandler,
            searchQuery -> repository.searchListMovie(searchQuery));

    public LiveData<Resource<DiscoverTvShowResponse>> searchTvShow = Transformations.switchMap(queryHandler,
            searchQuery -> repository.searchListTvShow(searchQuery));

    @Inject
    SearchViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setQuery(String query) {
        queryHandler.setValue(query);
    }
}
