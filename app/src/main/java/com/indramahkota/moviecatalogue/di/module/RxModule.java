package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.SingleSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    SingleSchedulers providesSingleScheduler() {
        return SingleSchedulers.DEFAULT;
    }

    @Provides
    ObservableSchedulers providesObservableScheduler() {
        return ObservableSchedulers.DEFAULT;
    }
}
