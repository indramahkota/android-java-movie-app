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
    public static final String DATABASE_NAME = "favorite_database.db";
    private static FavoriteDatabase INSTANCE;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static FavoriteDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                FavoriteDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }
}
