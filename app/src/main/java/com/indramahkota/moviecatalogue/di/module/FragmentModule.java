package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.ui.main.fragment.FavoriteFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.MovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.TvShowFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.favorite.FavoriteMovieFragment;
import com.indramahkota.moviecatalogue.ui.main.fragment.favorite.FavoriteTvShowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MovieFragment movieFragmentInjector();

    @ContributesAndroidInjector
    abstract TvShowFragment tvShowFragmentInjector();

    @ContributesAndroidInjector
    abstract FavoriteFragment favoriteFragmentInjector();

    @ContributesAndroidInjector
    abstract FavoriteMovieFragment favoriteMovieFragmentInjector();

    @ContributesAndroidInjector
    abstract FavoriteTvShowFragment favoriteTvShowFragmentInjector();
}
