package com.indramahkota.moviecatalogue;

import com.indramahkota.moviecatalogue.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MovieCatalogueApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.factory().create(this);
    }
}
