package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteMovie;
import com.indramahkota.moviecatalogue.data.source.locale.repository.LocalRepository;
import com.indramahkota.moviecatalogue.ui.detail.interfaces.LoadLocalDbCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class FavoriteMovieViewModel extends ViewModel implements LoadLocalDbCallback {
    private final LocalRepository localRepository;
    private LiveData<List<FavoriteMovie>> listFavoriteMovie;

    @Inject
    FavoriteMovieViewModel(LocalRepository localRepository) {
        this.localRepository = localRepository;
        listFavoriteMovie = localRepository.getAllFavoriteMovie();
    }

    /*
     * Get List Favorite Movie
     * */

    public LiveData<List<FavoriteMovie>> getListFavoriteMovie() {
        return listFavoriteMovie;
    }

    /*
     * Get Favorite Movie
     * */

    public LiveData<FavoriteMovie> getFavoriteMovie(Long ln) {
        return localRepository.getFavoriteMovie(ln);
    }

    /*
     * Insert Favorite Movie
     * */

    public void insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        new InsertFavoriteMovieAsyncTask(localRepository, this).execute(favoriteMovie);
    }

    private static class InsertFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Long> {
        private final WeakReference<LocalRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        InsertFavoriteMovieAsyncTask(LocalRepository localRepository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(localRepository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Long doInBackground(FavoriteMovie... favoriteMovies) {
            return weakRepo.get().insertFavoriteMovie(favoriteMovies[0]);
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
        new DeleteFavoriteMovieAsyncTask(localRepository, this).execute(itemId);
    }

    private static class DeleteFavoriteMovieAsyncTask extends AsyncTask<Long, Void, Integer> {
        private final WeakReference<LocalRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        DeleteFavoriteMovieAsyncTask(LocalRepository localRepository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(localRepository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Integer doInBackground(Long... ln) {
            return weakRepo.get().deleteFavoriteMovie(ln[0]);
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
