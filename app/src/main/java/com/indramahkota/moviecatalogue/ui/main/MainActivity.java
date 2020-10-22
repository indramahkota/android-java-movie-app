package com.indramahkota.moviecatalogue.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.ui.main.fragment.FavoriteFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;
import com.indramahkota.moviecatalogue.ui.setting.SettingsActivity;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String STATE_MODE = "state_mode";
    private static final String SCROLL_STATE_MOVIE = "movie_rv_scroll_state";
    private static final String FRAGMENT_STATE_MOVIE = "movie_fragment_state";
    private static final String SCROLL_STATE_TV_SHOW = "tv_show_rv_scroll_state";
    private static final String FRAGMENT_STATE_TV_SHOW = "tv_show_fragment_state";

    private int mode;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private MovieFragment mMovieFragment;
    private TvShowFragment mTvShowFragment;
    private FavoriteFragment mFavoriteFragment;

    private int movieCounter = 0;
    private int tvShowCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            setTitle(R.string.list_movies);
            mode = R.id.navigation_movie;
            movieCounter++;
            showMovieFragment();
        } else {
            setMode(savedInstanceState.getInt(STATE_MODE));
            movieCounter = savedInstanceState.getInt(SCROLL_STATE_MOVIE);
            tvShowCounter = savedInstanceState.getInt(SCROLL_STATE_TV_SHOW);

            if (mode == R.id.navigation_movie) {
                mMovieFragment = (MovieFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE_MOVIE);
            } else if (mode == R.id.navigation_tv_show) {
                mTvShowFragment = (TvShowFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE_TV_SHOW);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_MODE, mode);
        outState.putInt(SCROLL_STATE_MOVIE, movieCounter);
        outState.putInt(SCROLL_STATE_TV_SHOW, tvShowCounter);

        if (mode == R.id.navigation_movie) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_STATE_MOVIE, mMovieFragment);
        } else if (mode == R.id.navigation_tv_show) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_STATE_TV_SHOW, mTvShowFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent mIntent = new Intent(this, SettingsActivity.class);
            startActivity(mIntent);
        }
        return true;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> setMode(item.getItemId());

    private boolean setMode(int selectedMode) {
        if (selectedMode == R.id.navigation_movie) {
            mode = R.id.navigation_movie;
            setTitle(R.string.list_movies);
            movieCounter++;
            tvShowCounter = 0;
            showMovieFragment();
            return true;
        } else if (selectedMode == R.id.navigation_tv_show) {
            mode = R.id.navigation_tv_show;
            setTitle(R.string.list_tv_shows);
            tvShowCounter++;
            movieCounter = 0;
            showTvShowFragment();
            return true;
        } else if (selectedMode == R.id.navigation_favorite) {
            mode = R.id.navigation_favorite;
            setTitle(R.string.list_favorites);
            movieCounter = 0;
            tvShowCounter = 0;
            showFavoriteFragment();
            return true;
        }
        return false;
    }

    private void showMovieFragment() {
        if (mMovieFragment == null) {
            mMovieFragment = new MovieFragment();
        }

        if (movieCounter > 1) {
            mMovieFragment.scrollToTop();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
        if (!(fragment instanceof MovieFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mMovieFragment, MovieFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }

    private void showTvShowFragment() {
        if (mTvShowFragment == null) {
            mTvShowFragment = new TvShowFragment();
        }

        if (tvShowCounter > 1) {
            mTvShowFragment.scrollToTop();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TvShowFragment.class.getSimpleName());
        if (!(fragment instanceof TvShowFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mTvShowFragment, TvShowFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }

    private void showFavoriteFragment() {
        if (mFavoriteFragment == null) {
            mFavoriteFragment = new FavoriteFragment();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(FavoriteFragment.class.getSimpleName());
        if (!(fragment instanceof FavoriteFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mFavoriteFragment, FavoriteFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }
}
