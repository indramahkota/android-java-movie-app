package com.indramahkota.moviecatalogue.data.source.locale.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Credits;

import java.lang.reflect.Type;

public class CreditsTypeConverter {

    @TypeConverter
    public Credits fromString(String value) {
        Type listType = new TypeToken<Credits>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromCredits(Credits credits) {
        return new Gson().toJson(credits);
    }
}
