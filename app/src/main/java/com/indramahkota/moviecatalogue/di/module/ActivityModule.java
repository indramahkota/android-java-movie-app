package com.indramahkota.moviecatalogue.di.module;


import com.indramahkota.moviecatalogue.ui.detail.MovieDetailsActivity;
import com.indramahkota.moviecatalogue.ui.detail.TvShowDetailsActivity;
import com.indramahkota.moviecatalogue.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract MovieDetailsActivity bindMovieDetailsActivity();

    @ContributesAndroidInjector()
    abstract TvShowDetailsActivity bindTvShowDetailsActivity();
}
