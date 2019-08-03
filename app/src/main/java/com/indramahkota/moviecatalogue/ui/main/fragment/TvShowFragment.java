package com.indramahkota.moviecatalogue.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.EspressoIdlingResource;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.DiscoverTvShow;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.adapter.TvShowAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TvShowFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;

    private List<DiscoverTvShow> discoverTvShows;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout relativeLayout;

    public TvShowFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tv_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EspressoIdlingResource.increment();

        relativeLayout = view.findViewById(R.id.empty_indicator);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_fragment_container);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView rvTvShows = view.findViewById(R.id.rv_fragment_category);
        rvTvShows.setLayoutManager(linearLayoutManager);
        rvTvShows.setHasFixedSize(true);

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
                    relativeLayout.setVisibility(View.GONE);
                    mShimmerViewContainer.setVisibility(View.GONE);

                    discoverTvShows = tvShowListViewState.getData().getResults();
                    TvShowAdapter listTvShowAdapter = new TvShowAdapter(discoverTvShows, getContext());
                    listTvShowAdapter.notifyDataSetChanged();
                    rvTvShows.setAdapter(listTvShowAdapter);

                    EspressoIdlingResource.decrement();
                    break;
                case -1:
                    //show error
                    relativeLayout.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    break;
            }
        });

        if(discoverTvShows != null) {
            TvShowAdapter listTvShowAdapter = new TvShowAdapter(discoverTvShows, getContext());
            listTvShowAdapter.notifyDataSetChanged();
            rvTvShows.setAdapter(listTvShowAdapter);
            mShimmerViewContainer.setVisibility(View.GONE);
        } else {
            viewModel.loadTvShow();
        }
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
