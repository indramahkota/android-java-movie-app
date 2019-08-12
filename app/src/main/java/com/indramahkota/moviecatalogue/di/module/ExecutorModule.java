package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ExecutorModule {
    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
