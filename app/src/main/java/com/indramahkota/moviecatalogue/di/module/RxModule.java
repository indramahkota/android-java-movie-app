package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    public SingleSchedulers providesScheduler() {
        return SingleSchedulers.DEFAULT;
    }
}
