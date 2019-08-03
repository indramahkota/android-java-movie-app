package com.indramahkota.moviecatalogue.data.source.locale.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovie;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;

@Database(entities = {FavoriteMovie.class, FavoriteTvShow.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "favorite_database";
    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, FavoriteDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
