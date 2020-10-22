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
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.pagination.PaginationScrollListener;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchTvShowViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SearchTvShowActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_SEARCH_QUERY = "extra_search_query";
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_SEARCH_TV_SHOW_RESPONSE = "state_search_tv_show_response";

    private boolean isLoading;
    private Long currentPage = 1L;
    private RecyclerView rvSearch;
    private Integer scrollPosition = 0;
    private List<TvShowEntity> searchTvShows;
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
            searchTvShows = savedInstanceState.getParcelableArrayList(STATE_SEARCH_TV_SHOW_RESPONSE);
        }

        SearchTvShowViewModel searchViewModel = new ViewModelProvider(this, viewModelFactory).get(SearchTvShowViewModel.class);

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

        TvShowAdapter listTvShowAdapter = new TvShowAdapter(new ArrayList<>(), this);
        listTvShowAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(listTvShowAdapter);

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
            listTvShowAdapter.clear();

            relativeLayout.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);

            if (searchQuery != null && !searchQuery.isEmpty()) {
                searchViewModel.loadMoreMovies(currentPage);
            }

            showToast(getResources().getString(R.string.refresh));
        });

        searchViewModel.searchTvShow.observe(this, tvShowListViewState -> {
            switch (tvShowListViewState.status) {
                case SUCCESS:
                    //show data
                    swipeRefreshLayout.setRefreshing(false);
                    rvSearch.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    if (tvShowListViewState.data != null) {
                        if (searchTvShows == null) {
                            searchTvShows = new ArrayList<>(tvShowListViewState.data.getResults());
                        } else {
                            searchTvShows.addAll(tvShowListViewState.data.getResults());
                        }
                        listTvShowAdapter.addAll(tvShowListViewState.data.getResults());
                        isLoading = false;

                        if (searchTvShows.size() < 1) {
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
            if (searchTvShows != null) {
                listTvShowAdapter.addAll(searchTvShows);
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

        if (searchTvShows != null) {
            ArrayList<TvShowEntity> tvShowHelper = new ArrayList<>();
            int tvLen = searchTvShows.size();
            for (int i = 0; i < tvLen; ++i) {
                tvShowHelper.add(searchTvShows.get(i));
            }
            outState.putParcelableArrayList(STATE_SEARCH_TV_SHOW_RESPONSE, tvShowHelper);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
