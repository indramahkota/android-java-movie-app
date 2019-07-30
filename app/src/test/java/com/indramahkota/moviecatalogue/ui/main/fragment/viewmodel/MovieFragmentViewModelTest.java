package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import com.indramahkota.moviecatalogue.data.source.remote.model.DiscoverMovie;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieFragmentViewModelTest {

    private MovieFragmentViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new MovieFragmentViewModel();
    }

    @Test
    public void getListMovie() {
        List<DiscoverMovie> discoverMovies = viewModel.getListMovie();
        assertNotNull(discoverMovies);
        assertEquals(10, discoverMovies.size());
    }
}