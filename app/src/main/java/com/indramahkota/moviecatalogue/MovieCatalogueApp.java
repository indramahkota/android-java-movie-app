package com.indramahkota.moviecatalogue;

import android.app.Activity;
import android.app.Application;

import com.indramahkota.moviecatalogue.di.component.DaggerAppComponent;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.DbModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MovieCatalogueApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .dbModule(new DbModule())
                .build()
                .inject(this);
    }
}
