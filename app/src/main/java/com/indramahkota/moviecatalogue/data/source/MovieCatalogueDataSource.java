package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */

    //Movie Fragment
    Single<DiscoverMovieResponse> loadListMovie();

    //Tv Show Fragment
    Single<DiscoverTvShowResponse> loadListTvShow();

    //Favorite Movie Fragment
    LiveData<List<FavoriteMovieEntity>> getAllFavoriteMovie();
    LiveData<FavoriteMovieEntity> getFavoriteMovieById(Long id);
    long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity);
    int deleteFavoriteMovieById(long id);

    //Favorite Tv Show Fragment
    LiveData<List<FavoriteTvShowEntity>> getAllFavoriteTvShow();
    LiveData<FavoriteTvShowEntity> getFavoriteTvShowById(Long id);
    long insertFavoriteTvShow(FavoriteTvShowEntity favoriteTvShowEntity);
    int deleteFavoriteTvShowById(long id);

    /*
    * Detail Activity
    * */

    //Movie Detail Activity
    Single<MovieEntity> loadMovieDetails(Long movieId);

    //Tv Show Detail Activity
    Single<TvShowEntity> loadTvShowDetails(Long tvShowId);

    //Language
    Observable<ArrayList<Language>> loadLanguages();

    /*
     * Search Activity
     * */

    //Search Activity
    Single<DiscoverMovieResponse> searchListMovie(String query);

    //Search Activity
    Single<DiscoverTvShowResponse> searchListTvShow(String query);
}
