package com.indramahkota.moviecatalogue.data.source.locale.database;

import android.content.Context;

import androidx.annotation.NonNull;
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
    private static MyDatabase INSTANCE;

    public abstract AppDao appDao();

    public static synchronized MyDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    private static MyDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                MyDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }
}
