package com.indramahkota.moviecatalogue.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.database.FavoriteDatabase;

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
    AppDao provideFavoriteDao(@NonNull FavoriteDatabase favoriteDatabase) {
        return favoriteDatabase.favoriteDao();
    }
}
