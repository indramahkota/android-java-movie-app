package com.indramahkota.moviecatalogue.di.module;

import com.google.gson.Gson;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiConstant;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @AppScope
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder().baseUrl(ApiConstant.BASE_URL_TMDB)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @AppScope
    @Provides
    ApiEndPoint provideTmdbApi(Retrofit retrofit) {
        return retrofit.create(ApiEndPoint.class);
    }
}
