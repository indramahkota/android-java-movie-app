package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.TmdbViewModelFactory;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovie;
import com.indramahkota.moviecatalogue.ui.main.adapter.MovieAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.MovieFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieFragment extends Fragment {
    @Inject
    TmdbViewModelFactory tmdbViewModelFactory;

    private RecyclerView rvMovies;

    public MovieFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tv_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvMovies = view.findViewById(R.id.rv_fragment_category);
        rvMovies.setLayoutManager(linearLayoutManager);
        rvMovies.setHasFixedSize(true);

        MovieFragmentViewModel viewModel = ViewModelProviders.of(this, tmdbViewModelFactory).get(MovieFragmentViewModel.class);
        MovieAdapter listMovieAdapter = new MovieAdapter(new ArrayList<>(), getContext());
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);
        viewModel.getMovieViewState().observe(this, newsListViewState -> {
            switch (newsListViewState.getCurrentState()) {
                case 0:
                    //binding.setShowLoading(true);
                    break;
                case 1:
                    //binding.setShowLoading(false);
                    listMovieAdapter.addNewsList(newsListViewState.getData().getResults());
                    break;
                case -1: // show error
                    //binding.setShowLoading(false);
                    break;
            }
        });
        viewModel.loadMovie();
    }
}
