package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.remote.RxSingleSchedulers;
import com.indramahkota.moviecatalogue.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @AppScope
    @Provides
    public RxSingleSchedulers providesScheduler() {
        return RxSingleSchedulers.DEFAULT;
    }
}
