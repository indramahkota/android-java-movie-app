package com.indramahkota.moviecatalogue.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.indramahkota.moviecatalogue.data.source.remote.TmdbViewModelFactory;
import com.indramahkota.moviecatalogue.di.scope.ViewModelKey;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.MovieFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieFragmentViewModel.class)
    abstract ViewModel bindMovieFragmentViewModel(MovieFragmentViewModel movieFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TvShowFragmentViewModel.class)
    abstract ViewModel bindTvShowFragmentViewModel(TvShowFragmentViewModel tvShowFragmentViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindNewsViewModelFactory(TmdbViewModelFactory factory);
}
