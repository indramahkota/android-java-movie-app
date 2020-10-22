package com.indramahkota.moviecatalogue.data.source.locale.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Genres;

import java.lang.reflect.Type;
import java.util.List;

public class GenresTypeConverter {

    @TypeConverter
    public List<Genres> fromString(String value) {
        Type listType = new TypeToken<List<Genres>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromList(List<Genres> genres) {
        return new Gson().toJson(genres);
    }
}
