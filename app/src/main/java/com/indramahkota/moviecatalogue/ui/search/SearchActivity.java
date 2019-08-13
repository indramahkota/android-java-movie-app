package com.indramahkota.moviecatalogue.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.adapter.MovieAdapter;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity {
    public static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_SEARCH_MOVIE_RESPONSE = "state_search_movie_response";
    private static final String STATE_SEARCH_TV_SHOW_RESPONSE = "state_search_tv_show_response";

    private RecyclerView rvSearch;
    private Integer scrollPosition = 0;
    private List<MovieEntity> searchMovies;
    private List<TvShowEntity> searchTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;
    private LinearLayoutManager linearLayoutManager;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
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

        linearLayoutManager = new LinearLayoutManager(this);
        rvSearch = findViewById(R.id.rv_search_category);
        rvSearch.setLayoutManager(linearLayoutManager);
        rvSearch.setHasFixedSize(true);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);

        SearchViewModel searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

        if (searchQuery != null && searchQuery[0] != null) {
            if(searchQuery[0].equals("Movie")) {
                searchViewModel.searchMovie.observe(this, movieViewState -> {
                    switch (movieViewState.status) {
                        case LOADING:
                            //show loading
                            relativeLayout.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            //show data
                            swipeRefreshLayout.setRefreshing(false);
                            rvSearch.setVisibility(View.VISIBLE);
                            searchMovies = movieViewState.data;
                            if (searchMovies != null) {
                                setMovieAdapter(searchMovies);
                                if(searchMovies.size() < 1) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            break;
                        case ERROR:
                            //show error
                            swipeRefreshLayout.setRefreshing(false);
                            relativeLayout.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            } else if(searchQuery[0].equals("Tv Show")){
                searchViewModel.searchTvShow.observe(this, tvShowListViewState -> {
                    switch (tvShowListViewState.status) {
                        case LOADING:
                            //show loading
                            relativeLayout.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            //show data
                            swipeRefreshLayout.setRefreshing(false);
                            rvSearch.setVisibility(View.VISIBLE);
                            searchTvShows = tvShowListViewState.data;
                            if (searchTvShows != null) {
                                setTvSowAdapter(searchTvShows);
                                if(searchTvShows.size() < 1) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            break;
                        case ERROR:
                            //show error
                            swipeRefreshLayout.setRefreshing(false);
                            relativeLayout.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            relativeLayout.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            rvSearch.setVisibility(View.GONE);

            if (searchQuery != null && searchQuery[1] != null) {
                searchViewModel.setQuery(searchQuery[1]);
            }
        });

        if (searchQuery != null && searchQuery[0] != null && searchQuery[1] != null) {
            if(searchQuery[0].equals("Movie")) {
                if(!searchQuery[1].isEmpty()) {
                    if (searchMovies != null) {
                        setMovieAdapter(searchMovies);
                        linearLayoutManager.scrollToPosition(scrollPosition);
                    } else {
                        searchViewModel.setQuery(searchQuery[1]);
                    }
                }
            } else if(searchQuery[0].equals("Tv Show")){
                if(!searchQuery[1].isEmpty()) {
                    if (searchTvShows != null) {
                        setTvSowAdapter(searchTvShows);
                        linearLayoutManager.scrollToPosition(scrollPosition);
                    } else {
                        searchViewModel.setQuery(searchQuery[1]);
                    }
                }
            }
        }
    }

    private void setMovieAdapter(@NonNull List<MovieEntity> disMovies) {
        MovieAdapter listMovieAdapter = new MovieAdapter(disMovies, this);
        listMovieAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(listMovieAdapter);
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private void setTvSowAdapter(@NonNull List<TvShowEntity> disTvShows) {
        TvShowAdapter listTvShowAdapter = new TvShowAdapter(disTvShows, this);
        listTvShowAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(listTvShowAdapter);
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(STATE_SCROLL, scrollPosition);

        if(searchMovies != null) {
            ArrayList<MovieEntity> movieHelper = new ArrayList<>();
            int movieLen = searchMovies.size();
            for(int i = 0; i<movieLen; ++i) {
                movieHelper.add(searchMovies.get(i));
            }
            outState.putParcelableArrayList(STATE_SEARCH_MOVIE_RESPONSE, movieHelper);
        }

        if(searchTvShows != null) {
            ArrayList<TvShowEntity> tvShowHelper = new ArrayList<>();
            int tvLen = searchTvShows.size();
            for(int i = 0; i<tvLen; ++i) {
                tvShowHelper.add(searchTvShows.get(i));
            }
            outState.putParcelableArrayList(STATE_SEARCH_TV_SHOW_RESPONSE, tvShowHelper);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
