package com.indramahkota.moviecatalogue.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.DiscoverMovie;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.DiscoverTvShow;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.adapter.MovieAdapter;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity {
    public static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    private static final String STATE_SEARCH_MOVIE_RESPONSE = "state_search_movie_response";
    private static final String STATE_SEARCH_TV_SHOW_RESPONSE = "state_search_tv_show_response";

    @Inject
    ViewModelFactory viewModelFactory;

    private ArrayList<DiscoverMovie> searchMovies;
    private ArrayList<DiscoverTvShow> searchTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            searchTvShows = savedInstanceState.getParcelableArrayList(STATE_SEARCH_TV_SHOW_RESPONSE);
            searchMovies = savedInstanceState.getParcelableArrayList(STATE_SEARCH_MOVIE_RESPONSE);
        }

        String[] searchQuery = getIntent().getStringArrayExtra(EXTRA_SEARCH_QUERY);
        if (searchQuery != null && searchQuery[0] != null && !searchQuery[0].isEmpty()) {
            if(searchQuery[0].equals("Movie")) {
                setTitle(R.string.search_list_movies);
            } else {
                setTitle(R.string.search_list_tv_shows);
            }
        }

        relativeLayout = findViewById(R.id.empty_indicator);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_fragment_container);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView rvSearch = findViewById(R.id.rv_search_category);
        rvSearch.setLayoutManager(linearLayoutManager);
        rvSearch.setHasFixedSize(true);

        SearchViewModel searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        searchViewModel.getMovieViewState().observe(this, movieViewState -> {
            switch (movieViewState.getCurrentState()) {
                case 0:
                    //show loading
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //show data
                    mShimmerViewContainer.setVisibility(View.GONE);
                    searchMovies = movieViewState.getData().getResults();

                    if(searchMovies.size() < 1) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }

                    MovieAdapter listMovieAdapter = new MovieAdapter(searchMovies, this);
                    listMovieAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(listMovieAdapter);
                    break;
                case -1:
                    //show error
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        searchViewModel.getTvShowViewState().observe(this, tvShowListViewState -> {
            switch (tvShowListViewState.getCurrentState()) {
                case 0:
                    //show loading
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //show data
                    mShimmerViewContainer.setVisibility(View.GONE);
                    searchTvShows = tvShowListViewState.getData().getResults();

                    if(searchTvShows.size() < 1) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }

                    TvShowAdapter listTvShowAdapter = new TvShowAdapter(searchTvShows, this);
                    listTvShowAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(listTvShowAdapter);
                    break;
                case -1:
                    //show error
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        if (searchQuery != null && searchQuery[1] != null && !searchQuery[1].isEmpty()) {
            if(searchQuery[0].equals("Movie")) {
                if(searchMovies != null) {
                    MovieAdapter listMovieAdapter = new MovieAdapter(searchMovies, this);
                    listMovieAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(listMovieAdapter);
                    mShimmerViewContainer.setVisibility(View.GONE);
                } else {
                    searchViewModel.searchMovie(searchQuery[1]);
                }
            } else {
                if(searchTvShows != null) {
                    TvShowAdapter listTvShowAdapter = new TvShowAdapter(searchTvShows, this);
                    listTvShowAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(listTvShowAdapter);
                    mShimmerViewContainer.setVisibility(View.GONE);
                } else {
                    searchViewModel.searchTvShow(searchQuery[1]);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_SEARCH_MOVIE_RESPONSE, searchMovies);
        outState.putParcelableArrayList(STATE_SEARCH_TV_SHOW_RESPONSE, searchTvShows);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
