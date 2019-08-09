package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    ObservableSchedulers providesObservableScheduler() {
        return ObservableSchedulers.DEFAULT;
    }
}
