package com.indramahkota.moviecatalogue.data.source.remote;

import androidx.annotation.NonNull;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovie;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;

    private RemoteRepository() { }

    public static RemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository();
        }
        return INSTANCE;
    }

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
                if (response.body() != null) {
                    discoverMovieResponse[0] = response.body();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return discoverMovieResponse[0].getResults();
    }
}
