package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.remote.DataDummy;
import com.indramahkota.moviecatalogue.data.source.remote.model.DiscoverMovie;

import java.util.List;

public class MovieFragmentViewModel extends ViewModel {
    public List<DiscoverMovie> getListMovie() {
        return DataDummy.generateDummyMovies();
    }
}
