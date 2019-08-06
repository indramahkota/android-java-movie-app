package com.indramahkota.moviecatalogue.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.ui.main.fragment.FavoriteFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;
import com.indramahkota.moviecatalogue.ui.search.SearchActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String STATE_MODE = "state_mode";

    private int mode;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private MovieFragment mMovieFragment;
    private TvShowFragment mTvShowFragment;
    private FavoriteFragment mFavoriteFragment;

    private SearchView searchView;
    private View rootView;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        rootView = findViewById(R.id.container);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent moveToSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
                String state;
                if (mode == R.id.navigation_movie)
                    state = "Movie";
                else
                    state = "Tv Show";

                String[] extraData = {state, query};
                moveToSearchActivity.putExtra(SearchActivity.EXTRA_SEARCH_QUERY, extraData);
                startActivity(moveToSearchActivity);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            setTitle(R.string.list_movies);
            mode = R.id.navigation_movie;
            showMovieFragment();
        } else {
            setMode(savedInstanceState.getInt(STATE_MODE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setQuery("", false);
        rootView.requestFocus();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_MODE, mode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> setMode(item.getItemId());

    private boolean setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.navigation_movie:
                mode = R.id.navigation_movie;
                setTitle(R.string.list_movies);
                searchView.setVisibility(View.VISIBLE);
                showMovieFragment();
                return true;
            case R.id.navigation_tv_show:
                mode = R.id.navigation_tv_show;
                setTitle(R.string.list_tv_shows);
                searchView.setVisibility(View.VISIBLE);
                showTvShowFragment();
                return true;
            case R.id.navigation_favorite:
                mode = R.id.navigation_favorite;
                setTitle(R.string.list_favoritess);
                showFavoriteFragment();
                searchView.setVisibility(View.GONE);
                return true;
        }
        return false;
    }

    private void showMovieFragment() {
        if(mMovieFragment == null) {
            mMovieFragment = new MovieFragment();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
        if (!(fragment instanceof MovieFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mMovieFragment, MovieFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }

    private void showTvShowFragment() {
        if(mTvShowFragment == null) {
            mTvShowFragment = new TvShowFragment();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TvShowFragment.class.getSimpleName());
        if (!(fragment instanceof TvShowFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mTvShowFragment, TvShowFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }

    private void showFavoriteFragment() {
        if(mFavoriteFragment == null) {
            mFavoriteFragment = new FavoriteFragment();
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(FavoriteFragment.class.getSimpleName());
        if (!(fragment instanceof FavoriteFragment)) {
            mFragmentTransaction.replace(R.id.frame_container, mFavoriteFragment, FavoriteFragment.class.getSimpleName());
            mFragmentTransaction.commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
