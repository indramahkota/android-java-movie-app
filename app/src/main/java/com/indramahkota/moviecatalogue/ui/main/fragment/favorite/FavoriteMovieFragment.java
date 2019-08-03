package com.indramahkota.moviecatalogue.ui.main.fragment.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indramahkota.moviecatalogue.R;

public class FavoriteMovieFragment extends Fragment {
    private static final String STATE_SCROLL = "state_scroll";

    private LinearLayoutManager linearLayoutManager;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Integer scrollPosition = 0;
    private RecyclerView rvMovies;
    private RelativeLayout relativeLayout;

    public static FavoriteMovieFragment newInstance(String title) {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(STATE_SCROLL, scrollPosition);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.GONE);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvMovies = view.findViewById(R.id.rv_category);
        rvMovies.setLayoutManager(linearLayoutManager);
        rvMovies.setHasFixedSize(true);

        relativeLayout = view.findViewById(R.id.favorite_empty_indicator);
        relativeLayout.setVisibility(View.GONE);

        /*FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getListFavoriteMovie().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favMovies) {
                FavoriteMovieAdapter favoriteMovieAdapter = new FavoriteMovieAdapter(favMovies, getContext());
                rvMovies.setAdapter(favoriteMovieAdapter);
                linearLayoutManager.scrollToPosition(scrollPosition);
                mShimmerViewContainer.setVisibility(View.GONE);

                if(favMovies.size() > 0) {
                    relativeLayout.setVisibility(View.GONE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });*/
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