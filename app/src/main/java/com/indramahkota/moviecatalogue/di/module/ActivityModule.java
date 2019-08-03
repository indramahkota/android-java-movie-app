package com.indramahkota.moviecatalogue.di.module;


import com.indramahkota.moviecatalogue.testing.SingleFragmentActivity;
import com.indramahkota.moviecatalogue.ui.detail.MovieDetailsActivity;
import com.indramahkota.moviecatalogue.ui.detail.TvShowDetailsActivity;
import com.indramahkota.moviecatalogue.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract SingleFragmentActivity contributeSingleFragmentActivityInjector();

    @ContributesAndroidInjector()
    abstract MovieDetailsActivity contributeMovieDetailsActivityInjector();

    @ContributesAndroidInjector()
    abstract TvShowDetailsActivity contributeTvShowDetailsActivityInjector();
}
