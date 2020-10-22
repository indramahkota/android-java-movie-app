package com.indramahkota.moviecatalogue.data.source.locale.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.indramahkota.moviecatalogue.data.source.locale.converter.CreditsTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.converter.GenresTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class, LanguageEntity.class}, version = 1, exportSchema = false)
@TypeConverters({GenresTypeConverter.class, CreditsTypeConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "my_movie_catalogue_database.db";

    public abstract AppDao appDao();

    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
