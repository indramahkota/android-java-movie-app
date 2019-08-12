package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.search.SearchActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TvShowFragment extends Fragment {
    private static final String STATE_SCROLL = "state_scroll";
    private static final String STATE_DISCOVER_TV_SHOW_RESPONSE = "state_discover_tv_show_response";

    @Inject
    ViewModelFactory viewModelFactory;

    private Integer scrollPosition = 0;
    private RecyclerView rvFragmentTvShows;
    private DiscoverTvShowResponse discoverTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    private View rootView;

    public TvShowFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
            discoverTvShows = savedInstanceState.getParcelable(STATE_DISCOVER_TV_SHOW_RESPONSE);
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

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvFragmentTvShows = view.findViewById(R.id.rv_fragment_category);
        rvFragmentTvShows.setLayoutManager(linearLayoutManager);
        rvFragmentTvShows.setHasFixedSize(true);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent moveToSearchActivity = new Intent(getContext(), SearchActivity.class);
                String[] extraData = {"Tv Show", query};
                moveToSearchActivity.putExtra(SearchActivity.EXTRA_SEARCH_QUERY, extraData);
                startActivity(moveToSearchActivity);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);

        TvShowFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowFragmentViewModel.class);
        viewModel.listDiscoverTvShow.observe(this, tvShowListViewState -> {
            switch (tvShowListViewState.status) {
                case LOADING:
                    //show loading
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    //show data
                    swipeRefreshLayout.setRefreshing(false);
                    rvFragmentTvShows.setVisibility(View.VISIBLE);
                    discoverTvShows = tvShowListViewState.data;
                    if (discoverTvShows != null) {
                        setAdapter(discoverTvShows);
                        if(discoverTvShows.getResults().size() < 1) {
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case ERROR:
                    //show error
                    swipeRefreshLayout.setRefreshing(false);
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            relativeLayout.setVisibility(View.GONE);
            rvFragmentTvShows.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            viewModel.setRefreshId("refresh");
        });

        if(discoverTvShows != null) {
            setAdapter(discoverTvShows);
            linearLayoutManager.scrollToPosition(scrollPosition);
        } else {
            viewModel.setRefreshId("start");
        }
    }

    private void setAdapter(@NonNull DiscoverTvShowResponse disTvShows) {
        TvShowAdapter listTvShowAdapter = new TvShowAdapter(disTvShows.getResults(), getContext());
        listTvShowAdapter.notifyDataSetChanged();
        rvFragmentTvShows.setAdapter(listTvShowAdapter);
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(STATE_SCROLL, scrollPosition);
        outState.putParcelable(STATE_DISCOVER_TV_SHOW_RESPONSE, discoverTvShows);
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
