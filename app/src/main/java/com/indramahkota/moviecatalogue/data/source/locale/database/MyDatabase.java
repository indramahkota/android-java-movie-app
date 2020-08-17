package com.indramahkota.moviecatalogue.data.source.locale.database;

import androidx.room.Database;
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
}
