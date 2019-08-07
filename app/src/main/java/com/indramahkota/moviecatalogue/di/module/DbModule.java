package com.indramahkota.moviecatalogue.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.database.FavoriteDatabase;
import com.indramahkota.moviecatalogue.data.source.locale.repository.LocalRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.indramahkota.moviecatalogue.data.source.locale.database.FavoriteDatabase.DATABASE_NAME;

@Module
public class DbModule {
    @Provides
    @Singleton
    FavoriteDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                FavoriteDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    FavoriteDao provideFavoriteDao(@NonNull FavoriteDatabase favoriteDatabase) {
        return favoriteDatabase.favoriteDao();
    }

    @Provides
    @Singleton
    LocalRepository provideLocalRepository(FavoriteDao favoriteDao) {
        return new LocalRepository(favoriteDao);
    }
}
