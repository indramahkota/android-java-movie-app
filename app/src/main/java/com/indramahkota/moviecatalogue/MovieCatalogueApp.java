package com.indramahkota.moviecatalogue;

import android.app.Activity;
import android.app.Application;

import com.indramahkota.moviecatalogue.di.component.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class MovieCatalogueApp extends Application  /*implements HasActivityInjector*/ {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    /*@Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }*/
}
