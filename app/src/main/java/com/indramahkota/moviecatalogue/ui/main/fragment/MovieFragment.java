package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.ui.main.adapter.MovieAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.pagination.PaginationScrollListener;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.MovieFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.search.SearchMovieActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MovieFragment extends DaggerFragment {
    private static final String STATE_PAGE = "state_page";
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_DISCOVER_MOVIE_RESPONSE = "state_discover_movie_response";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private boolean isLoading;
    private Long currentPage = 1L;
    private Integer scrollPosition = 0;
    private RecyclerView rvFragmentMovies;
    private List<MovieEntity> discoverMovies;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout relativeLayout;
    private SearchView searchView;
    private View rootView;

    private Toast mToast;

    public MovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getLong(STATE_PAGE);
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
            discoverMovies = savedInstanceState.getParcelableArrayList(STATE_DISCOVER_MOVIE_RESPONSE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tv_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView = view.findViewById(R.id.rv_fragment_category_container);

        relativeLayout = view.findViewById(R.id.empty_indicator);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_fragment_container);

        MovieFragmentViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieFragmentViewModel.class);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvFragmentMovies = view.findViewById(R.id.rv_fragment_category);
        rvFragmentMovies.setLayoutManager(linearLayoutManager);
        rvFragmentMovies.setHasFixedSize(true);

        MovieAdapter listMovieAdapter = new MovieAdapter(new ArrayList<>(), getContext());
        listMovieAdapter.notifyDataSetChanged();
        rvFragmentMovies.setAdapter(listMovieAdapter);

        rvFragmentMovies.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public boolean isLastPage() {
                return viewModel.isLastPage();
            }

            @Override
            public void loadMore() {
                isLoading = true;
                currentPage++;
                viewModel.loadMoreMovies(currentPage);
                showToast(getResources().getString(R.string.page) + " " + currentPage);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent moveToSearchActivity = new Intent(getContext(), SearchMovieActivity.class);
                moveToSearchActivity.putExtra(SearchMovieActivity.EXTRA_SEARCH_QUERY, query);
                startActivity(moveToSearchActivity);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isLoading = true;
            currentPage = 1L;
            listMovieAdapter.clear();
            viewModel.loadMoreMovies(currentPage);
            relativeLayout.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            showToast(getResources().getString(R.string.refresh));
        });

        viewModel.listDiscoverMovie.observe(getViewLifecycleOwner(), discoverMovieResponseResource -> {
            swipeRefreshLayout.setRefreshing(false);
            rvFragmentMovies.setVisibility(View.VISIBLE);

            if (discoverMovieResponseResource.data != null) {
                if (discoverMovies == null) {
                    discoverMovies = new ArrayList<>(discoverMovieResponseResource.data.getResults());
                } else {
                    switch (discoverMovieResponseResource.status) {
                        case SUCCESS:
                            //show data
                            discoverMovies.addAll(discoverMovieResponseResource.data.getResults());
                            listMovieAdapter.addAll(discoverMovieResponseResource.data.getResults());
                            if (discoverMovies.size() < 1) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    if (discoverMovies.size() < 1) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        rvFragmentMovies.setVisibility(View.GONE);
                                        mShimmerViewContainer.setVisibility(View.GONE);
                                    }
                                }, 1000);
                            } else {
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(() -> isLoading = false, 2000);
                            //showToast(getResources().getString(R.string.success));
                            break;
                        case ERROR:
                            //show error
                            if (discoverMovieResponseResource.data.getResults().size() > 1) {
                                discoverMovies.addAll(discoverMovieResponseResource.data.getResults());
                                listMovieAdapter.addAll(discoverMovieResponseResource.data.getResults());
                            } else {
                                if (currentPage > 1)
                                    currentPage--;
                            }
                            if (discoverMovies.size() < 1) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    if (discoverMovies.size() < 1) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        rvFragmentMovies.setVisibility(View.GONE);
                                        mShimmerViewContainer.setVisibility(View.GONE);
                                    }
                                }, 1000);
                            } else {
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(() -> isLoading = false, 2000);
                            //showToast(getResources().getString(R.string.error));
                            break;
                    }
                }
            }
        });

        if (discoverMovies != null) {
            listMovieAdapter.addAll(discoverMovies);
            linearLayoutManager.scrollToPosition(scrollPosition);
            mShimmerViewContainer.setVisibility(View.GONE);
            if (discoverMovies.size() < 1) {
                rvFragmentMovies.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            viewModel.loadMoreMovies(currentPage);
            showToast(getResources().getString(R.string.page) + " " + currentPage);
        }
    }

    public void scrollToTop() {
        rvFragmentMovies.smoothScrollToPosition(0);
    }

    private void showToast(String message) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(STATE_SCROLL, scrollPosition);
        outState.putLong(STATE_PAGE, currentPage);

        if (discoverMovies != null) {
            ArrayList<MovieEntity> helper = new ArrayList<>();
            int len = discoverMovies.size();
            for (int i = 0; i < len; ++i) {
                helper.add(discoverMovies.get(i));
            }
            outState.putParcelableArrayList(STATE_DISCOVER_MOVIE_RESPONSE, helper);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
        searchView.setQuery("", false);
        rootView.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }
}
