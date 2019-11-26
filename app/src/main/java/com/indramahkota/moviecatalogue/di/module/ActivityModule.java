package com.indramahkota.moviecatalogue.di.module;


import com.indramahkota.moviecatalogue.testing.SingleFragmentActivity;
import com.indramahkota.moviecatalogue.ui.detail.MovieDetailsActivity;
import com.indramahkota.moviecatalogue.ui.detail.TvShowDetailsActivity;
import com.indramahkota.moviecatalogue.ui.main.MainActivity;
import com.indramahkota.moviecatalogue.ui.search.SearchMovieActivity;
import com.indramahkota.moviecatalogue.ui.search.SearchTvShowActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity mainActivityInjector();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract SingleFragmentActivity singleFragmentActivityInjector();

    @ContributesAndroidInjector()
    abstract MovieDetailsActivity movieDetailsActivityInjector();

    @ContributesAndroidInjector()
    abstract TvShowDetailsActivity tvShowDetailsActivityInjector();

    @ContributesAndroidInjector()
    abstract SearchMovieActivity searchMovieActivityInjector();

    @ContributesAndroidInjector()
    abstract SearchTvShowActivity searchTvShowActivityInjector();
}
