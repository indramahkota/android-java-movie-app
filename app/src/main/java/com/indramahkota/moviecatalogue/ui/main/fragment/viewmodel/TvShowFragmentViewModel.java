package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShow;

import java.util.List;

public class TvShowFragmentViewModel extends ViewModel {
    private RemoteRepository remoteRepository = new RemoteRepository();
    public List<DiscoverTvShow> getListTvShow() {
        return remoteRepository.loadListTvShow();
    }
}
