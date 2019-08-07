package com.indramahkota.moviecatalogue.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.repository.RemoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder().baseUrl(ApiConstant.BASE_URL_TMDB)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiEndPoint provideTmdbApi(Retrofit retrofit) {
        return retrofit.create(ApiEndPoint.class);
    }

    @Provides
    @Singleton
    RemoteRepository provideRemoteRepository(ApiEndPoint apiEndPoint) {
        return new RemoteRepository(apiEndPoint);
    }
}
