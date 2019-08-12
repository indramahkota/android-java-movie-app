package com.indramahkota.moviecatalogue.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.BuildConfig;
import com.indramahkota.moviecatalogue.data.source.locale.dao.AppDao;
import com.indramahkota.moviecatalogue.data.source.locale.entity.LanguageEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.api.ApiEndPoint;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.LanguageResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class MovieCatalogueRepository implements MovieCatalogueDataSource {
    private final AppDao dao;
    private final ApiEndPoint api;

    private CompositeDisposable disposable;

    @Inject
    public MovieCatalogueRepository(AppDao appDao, ApiEndPoint apiEndPoint){
        this.dao = appDao;
        this.api = apiEndPoint;
    }

    /*
     * Main Activity
     * */

    //MovieFragmentViewModel
    @Override
    public Observable<DiscoverMovieResponse> loadListMovie() {
        return api.getDiscoverMovies(BuildConfig.TMDB_API_KEY);
    }

    //TvShowFragmentViewModel
    @Override
    public Observable<DiscoverTvShowResponse> loadListTvShow() {
        return api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY);
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
    public Observable<MovieEntity> loadMovieDetails(Long movieId) {
        return api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "credits");
    }

    @Override
    public Observable<TvShowEntity> loadTvShowDetails(Long tvShowId) {
        return api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "credits");
    }

    @Override
    public Observable<List<LanguageEntity>> loadLanguages() {
        return api.getLanguages(BuildConfig.TMDB_API_KEY);
    }

    /*
     * Search Activity
     * */

    @Override
    public Observable<DiscoverMovieResponse> searchListMovie(String query) {
        return api.searchMovies(BuildConfig.TMDB_API_KEY, query);
    }

    @Override
    public Observable<DiscoverTvShowResponse> searchListTvShow(String query) {
        return api.searchTvShows(BuildConfig.TMDB_API_KEY, query);
    }

    @Override
    public Observable<Resource<LanguageResponse>> loadLanguage(String iso) {
        return new NetworkBoundResource<LanguageResponse, LanguageResponse>() {
            @Override
            protected void saveCallResult(@NonNull LanguageResponse item) {
                dao.insertLanguages(item.getResults());
            }

            @Override
            protected boolean shouldFetch(LanguageResponse data) {
                return data == null;
            }

            @NonNull
            @Override
            protected Flowable<LanguageResponse> loadFromDb() {
                List<LanguageEntity> languages = dao.selectAllLanguage();
                if(languages == null || languages.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(new LanguageResponse(languages));
            }

            @NonNull
            @Override
            protected Observable<Resource<LanguageResponse>> createCall() {
                return api.getLanguages(BuildConfig.TMDB_API_KEY)
                        .flatMap(languageEntities -> Observable.just(
                                languageEntities == null ?
                                        Resource.error("", new LanguageResponse(new ArrayList<>())) :
                                        Resource.success(new LanguageResponse(languageEntities))
                        ));
            }
        }.getAsObservable();
    }
}
