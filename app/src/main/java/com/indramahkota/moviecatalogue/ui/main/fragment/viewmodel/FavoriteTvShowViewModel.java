package com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.indramahkota.moviecatalogue.data.source.locale.entity.FavoriteTvShow;

import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private final MutableLiveData<List<FavoriteTvShow>> listFavoriteTvShow = new MutableLiveData<>();

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);
    }
}
