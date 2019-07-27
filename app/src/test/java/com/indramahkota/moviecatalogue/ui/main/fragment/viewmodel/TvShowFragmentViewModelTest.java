package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import com.indramahkota.moviecatalogue.data.model.DiscoverTvShow;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TvShowFragmentViewModelTest {
    private TvShowFragmentViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TvShowFragmentViewModel();
    }

    @Test
    public void getListTvShow() {
        List<DiscoverTvShow> discoverTvShows = viewModel.getListTvShow();
        assertNotNull(discoverTvShows);
        assertEquals(10, discoverTvShows.size());
    }
}