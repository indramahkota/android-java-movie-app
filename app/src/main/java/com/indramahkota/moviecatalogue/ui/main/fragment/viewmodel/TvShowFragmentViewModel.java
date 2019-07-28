package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.remote.DataDummy;
import com.indramahkota.moviecatalogue.data.remote.model.DiscoverTvShow;

import java.util.List;

public class TvShowFragmentViewModel extends ViewModel {
    public List<DiscoverTvShow> getListTvShow() {
        return DataDummy.generateDummyTvShows();
    }
}
