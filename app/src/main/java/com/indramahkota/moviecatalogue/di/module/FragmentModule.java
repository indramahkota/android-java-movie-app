package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.ui.main.fragment.FavoriteFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.favorite.FavoriteMovieFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MovieFragment contributeMovieListFragment();

    @ContributesAndroidInjector
    abstract TvShowFragment contributeTvListFragment();

    @ContributesAndroidInjector
    abstract FavoriteFragment contributeFavoriteFragment();

    @ContributesAndroidInjector
    abstract FavoriteMovieFragment contributeFavoriteMovieFragment();
}
