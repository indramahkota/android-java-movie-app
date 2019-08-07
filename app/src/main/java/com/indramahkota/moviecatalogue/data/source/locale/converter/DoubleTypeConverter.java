package com.indramahkota.moviecatalogue.data.source.locale.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DoubleTypeConverter {

    @TypeConverter
    public Double fromString(String value) {
        Type listType = new TypeToken<Double>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromList(Double dbl) {
        return new Gson().toJson(dbl);
    }
}
