package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.MovieEntity;
import com.indramahkota.moviecatalogue.ui.detail.interfaces.LoadLocalDbCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel implements LoadLocalDbCallback {
    private final MovieCatalogueRepository repository;
    private LiveData<List<MovieEntity>> listMovie;

    @Inject
    FavoriteMovieViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
        listMovie = repository.getAllMovie();
    }

    /*
     * Get List Favorite Movie
     * */

    public LiveData<List<MovieEntity>> getListMovie() {
        return listMovie;
    }

    /*
     * Get Favorite Movie
     * */

    public LiveData<MovieEntity> getMovie(Long ln) {
        return repository.getMovieById(ln);
    }

    /*
     * Insert Favorite Movie
     * */

    public void insertMovie(MovieEntity movieEntity) {
        new InsertMovieAsyncTask(repository, this).execute(movieEntity);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<MovieEntity, Void, Long> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        InsertMovieAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Long doInBackground(MovieEntity... movieEntities) {
            return weakRepo.get().insertMovie(movieEntities[0]);
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

    public void deleteMovie(Long itemId) {
        new DeleteMovieAsyncTask(repository, this).execute(itemId);
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Long, Void, Integer> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        DeleteMovieAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Integer doInBackground(Long... ln) {
            return weakRepo.get().deleteMovieById(ln[0]);
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
