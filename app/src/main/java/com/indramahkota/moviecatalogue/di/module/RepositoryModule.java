package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.rxscheduler.ObservableSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    MovieCatalogueRepository provideMovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint, ObservableSchedulers observableSchedulers) {
        return new MovieCatalogueRepository(appDao, apiEndPoint, observableSchedulers);
    }
}
