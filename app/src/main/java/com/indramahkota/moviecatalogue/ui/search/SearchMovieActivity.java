package com.indramahkota.moviecatalogue.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.ui.main.adapter.MovieAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.pagination.PaginationScrollListener;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchMovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SearchMovieActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_SEARCH_MOVIE_RESPONSE = "state_search_movie_response";

    private boolean isLoading;
    private Long currentPage = 1L;
    private RecyclerView rvSearch;
    private Integer scrollPosition = 0;
    private List<MovieEntity> searchMovies;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;
    private LinearLayoutManager linearLayoutManager;

    private Toast mToast;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
            searchMovies = savedInstanceState.getParcelableArrayList(STATE_SEARCH_MOVIE_RESPONSE);
        }

        SearchMovieViewModel searchViewModel = new ViewModelProvider(this, viewModelFactory).get(SearchMovieViewModel.class);

        String searchQuery = getIntent().getStringExtra(EXTRA_SEARCH_QUERY);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchViewModel.setQuery(searchQuery);
        }

        relativeLayout = findViewById(R.id.empty_indicator);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_fragment_container);

        linearLayoutManager = new LinearLayoutManager(this);
        rvSearch = findViewById(R.id.rv_search_category);
        rvSearch.setLayoutManager(linearLayoutManager);
        rvSearch.setHasFixedSize(true);

        MovieAdapter listMovieAdapter = new MovieAdapter(new ArrayList<>(), this);
        listMovieAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(listMovieAdapter);

        rvSearch.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public boolean isLastPage() {
                return searchViewModel.isLastPage();
            }

            @Override
            public void loadMore() {
                currentPage++;
                isLoading = true;
                searchViewModel.loadMoreMovies(currentPage);
                showToast(getResources().getString(R.string.page) + " " + currentPage);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1L;
            listMovieAdapter.clear();

            relativeLayout.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);

            if (searchQuery != null && !searchQuery.isEmpty()) {
                searchViewModel.loadMoreMovies(currentPage);
            }

            showToast(getResources().getString(R.string.refresh));
        });

        searchViewModel.searchMovie.observe(this, movieViewState -> {
            switch (movieViewState.status) {
                case SUCCESS:
                    //show data
                    swipeRefreshLayout.setRefreshing(false);
                    rvSearch.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    if (movieViewState.data != null) {
                        if (searchMovies == null) {
                            searchMovies = new ArrayList<>(movieViewState.data.getResults());
                        } else {
                            searchMovies.addAll(movieViewState.data.getResults());
                        }
                        listMovieAdapter.addAll(movieViewState.data.getResults());
                        isLoading = false;

                        if (searchMovies.size() < 1) {
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case ERROR:
                    //show error
                    swipeRefreshLayout.setRefreshing(false);
                    showToast(getResources().getString(R.string.error));
                    break;
            }
        });

        if (searchQuery != null && !searchQuery.isEmpty()) {
            if (searchMovies != null) {
                listMovieAdapter.addAll(searchMovies);
                linearLayoutManager.scrollToPosition(scrollPosition);
                mShimmerViewContainer.setVisibility(View.GONE);
            } else {
                searchViewModel.loadMoreMovies(currentPage);
                showToast(getResources().getString(R.string.page) + " " + currentPage);
            }
        }
    }

    private void showToast(String message) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(STATE_SCROLL, scrollPosition);

        if (searchMovies != null) {
            ArrayList<MovieEntity> movieHelper = new ArrayList<>();
            int movieLen = searchMovies.size();
            for (int i = 0; i < movieLen; ++i) {
                movieHelper.add(searchMovies.get(i));
            }
            outState.putParcelableArrayList(STATE_SEARCH_MOVIE_RESPONSE, movieHelper);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
