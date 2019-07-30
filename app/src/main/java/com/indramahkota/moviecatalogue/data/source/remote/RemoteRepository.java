package com.indramahkota.moviecatalogue.data.source.remote;

import androidx.annotation.NonNull;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovie;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShow;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {

    public RemoteRepository() { }

    public List<DiscoverMovie> loadListMovie() {
        final DiscoverMovieResponse[] discoverMovieResponse = new DiscoverMovieResponse[1];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL_TMDB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndPoint apiEndPoint = retrofit.create(ApiEndPoint.class);
        Call<DiscoverMovieResponse> call = apiEndPoint.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverMovieResponse> call, @NonNull Response<DiscoverMovieResponse> response) {
                discoverMovieResponse[0] = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) { }
        });
        return discoverMovieResponse[0].getResults();
    }

    public List<DiscoverTvShow> loadListTvShow() {
        final DiscoverTvShowResponse[] discoverTvShowResponse = new DiscoverTvShowResponse[1];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL_TMDB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndPoint apiEndPoint = retrofit.create(ApiEndPoint.class);
        Call<DiscoverTvShowResponse> call = apiEndPoint.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<DiscoverTvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Response<DiscoverTvShowResponse> response) {
                discoverTvShowResponse[0] = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) { }
        });
        return discoverTvShowResponse[0].getResults();
    }
}
