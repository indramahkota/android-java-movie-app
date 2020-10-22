package com.indramahkota.moviecatalogue.ui.main.fragment.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.ui.main.fragment.pagedlist.MoviePagedListAdapter;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteMovieViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavoriteMovieFragment extends DaggerFragment {
    private static final String STATE_SCROLL = "state_scroll";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private LinearLayoutManager linearLayoutManager;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Integer scrollPosition = 0;
    private RelativeLayout relativeLayout;
    private MoviePagedListAdapter favoriteMovieAdapter;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            // Aksi di bawah digunakan untuk melakukan swap ke kenan dan ke kiri
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                // Sebelum melakukan penghapusan, course harus mendapatkan posisi dari item yang di swipe
                int swipedPosition = viewHolder.getAdapterPosition();

                // Kemudian memanggil CourseEntity sesuai posisi ketika diswipe
                MovieEntity movieEntity = favoriteMovieAdapter.getItemById(swipedPosition);
                movieEntity.setFavorite(!movieEntity.getFavorite());

                // Melakukan setBookmark untuk menghapus bookmark dari list course
                favoriteMovieViewModel.updateMovie(movieEntity);

                movieEntity.setFavorite(!movieEntity.getFavorite());

                // Memanggil Snackbar untuk melakukan pengecekan, apakah benar melakukan penghapusan bookmark
                Snackbar snackbar = Snackbar.make(getView(), R.string.message_undo, Snackbar.LENGTH_LONG);

                // Mengembalikan item yang terhapus
                snackbar.setAction(R.string.message_ok, v -> favoriteMovieViewModel.updateMovie(movieEntity));

                // Menampilkan snackbar
                snackbar.show();
            }
        }
    });

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
        RecyclerView rvMovies = view.findViewById(R.id.rv_category);
        rvMovies.setLayoutManager(linearLayoutManager);
        rvMovies.setHasFixedSize(true);
        itemTouchHelper.attachToRecyclerView(rvMovies);

        favoriteMovieAdapter = new MoviePagedListAdapter(getContext());
        rvMovies.setAdapter(favoriteMovieAdapter);

        relativeLayout = view.findViewById(R.id.favorite_empty_indicator);
        relativeLayout.setVisibility(View.GONE);

        favoriteMovieViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getListMovie().observe(getViewLifecycleOwner(), movies -> {
            if (movies.data != null) {
                switch (movies.status) {
                    case LOADING:
                        mShimmerViewContainer.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        mShimmerViewContainer.setVisibility(View.GONE);
                        favoriteMovieAdapter.submitList(movies.data);
                        favoriteMovieAdapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(scrollPosition);
                        break;
                    case ERROR:
                        mShimmerViewContainer.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        break;
                }

                if (movies.data.size() < 1) {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
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