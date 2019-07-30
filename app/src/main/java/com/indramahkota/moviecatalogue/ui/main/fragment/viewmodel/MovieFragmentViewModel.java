package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.RemoteRepository;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovie;

import java.util.List;

public class MovieFragmentViewModel extends ViewModel {
    private RemoteRepository remoteRepository = new RemoteRepository();

    public List<DiscoverMovie> getListMovie() {
        return remoteRepository.loadListMovie();
    }
}
