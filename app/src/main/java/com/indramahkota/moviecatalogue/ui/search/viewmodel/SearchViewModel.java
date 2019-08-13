package com.indramahkota.moviecatalogue.ui.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private MutableLiveData<String> queryHandler = new MutableLiveData<>();

    public LiveData<Resource<List<MovieEntity>>> searchMovie = Transformations.switchMap(queryHandler,
            searchQuery -> repository.searchListMovie(searchQuery));

    public LiveData<Resource<List<TvShowEntity>>> searchTvShow = Transformations.switchMap(queryHandler,
            searchQuery -> repository.searchListTvShow(searchQuery));

    @Inject
    SearchViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void setQuery(String query) {
        queryHandler.setValue(query);
    }
}
