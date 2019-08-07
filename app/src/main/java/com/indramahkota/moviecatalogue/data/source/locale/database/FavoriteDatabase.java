package com.indramahkota.moviecatalogue.data.source.locale.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.indramahkota.moviecatalogue.data.source.locale.converter.CastsTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.converter.CreditsTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.converter.DoubleTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.converter.GenresTypeConverter;
import com.indramahkota.moviecatalogue.data.source.locale.dao.FavoriteDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;

@Database(entities = {FavoriteMovieEntity.class, FavoriteTvShowEntity.class,
        MovieEntity.class, TvShowEntity.class}, version = 1, exportSchema = false)
@TypeConverters({CastsTypeConverter.class, GenresTypeConverter.class, CreditsTypeConverter.class, DoubleTypeConverter.class})
public abstract class FavoriteDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "my_movie_catalogue_database.db";
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

    @NonNull
    private static FavoriteDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                FavoriteDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }
}
