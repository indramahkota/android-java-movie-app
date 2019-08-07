package com.indramahkota.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShowEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverMovieResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse;
import com.indramahkota.moviecatalogue.data.source.remote.response.others.Language;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieCatalogueDataSource {
    /*
    * Main Activity
    * */

    //Movie Fragment & Search Activity
    Single<Resource<DiscoverMovieResponse>> loadDiscoverMovie();

    //Tv Show Fragment & Search Activity
    Single<Resource<DiscoverTvShowResponse>> loadDiscoverTvShow();

    //Favorite Movie Fragment
    LiveData<Resource<PagedList<FavoriteMovieEntity>>> getListFavoriteMovie();
    LiveData<Resource<FavoriteMovieEntity>> getFavoriteMovieById(Long ln);
    long insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity);
    int deleteFavoriteMovieById(long id);

    //Favorite Tv Show Fragment
    LiveData<Resource<PagedList<FavoriteTvShowEntity>>> getListFavoriteTvShow();
    LiveData<Resource<FavoriteTvShowEntity>> getFavoriteTvShowById(Long ln);
    long insertTvShowMovie(FavoriteTvShowEntity favoriteTvShowEntity);
    int deleteFavoriteTvShowById(long id);

    /*
    * Detail Activity
    * */

    //Movie Detail
    Single<Resource<MovieEntity>> loadMovie();

    //Tv Show Detail
    Single<Resource<TvShowEntity>> loadTvShow();

    //Language
    Observable<Resource<List<Language>>> loadLanguage();
}
