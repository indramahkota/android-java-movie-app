package com.indramahkota.moviecatalogue.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.indramahkota.moviecatalogue.R;
import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;

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

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            showMovieFragment();
        } else {
            setMode(savedInstanceState.getInt(STATE_MODE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_MODE, mode);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> setMode(item.getItemId());

    private boolean setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.navigation_movie:
                mode = R.id.navigation_movie;
                setTitle(R.string.list_movies);
                showMovieFragment();
                return true;
            case R.id.navigation_tv_show:
                mode = R.id.navigation_tv_show;
                setTitle(R.string.list_tv_shows);
                showTvShowFragment();
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
