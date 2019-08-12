package com.indramahkota.moviecatalogue.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MovieCatalogueRepository implements MovieCatalogueDataSource {
    private final AppDao dao;
    private final ApiEndPoint api;
    private final AppExecutors exec;

    private CompositeDisposable disposable;

    @Inject
    public MovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint, AppExecutors exec){
        this.dao = appDao;
        this.api = apiEndPoint;
        this.exec = exec;
    }

    /*
     * Main Activity
     * */

    //MovieFragmentViewModel
    @Override
    public LiveData<ApiResponse<DiscoverMovieResponse>> loadListMovie() {
        MutableLiveData<ApiResponse<DiscoverMovieResponse>> result = new MutableLiveData<>();
        Call<DiscoverMovieResponse> call = api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverMovieResponse> call, @NonNull Response<DiscoverMovieResponse> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    //TvShowFragmentViewModel
    @Override
    public LiveData<ApiResponse<DiscoverTvShowResponse>> loadListTvShow() {
        MutableLiveData<ApiResponse<DiscoverTvShowResponse>> result = new MutableLiveData<>();
        Call<DiscoverTvShowResponse> call = api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<DiscoverTvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Response<DiscoverTvShowResponse> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<List<MovieEntity>> getAllMovie() {
        return dao.selectAllMovie();
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<MovieEntity> getMovieById(Long id) {
        return dao.selectMovieById(id);
    }

    //FavoriteMovieViewModel
    @Override
    public void insertMovie(MovieEntity movieEntity) {
        dao.insertMovie(movieEntity);
    }

    //FavoriteMovieViewModel
    @Override
    public void deleteMovieById(long id) {
        dao.deleteMovieById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<List<TvShowEntity>> getAllTvShow() {
        return dao.selectAllTvShow();
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<TvShowEntity> getTvShowById(Long id) {
        return dao.selectTvShowById(id);
    }

    //FavoriteTvShowViewModel
    @Override
    public void insertTvShow(TvShowEntity tvShowEntity) {
        dao.insertTvShow(tvShowEntity);
    }

    //FavoriteTvShowViewModel
    @Override
    public void deleteTvShowById(long id) {
        dao.deleteTvShowById(id);
    }

    /*
     * Detail Activity
     * */

    @Override
    public LiveData<ApiResponse<MovieEntity>> loadMovieDetails(Long movieId) {
        MutableLiveData<ApiResponse<MovieEntity>> result = new MutableLiveData<>();
        Call<MovieEntity> call = api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(@NonNull Call<MovieEntity> call, @NonNull Response<MovieEntity> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieEntity> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    @Override
    public LiveData<ApiResponse<TvShowEntity>> loadTvShowDetails(Long tvShowId) {
        MutableLiveData<ApiResponse<TvShowEntity>> result = new MutableLiveData<>();
        Call<TvShowEntity> call = api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
        call.enqueue(new Callback<TvShowEntity>() {
            @Override
            public void onResponse(@NonNull Call<TvShowEntity> call, @NonNull Response<TvShowEntity> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<TvShowEntity> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    /*@Override
    public LiveData<List<LanguageEntity>> loadLanguages() {
        return api.getLanguages(BuildConfig.TMDB_API_KEY);
    }*/

    /*
     * Search Activity
     * */

    @Override
    public LiveData<ApiResponse<DiscoverMovieResponse>> searchListMovie(String query) {
        MutableLiveData<ApiResponse<DiscoverMovieResponse>> result = new MutableLiveData<>();
        Call<DiscoverMovieResponse> call = api.searchMovies(BuildConfig.TMDB_API_KEY, query);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverMovieResponse> call, @NonNull Response<DiscoverMovieResponse> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    @Override
    public LiveData<ApiResponse<DiscoverTvShowResponse>> searchListTvShow(String query) {
        MutableLiveData<ApiResponse<DiscoverTvShowResponse>> result = new MutableLiveData<>();
        Call<DiscoverTvShowResponse> call = api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
        call.enqueue(new Callback<DiscoverTvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Response<DiscoverTvShowResponse> response) {
                Objects.requireNonNull(response.body(), "Must not be null");
                result.setValue(ApiResponse.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                Objects.requireNonNull(t.getMessage(), "Must not be null");
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<List<LanguageEntity>>> loadLanguage() {
        return new NetworkBoundResource<List<LanguageEntity>, List<LanguageEntity>>(exec) {
            @Override
            public LiveData<List<LanguageEntity>> loadFromDB() {
                return dao.selectAllLanguage();
            }

            @Override
            public Boolean shouldFetch(List<LanguageEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            public LiveData<ApiResponse<List<LanguageEntity>>> createCall() {
                MutableLiveData<ApiResponse<List<LanguageEntity>>> resultLanguage = new MutableLiveData<>();
                Call<List<LanguageEntity>> call = api.getLanguages(BuildConfig.TMDB_API_KEY);
                call.enqueue(new Callback<List<LanguageEntity>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<LanguageEntity>> call, @NonNull Response<List<LanguageEntity>> response) {
                        Objects.requireNonNull(response.body(), "Must not be null");
                        resultLanguage.setValue(ApiResponse.success(response.body()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<LanguageEntity>> call, @NonNull Throwable t) {
                        Objects.requireNonNull(t.getMessage(), "Must not be null");
                    }
                });
                return resultLanguage;
            }

            @Override
            public void saveCallResult(List<LanguageEntity> lisLanguage) {
                dao.insertLanguages(lisLanguage);
            }
        }.asLiveData();
    }
}
