package com.indramahkota.moviecatalogue.di.module;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    MovieCatalogueRepository provideMovieCatalogueRepository(FavoriteDao favoriteDao, ApiEndPoint apiEndPoint) {
        return new MovieCatalogueRepository(favoriteDao, apiEndPoint);
    }
}
