package com.indramahkota.moviecatalogue.data.source.locale.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Cast;

import java.lang.reflect.Type;
import java.util.List;

public class CastTypeConverter {

    @TypeConverter
    public List<Cast> fromString(String value) {
        Type listType = new TypeToken<List<Cast>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromCast(List<Cast> casts) {
        return new Gson().toJson(casts);
    }
}
