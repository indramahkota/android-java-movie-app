package com.indramahkota.moviecatalogue.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MovieCatalogueRepository implements MovieCatalogueDataSource {
    private final AppDao dao;
    private final ApiEndPoint api;
    private final AppExecutors exec;

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
    public LiveData<Resource<List<MovieEntity>>> loadListMovie(String refresh) {
        return new NetworkBoundResource<List<MovieEntity>, DiscoverMovieResponse>(exec) {
            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return dao.selectAllMovies();
            }

            @Override
            public Boolean shouldFetch(List<MovieEntity> data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<DiscoverMovieResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverMovieResponse>> resultMovie = new MutableLiveData<>();

                Call<DiscoverMovieResponse> call = api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
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
                            discoverMovieResponse.setResults(helper);
                            resultMovie.postValue(ApiResponse.success(discoverMovieResponse));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new DiscoverMovieResponse()));
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
    public LiveData<Resource<List<TvShowEntity>>> loadListTvShow(String refresh) {
        return new NetworkBoundResource<List<TvShowEntity>, DiscoverTvShowResponse>(exec) {
            @Override
            protected LiveData<List<TvShowEntity>> loadFromDB() {
                return dao.selectAllTvShows();
            }

            @Override
            public Boolean shouldFetch(List<TvShowEntity> data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<DiscoverTvShowResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverTvShowResponse>> resultTvShow = new MutableLiveData<>();

                Call<DiscoverTvShowResponse> call = api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
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
                            discoverTvShowResponse.setResults(helper);
                            resultTvShow.postValue(ApiResponse.success(discoverTvShowResponse));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new DiscoverTvShowResponse()));
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
    public DataSource.Factory<Integer, MovieEntity> getAllMovie() {
        return dao.selectAllMovie();
    }

    @Override
    public void updateMovie(MovieEntity movieEntity) {
        dao.updateMovie(movieEntity);
    }

    //FavoriteTvShowViewModel
    @Override
    public DataSource.Factory<Integer, TvShowEntity> getAllTvShow() {
        return dao.selectAllTvShow();
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
                            }
                            resultMovie.setValue(ApiResponse.success(movieEntity));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieEntity> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new MovieEntity()));
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
                            }
                            resultTvShow.setValue(ApiResponse.success(tvShowEntity));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowEntity> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new TvShowEntity()));
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
    public LiveData<Resource<List<MovieEntity>>> searchListMovie(String query) {
        return new NetworkBoundResource<List<MovieEntity>, DiscoverMovieResponse>(exec) {
            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return dao.selectAllMovies();
            }

            @Override
            public Boolean shouldFetch(List<MovieEntity> data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<DiscoverMovieResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverMovieResponse>> resultMovie = new MutableLiveData<>();

                Call<DiscoverMovieResponse> call = api.searchMovies(BuildConfig.TMDB_API_KEY, query);
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
                            discoverMovieResponse.setResults(helper);
                            resultMovie.postValue(ApiResponse.success(discoverMovieResponse));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverMovieResponse> call, @NonNull Throwable t) {
                        resultMovie.setValue(ApiResponse.error(t.getMessage(), new DiscoverMovieResponse()));
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

    //Search Tv Show
    @Override
    public LiveData<Resource<List<TvShowEntity>>> searchListTvShow(String query) {
        return new NetworkBoundResource<List<TvShowEntity>, DiscoverTvShowResponse>(exec) {
            @Override
            protected LiveData<List<TvShowEntity>> loadFromDB() {
                return dao.selectAllTvShows();
            }

            @Override
            public Boolean shouldFetch(List<TvShowEntity> data) {
                return true;
            }

            @Override
            public LiveData<ApiResponse<DiscoverTvShowResponse>> createCall() {
                MutableLiveData<ApiResponse<DiscoverTvShowResponse>> resultTvShow = new MutableLiveData<>();

                Call<DiscoverTvShowResponse> call = api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
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
                            discoverTvShowResponse.setResults(helper);
                            resultTvShow.postValue(ApiResponse.success(discoverTvShowResponse));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DiscoverTvShowResponse> call, @NonNull Throwable t) {
                        resultTvShow.setValue(ApiResponse.error(t.getMessage(), new DiscoverTvShowResponse()));
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
                Call<List<LanguageEntity>> call = api.getLanguages(BuildConfig.TMDB_API_KEY);
                call.enqueue(new Callback<List<LanguageEntity>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<LanguageEntity>> call, @NonNull Response<List<LanguageEntity>> response) {
                        resultLanguage.setValue(ApiResponse.success(response.body()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<LanguageEntity>> call, @NonNull Throwable t) { }
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
