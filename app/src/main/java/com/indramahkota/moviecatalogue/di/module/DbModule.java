package com.indramahkota.moviecatalogue.di.module;

import android.app.Application;

import androidx.annotation.NonNull;

import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.database.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    @Provides
    @Singleton
    MyDatabase provideDatabase(@NonNull Application application) {
        return MyDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    AppDao provideFavoriteDao(@NonNull MyDatabase myDatabase) {
        return myDatabase.appDao();
    }
}
