package com.indramahkota.moviecatalogue.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.database.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.indramahkota.moviecatalogue.data.source.locale.database.MyDatabase.DATABASE_NAME;

@Module
public class DbModule {
    @Provides
    @Singleton
    MyDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    AppDao provideFavoriteDao(@NonNull MyDatabase myDatabase) {
        return myDatabase.appDao();
    }
}
