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
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.ui.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class FakeMovieCatalogueRepository implements MovieCatalogueDataSource {
    private static final String TAG = "REPOSITORY";

    private final AppDao dao;
    private final ApiEndPoint api;
    private final AppExecutors exec;

    @Inject
    public FakeMovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint, AppExecutors exec){
        this.dao = appDao;
        this.api = apiEndPoint;
        this.exec = exec;
    }

    /*
     * Main Activity
     * */

    //MovieFragmentViewModel
    @Override
    public LiveData<Resource<DiscoverMovieResponse>> loadListMovie(Long page) {
        return new NetworkBoundResource<DiscoverMovieResponse, DiscoverMovieResponse>(exec) {
            @Override
            protected LiveData<DiscoverMovieResponse> loadFromDB() {
                MutableLiveData<DiscoverMovieResponse> dbResourceLiveData = new MutableLiveData<>();

                DiscoverMovieResponse discoverMovieResponse = new DiscoverMovieResponse();
                discoverMovieResponse.setPage(page);
                discoverMovieResponse.setResults(dao.selectAllMovie(page));
                dbResourceLiveData.setValue(discoverMovieResponse);

                return dbResourceLiveData;
            }

            @Override
            protected Boolean shouldFetch(DiscoverMovieResponse data) {
                return true;
            }

            @Override
            protected LiveData<ApiResponse<DiscoverMovieResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverMovieResponse>> resultMovie = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Log.d(TAG, "Load: loadListMovie");
                Log.d(TAG, "Load: loadListMovie Page: " + page);

                Call<DiscoverMovieResponse> call = api.getDiscoverMovies(BuildConfig.TMDB_API_KEY, page);
                call.enqueue(new Callback<DiscoverMovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DiscoverMovieResponse> call, @NonNull Response<DiscoverMovieResponse> response) {
                        if (response.body() != null) {
                            List<MovieEntity> helper = new ArrayList<>();
                            for(MovieEntity movieEntity : response.body().getResults()) {
                                MovieEntity storedMovieEntity = dao.selectMovieById(movieEntity.getId());
                                if (storedMovieEntity == null) {
                                    movieEntity.setFavorite(false);
                                    movieEntity.setPage(page);
                                } else {
                                    if(storedMovieEntity.getFavorite()) {
                                        movieEntity.setFavorite(true);
                                    } else {
                                        movieEntity.setFavorite(false);
                                    }
                                    movieEntity.setPage(page);
                                }
                                helper.add(movieEntity);
                            }
                            DiscoverMovieResponse discoverMovieResponse = new DiscoverMovieResponse();
                            discoverMovieResponse.setPage(response.body().getPage());
                            discoverMovieResponse.setTotalPages(response.body().getTotalPages());
                            discoverMovieResponse.setResults(helper);
                            resultMovie.postValue(ApiResponse.success(discoverMovieResponse));

                            EspressoIdlingResource.decrement();

                            Log.d(TAG, "Complete: loadListMovie");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new DiscoverMovieResponse()));

                        EspressoIdlingResource.decrement();

                        Log.d(TAG, "Error: loadListMovie");
                    }
                });
                return resultMovie;
            }

            @Override
            protected void saveCallResult(DiscoverMovieResponse data) {
                dao.insertMovies(data.getResults());
            }
        }.asLiveData();
    }

    //TvShowFragmentViewModel
    @Override
    public LiveData<Resource<DiscoverTvShowResponse>> loadListTvShow(Long page) {
        return new NetworkBoundResource<DiscoverTvShowResponse, DiscoverTvShowResponse>(exec) {
            @Override
            protected LiveData<DiscoverTvShowResponse> loadFromDB() {
                MutableLiveData<DiscoverTvShowResponse> dbResourceLiveData = new MutableLiveData<>();

                DiscoverTvShowResponse discoverTvShowResponse = new DiscoverTvShowResponse();
                discoverTvShowResponse.setPage(page);
                discoverTvShowResponse.setResults(dao.selectAllTvShow(page));
                dbResourceLiveData.setValue(discoverTvShowResponse);

                return dbResourceLiveData;
            }

            @Override
            protected Boolean shouldFetch(DiscoverTvShowResponse data) {
                return true;
            }

            @Override
            protected LiveData<ApiResponse<DiscoverTvShowResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverTvShowResponse>> resultTvShow = new MutableLiveData<>();

                EspressoIdlingResource.increment();

                Log.d(TAG, "Load: loadListTvShow");
                Log.d(TAG, "Load: loadListTvShow Page: " + page);

                Call<DiscoverTvShowResponse> call = api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY, page);
                call.enqueue(new Callback<DiscoverTvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Response<DiscoverTvShowResponse> response) {
                        if (response.body() != null) {
                            List<TvShowEntity> helper = new ArrayList<>();
                            for(TvShowEntity tvShowEntity : response.body().getResults()) {
                                TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowEntity.getId());
                                if (storedTvShowEntity == null) {
                                    tvShowEntity.setFavorite(false);
                                    tvShowEntity.setPage(page);
                                } else {
                                    if(storedTvShowEntity.getFavorite()) {
                                        tvShowEntity.setFavorite(true);
                                    } else {
                                        tvShowEntity.setFavorite(false);
                                    }
                                    tvShowEntity.setPage(page);
                                }
                                helper.add(tvShowEntity);
                            }
                            DiscoverTvShowResponse discoverTvShowResponse = new DiscoverTvShowResponse();
                            discoverTvShowResponse.setPage(response.body().getPage());
                            discoverTvShowResponse.setTotalPages(response.body().getTotalPages());
                            discoverTvShowResponse.setResults(helper);
                            resultTvShow.postValue(ApiResponse.success(discoverTvShowResponse));

                            EspressoIdlingResource.decrement();

                            Log.d(TAG, "Complete: loadListTvShow");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new DiscoverTvShowResponse()));

                        EspressoIdlingResource.decrement();

                        Log.d(TAG, "Error: loadListTvShow");
                    }
                });
                return resultTvShow;
            }

            @Override
            protected void saveCallResult(DiscoverTvShowResponse data) {
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
                        if(response.body() != null) {
                            MovieEntity storedMovieEntity = dao.selectMovieById(movieId);
                            MovieEntity movieEntity = response.body();

                            if (storedMovieEntity == null) {
                                movieEntity.setFavorite(false);
                            } else {
                                if(storedMovieEntity.getFavorite()) {
                                    movieEntity.setFavorite(true);
                                } else {
                                    movieEntity.setFavorite(false);
                                }
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
                if(dao.selectMovieById(movieId) == null) {
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
                        if(response.body() != null) {
                            TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowId);
                            TvShowEntity tvShowEntity = response.body();

                            if (storedTvShowEntity == null) {
                                tvShowEntity.setFavorite(false);
                            } else {
                                if(storedTvShowEntity.getFavorite()) {
                                    tvShowEntity.setFavorite(true);
                                } else {
                                    tvShowEntity.setFavorite(false);
                                }
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
                if(dao.selectTvShowById(tvShowId) == null) {
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
    public LiveData<Resource<DiscoverMovieResponse>> searchListMovie(String query, Long page) {
        MutableLiveData<Resource<DiscoverMovieResponse>> resultMovie = new MutableLiveData<>();

        EspressoIdlingResource.increment();

        Call<DiscoverMovieResponse> call = api.searchMovies(BuildConfig.TMDB_API_KEY, query, page);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverMovieResponse> call, @NonNull Response<DiscoverMovieResponse> response) {
                if (response.body() != null) {
                    ArrayList<MovieEntity> helper = new ArrayList<>();
                    for(MovieEntity movieEntity : response.body().getResults()) {
                        MovieEntity storedMovieEntity = dao.selectMovieById(movieEntity.getId());
                        if (storedMovieEntity == null) {
                            movieEntity.setFavorite(false);
                        } else {
                            if(storedMovieEntity.getFavorite()) {
                                movieEntity.setFavorite(true);
                            } else {
                                movieEntity.setFavorite(false);
                            }
                        }
                        helper.add(movieEntity);
                    }
                    DiscoverMovieResponse discoverMovieResponse = new DiscoverMovieResponse();
                    discoverMovieResponse.setPage(response.body().getPage());
                    discoverMovieResponse.setTotalPages(response.body().getTotalPages());
                    discoverMovieResponse.setResults(helper);
                    dao.insertMovies(helper);
                    resultMovie.postValue(Resource.success(discoverMovieResponse));

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                resultMovie.setValue(Resource.error(t.getMessage(), new DiscoverMovieResponse()));

                EspressoIdlingResource.decrement();
            }
        });

        return resultMovie;
    }

    //Search Tv Show
    @Override
    public LiveData<Resource<DiscoverTvShowResponse>> searchListTvShow(String query, Long page) {
        MutableLiveData<Resource<DiscoverTvShowResponse>> resultTvShow = new MutableLiveData<>();

        EspressoIdlingResource.increment();

        Call<DiscoverTvShowResponse> call = api.searchTvShows(BuildConfig.TMDB_API_KEY, query, page);
        call.enqueue(new Callback<DiscoverTvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Response<DiscoverTvShowResponse> response) {
                if (response.body() != null) {
                    ArrayList<TvShowEntity> helper = new ArrayList<>();
                    for(TvShowEntity tvShowEntity : response.body().getResults()) {
                        TvShowEntity storedTvShowEntity = dao.selectTvShowById(tvShowEntity.getId());
                        if (storedTvShowEntity == null) {
                            tvShowEntity.setFavorite(false);
                        } else {
                            if(storedTvShowEntity.getFavorite()) {
                                tvShowEntity.setFavorite(true);
                            } else {
                                tvShowEntity.setFavorite(false);
                            }
                        }
                        helper.add(tvShowEntity);
                    }
                    DiscoverTvShowResponse discoverTvShowResponse = new DiscoverTvShowResponse();
                    discoverTvShowResponse.setPage(response.body().getPage());
                    discoverTvShowResponse.setTotalPages(response.body().getTotalPages());
                    discoverTvShowResponse.setResults(helper);
                    dao.insertTvShows(helper);
                    resultTvShow.postValue(Resource.success(discoverTvShowResponse));

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                resultTvShow.setValue(Resource.error(t.getMessage(), new DiscoverTvShowResponse()));

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
