package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.remote.DataDummy;
import com.indramahkota.moviecatalogue.data.remote.model.DiscoverMovie;

import java.util.List;

public class MovieFragmentViewModel extends ViewModel {
    public List<DiscoverMovie> getListMovie() {
        return DataDummy.generateDummyMovies();
    }
}
