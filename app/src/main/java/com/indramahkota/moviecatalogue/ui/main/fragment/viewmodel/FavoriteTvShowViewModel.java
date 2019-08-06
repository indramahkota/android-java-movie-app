package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;
import com.indramahkota.moviecatalogue.data.source.locale.repository.LocalRepository;
import com.indramahkota.moviecatalogue.ui.detail.interfaces.LoadLocalDbCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class FavoriteTvShowViewModel extends ViewModel implements LoadLocalDbCallback  {
    private final LocalRepository localRepository;
    private LiveData<List<FavoriteTvShow>> listFavoriteTvShow;

    @Inject
    FavoriteTvShowViewModel(LocalRepository localRepository) {
        this.localRepository = localRepository;
        listFavoriteTvShow = localRepository.getAllFavoriteTvShow();
    }

    /*
     * Favorite Movie
     * */

    public LiveData<List<FavoriteTvShow>> getListFavoriteTvShow() {
        return listFavoriteTvShow;
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new InsertFavoriteTvShowAsyncTask(localRepository, this).execute(favoriteTvShow);
    }

    private static class InsertFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Long> {
        private final WeakReference<LocalRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        InsertFavoriteTvShowAsyncTask(LocalRepository localRepository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(localRepository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Long doInBackground(FavoriteTvShow... favoriteTvShows) {
            return weakRepo.get().insertFavoriteTvShow(favoriteTvShows[0]);
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

    @Override
    public void deleteSuccess(Boolean bool) {

    }
}
