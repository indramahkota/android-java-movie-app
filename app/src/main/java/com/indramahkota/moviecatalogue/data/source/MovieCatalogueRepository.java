package com.indramahkota.moviecatalogue.data.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.EspressoIdlingResource;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.ApiResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.MovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MovieCatalogueRepository implements MovieCatalogueDataSource {
    private static final String TAG = "REPOSITORY";

    private final AppDao dao;
    private final ApiEndPoint api;
    private final AppExecutors exec;

    @Inject
    public MovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint, AppExecutors exec) {
        this.dao = appDao;
        this.api = apiEndPoint;
        this.exec = exec;
    }

    /*
     * Main Activity
     * */

    //MovieFragmentViewModel
    @Override
    public LiveData<Resource<MovieResponse>> loadListMovie(Long page) {
        return new NetworkBoundResource<MovieResponse, MovieResponse>(exec) {
            @Override
            protected LiveData<MovieResponse> loadFromDB() {
                MutableLiveData<MovieResponse> dbResourceLiveData = new MutableLiveData<>();

                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setPage(page);
                movieResponse.setResults(dao.selectAllMovie(page));
                dbResourceLiveData.setValue(movieResponse);

                return dbResourceLiveData;
            }

            @Override
            protected Boolean shouldFetch(MovieResponse data) {

                return true;
            }

            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                MutableLiveData<ApiResponse<MovieResponse>> resultMovie = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Log.d(TAG, "Load: loadListMovie");
                Log.d(TAG, "Load: loadListMovie Page: " + page);

                Call<MovieResponse> call = api.getDiscoverMovies(BuildConfig.TMDB_API_KEY, page);
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            List<MovieEntity> helper = new ArrayList<>();
                            for (MovieEntity movieEntity : response.body().getResults()) {
                                MovieEntity storedMovieEntity = dao.selectMovieById(movieEntity.getId());
                                if (storedMovieEntity == null) {
                                    movieEntity.setFavorite(false);
                                } else {
                                    movieEntity.setFavorite(storedMovieEntity.getFavorite());
                                }
                                movieEntity.setPage(page);
                                helper.add(movieEntity);
                            }
                            MovieResponse movieResponse = new MovieResponse();
                            movieResponse.setPage(response.body().getPage());
                            movieResponse.setTotalPages(response.body().getTotalPages());
                            movieResponse.setResults(helper);
                            resultMovie.postValue(ApiResponse.success(movieResponse));

                            EspressoIdlingResource.decrement();

                            Log.d(TAG, "Complete: loadListMovie");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new MovieResponse()));

                        EspressoIdlingResource.decrement();

                        Log.d(TAG, "Error: loadListMovie");
                    }
                });
                return resultMovie;
            }

            @Override
            protected void saveCallResult(MovieResponse data) {
                dao.insertMovies(data.getResults());
            }
        }.asLiveData();
    }

    //TvShowFragmentViewModel
    @Override
    public LiveData<Resource<TvShowResponse>> loadListTvShow(Long page) {
        return new NetworkBoundResource<TvShowResponse, TvShowResponse>(exec) {
            @Override
            protected LiveData<TvShowResponse> loadFromDB() {
                MutableLiveData<TvShowResponse> dbResourceLiveData = new MutableLiveData<>();

                TvShowResponse tvShowResponse = new TvShowResponse();
                tvShowResponse.setPage(page);
                tvShowResponse.setResults(dao.selectAllTvShow(page));
                dbResourceLiveData.setValue(tvShowResponse);

                return dbResourceLiveData;
            }

            @Override
            protected Boolean shouldFetch(TvShowResponse data) {
                return true;
            }

            @Override
            protected LiveData<ApiResponse<TvShowResponse>> createCall() {
                MutableLiveData<ApiResponse<TvShowResponse>> resultTvShow = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Log.d(TAG, "Load: loadListTvShow");
                Log.d(TAG, "Load: loadListTvShow Page: " + page);

                Call<TvShowResponse> call = api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY, page);
                call.enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.body() != null) {
                            List<TvShowEntity> helper = new ArrayList<>();
                            for (TvShowEntity tvShowEntity : response.body().getResults()) {
                                TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowEntity.getId());
                                if (storedTvShowEntity == null) {
                                    tvShowEntity.setFavorite(false);
                                } else {
                                    tvShowEntity.setFavorite(storedTvShowEntity.getFavorite());
                                }
                                tvShowEntity.setPage(page);
                                helper.add(tvShowEntity);
                            }
                            TvShowResponse tvShowResponse = new TvShowResponse();
                            tvShowResponse.setPage(response.body().getPage());
                            tvShowResponse.setTotalPages(response.body().getTotalPages());
                            tvShowResponse.setResults(helper);
                            resultTvShow.postValue(ApiResponse.success(tvShowResponse));

                            EspressoIdlingResource.decrement();

                            Log.d(TAG, "Complete: loadListTvShow");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new TvShowResponse()));

                        EspressoIdlingResource.decrement();

                        Log.d(TAG, "Error: loadListTvShow");
                    }
                });
                return resultTvShow;
            }

            @Override
            protected void saveCallResult(TvShowResponse data) {
                dao.insertTvShows(data.getResults());
            }
        }.asLiveData();
    }

    //FavoriteMovieViewModel
    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getAllMovie() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(exec) {
            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(dao.selectAllFavoriteMovie(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<MovieEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<MovieEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public void updateMovie(MovieEntity movieEntity) {
        dao.updateMovie(movieEntity);
    }

    //FavoriteTvShowViewModel
    @Override
    public LiveData<Resource<PagedList<TvShowEntity>>> getAllTvShow() {
        return new NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(exec) {
            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(dao.selectAllFavoriteTvShow(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShowEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<TvShowEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<TvShowEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public void updateTvShow(TvShowEntity tvShowEntity) {
        dao.updateTvShow(tvShowEntity);
    }

    /*
     * Detail Activity
     * */
    //Movie Details
    @Override
    public LiveData<Resource<MovieEntity>> loadMovieDetails(Long movieId) {
        return new NetworkBoundResource<MovieEntity, MovieEntity>(exec) {
            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                MutableLiveData<MovieEntity> movieFromDb = new MutableLiveData<>();
                movieFromDb.setValue(dao.selectMovieById(movieId));
                return movieFromDb;
            }

            @Override
            public Boolean shouldFetch(MovieEntity data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<MovieEntity>> createCall() {
                MutableLiveData<ApiResponse<MovieEntity>> resultMovie = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Call<MovieEntity> call = api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
                call.enqueue(new Callback<MovieEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieEntity> call, @NonNull Response<MovieEntity> response) {
                        if (response.body() != null) {
                            MovieEntity storedMovieEntity = dao.selectMovieById(movieId);
                            MovieEntity movieEntity = response.body();

                            if (storedMovieEntity == null) {
                                movieEntity.setFavorite(false);
                            } else {
                                movieEntity.setFavorite(storedMovieEntity.getFavorite());
                                movieEntity.setPage(storedMovieEntity.getPage());
                            }
                            resultMovie.setValue(ApiResponse.success(movieEntity));
                        }

                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieEntity> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new MovieEntity()));

                        EspressoIdlingResource.decrement();
                    }
                });
                return resultMovie;
            }

            @Override
            protected void saveCallResult(MovieEntity data) {
                if (dao.selectMovieById(movieId) == null) {
                    dao.insertMovie(data);
                } else {
                    dao.updateMovie(data);
                }
            }
        }.asLiveData();
    }

    //Tv Show Details
    @Override
    public LiveData<Resource<TvShowEntity>> loadTvShowDetails(Long tvShowId) {
        return new NetworkBoundResource<TvShowEntity, TvShowEntity>(exec) {
            @Override
            protected LiveData<TvShowEntity> loadFromDB() {
                MutableLiveData<TvShowEntity> tvShowFromDb = new MutableLiveData<>();
                tvShowFromDb.setValue(dao.selectTvShowById(tvShowId));
                return tvShowFromDb;
            }

            @Override
            public Boolean shouldFetch(TvShowEntity data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<TvShowEntity>> createCall() {
                MutableLiveData<ApiResponse<TvShowEntity>> resultTvShow = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Call<TvShowEntity> call = api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
                call.enqueue(new Callback<TvShowEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowEntity> call, @NonNull Response<TvShowEntity> response) {
                        if (response.body() != null) {
                            TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowId);
                            TvShowEntity tvShowEntity = response.body();

                            if (storedTvShowEntity == null) {
                                tvShowEntity.setFavorite(false);
                            } else {
                                tvShowEntity.setFavorite(storedTvShowEntity.getFavorite());
                                tvShowEntity.setPage(storedTvShowEntity.getPage());
                            }
                            resultTvShow.setValue(ApiResponse.success(tvShowEntity));
                        }

                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowEntity> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new TvShowEntity()));

                        EspressoIdlingResource.decrement();
                    }
                });
                return resultTvShow;
            }

            @Override
            protected void saveCallResult(TvShowEntity data) {
                if (dao.selectTvShowById(tvShowId) == null) {
                    dao.insertTvShow(data);
                } else {
                    dao.updateTvShow(data);
                }
            }
        }.asLiveData();
    }

    /*
     * Search Activity
     * */
    //Search Movie
    @Override
    public LiveData<Resource<MovieResponse>> searchListMovie(String query, Long page) {
        MutableLiveData<Resource<MovieResponse>> resultMovie = new MutableLiveData<>();

        EspressoIdlingResource.increment();

        Call<MovieResponse> call = api.searchMovies(BuildConfig.TMDB_API_KEY, query, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    ArrayList<MovieEntity> helper = new ArrayList<>();
                    for (MovieEntity movieEntity : response.body().getResults()) {
                        MovieEntity storedMovieEntity = dao.selectMovieById(movieEntity.getId());
                        if (storedMovieEntity == null) {
                            movieEntity.setFavorite(false);
                        } else {
                            movieEntity.setFavorite(storedMovieEntity.getFavorite());
                        }
                        helper.add(movieEntity);
                    }
                    MovieResponse movieResponse = new MovieResponse();
                    movieResponse.setPage(response.body().getPage());
                    movieResponse.setTotalPages(response.body().getTotalPages());
                    movieResponse.setResults(helper);
                    dao.insertMovies(helper);
                    resultMovie.postValue(Resource.success(movieResponse));

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                resultMovie.setValue(Resource.error(t.getMessage(), new MovieResponse()));

                EspressoIdlingResource.decrement();
            }
        });

        return resultMovie;
    }

    //Search Tv Show
    @Override
    public LiveData<Resource<TvShowResponse>> searchListTvShow(String query, Long page) {
        MutableLiveData<Resource<TvShowResponse>> resultTvShow = new MutableLiveData<>();

        EspressoIdlingResource.increment();

        Call<TvShowResponse> call = api.searchTvShows(BuildConfig.TMDB_API_KEY, query, page);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.body() != null) {
                    ArrayList<TvShowEntity> helper = new ArrayList<>();
                    for (TvShowEntity tvShowEntity : response.body().getResults()) {
                        TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowEntity.getId());
                        if (storedTvShowEntity == null) {
                            tvShowEntity.setFavorite(false);
                        } else {
                            tvShowEntity.setFavorite(storedTvShowEntity.getFavorite());
                        }
                        helper.add(tvShowEntity);
                    }
                    TvShowResponse tvShowResponse = new TvShowResponse();
                    tvShowResponse.setPage(response.body().getPage());
                    tvShowResponse.setTotalPages(response.body().getTotalPages());
                    tvShowResponse.setResults(helper);
                    dao.insertTvShows(helper);
                    resultTvShow.postValue(Resource.success(tvShowResponse));

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                resultTvShow.setValue(Resource.error(t.getMessage(), new TvShowResponse()));

                EspressoIdlingResource.decrement();
            }
        });

        return resultTvShow;
    }

    /*
     * Languages
     * */
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

                EspressoIdlingResource.increment();

                Call<List<LanguageEntity>> call = api.getLanguages(BuildConfig.TMDB_API_KEY);
                call.enqueue(new Callback<List<LanguageEntity>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<LanguageEntity>> call, @NonNull Response<List<LanguageEntity>> response) {
                        resultLanguage.setValue(ApiResponse.success(response.body()));

                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<LanguageEntity>> call, @NonNull Throwable t) {
                        resultLanguage.setValue(ApiResponse.error(t.getMessage(), new ArrayList<>()));

                        EspressoIdlingResource.decrement();
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
