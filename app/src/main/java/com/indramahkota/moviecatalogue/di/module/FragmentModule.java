package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MovieFragment contributeMovieListFragment();

    @ContributesAndroidInjector
    abstract TvShowFragment contributeTvListFragment();
}
