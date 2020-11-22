package com.indramahkota.moviecatalogue.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    ApiEndPoint provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL_TMDB)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiEndPoint.class);
    }
}
