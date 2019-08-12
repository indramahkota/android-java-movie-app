package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    MovieCatalogueRepository provideMovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint, AppExecutors exec) {
        return new MovieCatalogueRepository(appDao, apiEndPoint, exec);
    }
}
