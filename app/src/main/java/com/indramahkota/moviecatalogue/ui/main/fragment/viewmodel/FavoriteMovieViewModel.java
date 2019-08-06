package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
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
    FavoriteMovieViewModel(@NonNull LocalRepository localRepository) {
        this.localRepository = localRepository;
        listFavoriteMovie = localRepository.getAllFavoriteMovie();
    }

    /*
     * Favorite Movie
     * */

    public LiveData<List<FavoriteMovie>> getListFavoriteMovie() {
        return listFavoriteMovie;
    }

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
        protected Long doInBackground(FavoriteMovie... favoriteMovie) {
            return weakRepo.get().insertFavoriteMovie(favoriteMovie[0]);
        }

        @Override
        protected void onPostExecute(Long l) {
            super.onPostExecute(l);
            weakCallback.get().insertSuccess(l);
        }
    }

    @Override
    public void insertSuccess(Long l) {
        Log.d("DB_EXECUTOR", String.valueOf(l));
    }



    @Override
    public void deleteSuccess(Boolean bool) {

    }
}
