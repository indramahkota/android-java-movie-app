package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.Resource;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;

import javax.inject.Inject;

public class TvShowFragmentViewModel extends ViewModel {
    private MovieCatalogueRepository repository;

    private final MutableLiveData<Long> pageHandler = new MutableLiveData<>();
    public LiveData<Resource<TvShowResponse>> listDiscoverTvShow = Transformations.switchMap(pageHandler,
            page -> repository.loadListTvShow(page));

    @Inject
    TvShowFragmentViewModel(MovieCatalogueRepository repository) {
        this.repository = repository;
    }

    public void loadMoreTvShows(Long currentPage) {
        pageHandler.setValue(currentPage);
    }

    public boolean isLastPage() {
        boolean lastPage = false;
        if (listDiscoverTvShow.getValue() != null &&
                listDiscoverTvShow.getValue().data != null &&
                listDiscoverTvShow.getValue().data.getPage() != null &&
                listDiscoverTvShow.getValue().data.getTotalPages() != null) {
            lastPage = listDiscoverTvShow.getValue().data.getPage() >= listDiscoverTvShow.getValue().data.getTotalPages();
        }
        return lastPage;
    }
}
