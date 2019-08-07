package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.indramahkota.moviecatalogue.data.source.MovieCatalogueRepository;
import com.indramahkota.moviecatalogue.data.source.locale.entity.TvShowEntity;
import com.indramahkota.moviecatalogue.ui.detail.interfaces.LoadLocalDbCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class FavoriteTvShowViewModel extends ViewModel implements LoadLocalDbCallback  {
    private final MovieCatalogueRepository repository;
    private LiveData<List<TvShowEntity>> listTvShow;

    @Inject
    FavoriteTvShowViewModel(@NonNull MovieCatalogueRepository repository) {
        this.repository = repository;
        listTvShow = repository.getAllTvShow();
    }
    /*
     * Get List Favorite Tv Show
     * */

    public LiveData<List<TvShowEntity>> getListTvShow() {
        return listTvShow;
    }

    /*
     * Get Favorite Tv Show
     * */

    public LiveData<TvShowEntity> getTvShow(Long ln) {
        return repository.getTvShowById(ln);
    }

    /*
     * Insert Favorite Tv Show
     * */

    public void insertTvShow(TvShowEntity tvShowEntity) {
        new InsertTvShowAsyncTask(repository, this).execute(tvShowEntity);
    }

    private static class InsertTvShowAsyncTask extends AsyncTask<TvShowEntity, Void, Long> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        InsertTvShowAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Long doInBackground(TvShowEntity... tvShowEntities) {
            return weakRepo.get().insertTvShow(tvShowEntities[0]);
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
     * Delete Favorite Tv Show
     * */

    public void deleteTvShow(Long itemId) {
        new DeleteTvShowAsyncTask(repository, this).execute(itemId);
    }

    private static class DeleteTvShowAsyncTask extends AsyncTask<Long, Void, Integer> {
        private final WeakReference<MovieCatalogueRepository> weakRepo;
        private final WeakReference<LoadLocalDbCallback> weakCallback;

        DeleteTvShowAsyncTask(MovieCatalogueRepository repository, LoadLocalDbCallback loadLocalDbCallback) {
            this.weakRepo = new WeakReference<>(repository);
            this.weakCallback = new WeakReference<>(loadLocalDbCallback);
        }

        @Override
        protected Integer doInBackground(Long... ln) {
            return weakRepo.get().deleteTvShowById(ln[0]);
        }

        @Override
        protected void onPostExecute(Integer ln) {
            super.onPostExecute(ln);
            weakCallback.get().deleteSuccess(ln);
        }
    }

    @Override
    public void deleteSuccess(Integer ln) {
        Log.d("DB_EXECUTOR", String.valueOf(ln));
    }
}
