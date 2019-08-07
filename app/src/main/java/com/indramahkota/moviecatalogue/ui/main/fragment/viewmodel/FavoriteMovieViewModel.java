package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovieEntity;
import com.indramahkota.moviecatalogue.ui.detail.interfaces.LoadLocalDbCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel implements LoadLocalDbCallback {
    private final MovieCatalogueRepository repository;
    private LiveData<List<FavoriteMovieEntity>> listFavoriteMovie;

    @Inject
    FavoriteMovieViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
        listFavoriteMovie = repository.getAllFavoriteMovie();
    }

    /*
     * Get List Favorite Movie
     * */

    public LiveData<List<FavoriteMovieEntity>> getListFavoriteMovie() {
        return listFavoriteMovie;
    }

    /*
     * Get Favorite Movie
     * */

    public LiveData<FavoriteMovieEntity> getFavoriteMovie(Long ln) {
        return repository.getFavoriteMovieById(ln);
    }

    /*
     * Insert Favorite Movie
     * */

    public void insertFavoriteMovie(FavoriteMovieEntity favoriteMovieEntity) {
        new InsertFavoriteMovieAsyncTask(repository, this).execute(favoriteMovieEntity);
    }

    private static class InsertFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovieEntity, Void, Long> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        InsertFavoriteMovieAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Long doInBackground(FavoriteMovieEntity... favoriteMovieEntities) {
            return weakRepo.get().insertFavoriteMovie(favoriteMovieEntities[0]);
        }

        @Override
        protected void onPostExecute(Long ln) {
            super.onPostExecute(ln);
            weakCallback.get().insertSuccess(ln);
        }
    }

    @Override
    public void insertSuccess(Long l) {
        Log.d("DB_EXECUTOR", String.valueOf(l));
    }

    /*
     * Delete Favorite Movie
     * */

    public void deleteFavoriteMovie(Long itemId) {
        new DeleteFavoriteMovieAsyncTask(repository, this).execute(itemId);
    }

    private static class DeleteFavoriteMovieAsyncTask extends AsyncTask<Long, Void, Integer> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        DeleteFavoriteMovieAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Integer doInBackground(Long... ln) {
            return weakRepo.get().deleteFavoriteMovieById(ln[0]);
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            weakCallback.get().deleteSuccess(i);
        }
    }

    @Override
    public void deleteSuccess(Integer i) {
        Log.d("DB_EXECUTOR", String.valueOf(i));
    }
}
