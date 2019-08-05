package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.DiscoverTvShow;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TvShowFragment extends Fragment {
    private static final String STATE_DISCOVER_TV_SHOW_RESPONSE = "state_discover_tv_show_response";

    @Inject
    ViewModelFactory viewModelFactory;

    private ArrayList<DiscoverTvShow> discoverTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;

    public TvShowFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
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

        relativeLayout = view.findViewById(R.id.empty_indicator);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_fragment_container);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView rvFragmentTvShows = view.findViewById(R.id.rv_fragment_category);
        rvFragmentTvShows.setLayoutManager(linearLayoutManager);
        rvFragmentTvShows.setHasFixedSize(true);

        TvShowFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowFragmentViewModel.class);
        viewModel.getTvShowViewState().observe(this, tvShowListViewState -> {
            switch (tvShowListViewState.getCurrentState()) {
                case 0:
                    //show loading
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //show data
                    mShimmerViewContainer.setVisibility(View.GONE);
                    discoverTvShows = tvShowListViewState.getData().getResults();

                    if(discoverTvShows.size() < 1) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }

                    TvShowAdapter listTvShowAdapter = new TvShowAdapter(discoverTvShows, getContext());
                    listTvShowAdapter.notifyDataSetChanged();
                    rvFragmentTvShows.setAdapter(listTvShowAdapter);
                    break;
                case -1:
                    //show error
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        if (savedInstanceState != null) {
            discoverTvShows = savedInstanceState.getParcelableArrayList(STATE_DISCOVER_TV_SHOW_RESPONSE);
        }

        if(discoverTvShows != null) {
            TvShowAdapter listTvShowAdapter = new TvShowAdapter(discoverTvShows, getContext());
            listTvShowAdapter.notifyDataSetChanged();
            rvFragmentTvShows.setAdapter(listTvShowAdapter);
            mShimmerViewContainer.setVisibility(View.GONE);
        } else {
            viewModel.loadTvShow();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_DISCOVER_TV_SHOW_RESPONSE, discoverTvShows);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }
}
