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
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.pagination.PaginationScrollListener;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.search.SearchTvShowActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TvShowFragment extends DaggerFragment {
    private static final String STATE_PAGE = "state_page";
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_DISCOVER_TV_SHOW_RESPONSE = "state_discover_tv_show_response";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private boolean isLoading;
    private Long currentPage = 1L;
    private Integer scrollPosition = 0;
    private RecyclerView rvFragmentTvShows;
    private List<TvShowEntity> discoverTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout relativeLayout;
    private SearchView searchView;
    private View rootView;

    private Toast mToast;

    public TvShowFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getLong(STATE_PAGE);
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
            discoverTvShows = savedInstanceState.getParcelableArrayList(STATE_DISCOVER_TV_SHOW_RESPONSE);
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

        TvShowFragmentViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(TvShowFragmentViewModel.class);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvFragmentTvShows = view.findViewById(R.id.rv_fragment_category);
        rvFragmentTvShows.setLayoutManager(linearLayoutManager);
        rvFragmentTvShows.setHasFixedSize(true);

        rvFragmentTvShows.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public boolean isLastPage() {
                return viewModel.isLastPage();
            }

            @Override
            public void loadMore() {
                currentPage++;
                isLoading = true;
                viewModel.loadMoreTvShows(currentPage);
                showToast(getResources().getString(R.string.page) + " " + currentPage);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        TvShowAdapter listTvShowAdapter = new TvShowAdapter(new ArrayList<>(), getContext());
        listTvShowAdapter.notifyDataSetChanged();
        rvFragmentTvShows.setAdapter(listTvShowAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent moveToSearchActivity = new Intent(getContext(), SearchTvShowActivity.class);
                moveToSearchActivity.putExtra(SearchTvShowActivity.EXTRA_SEARCH_QUERY, query);
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
            listTvShowAdapter.clear();
            viewModel.loadMoreTvShows(currentPage);
            relativeLayout.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            showToast(getResources().getString(R.string.refresh));
        });

        viewModel.listDiscoverTvShow.observe(getViewLifecycleOwner(), discoverTvShowResponseResource -> {
            swipeRefreshLayout.setRefreshing(false);
            rvFragmentTvShows.setVisibility(View.VISIBLE);

            if (discoverTvShowResponseResource.data != null) {
                if (discoverTvShows == null) {
                    discoverTvShows = new ArrayList<>(discoverTvShowResponseResource.data.getResults());
                } else {
                    switch (discoverTvShowResponseResource.status) {
                        case SUCCESS:
                            //show data
                            discoverTvShows.addAll(discoverTvShowResponseResource.data.getResults());
                            listTvShowAdapter.addAll(discoverTvShowResponseResource.data.getResults());
                            if (discoverTvShows.size() < 1) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    if (discoverTvShows.size() < 1) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        rvFragmentTvShows.setVisibility(View.GONE);
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
                            if (discoverTvShowResponseResource.data.getResults().size() > 1) {
                                discoverTvShows.addAll(discoverTvShowResponseResource.data.getResults());
                                listTvShowAdapter.addAll(discoverTvShowResponseResource.data.getResults());
                            } else {
                                if (currentPage > 1)
                                    currentPage--;
                            }
                            if (discoverTvShows.size() < 1) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    if (discoverTvShows.size() < 1) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        rvFragmentTvShows.setVisibility(View.GONE);
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

        if (discoverTvShows != null) {
            listTvShowAdapter.addAll(discoverTvShows);
            linearLayoutManager.scrollToPosition(scrollPosition);
            mShimmerViewContainer.setVisibility(View.GONE);
            if (discoverTvShows.size() < 1) {
                rvFragmentTvShows.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            viewModel.loadMoreTvShows(currentPage);
            showToast(getResources().getString(R.string.page) + " " + currentPage);
        }
    }

    public void scrollToTop() {
        rvFragmentTvShows.smoothScrollToPosition(0);
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

        if (discoverTvShows != null) {
            ArrayList<TvShowEntity> helper = new ArrayList<>();
            int len = discoverTvShows.size();
            for (int i = 0; i < len; ++i) {
                helper.add(discoverTvShows.get(i));
            }
            outState.putParcelableArrayList(STATE_DISCOVER_TV_SHOW_RESPONSE, helper);
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
