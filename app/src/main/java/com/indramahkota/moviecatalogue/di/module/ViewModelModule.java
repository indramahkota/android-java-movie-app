package com.indramahkota.moviecatalogue.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.MovieFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieFragmentViewModel.class)
    protected abstract ViewModel movieListViewModel(MovieFragmentViewModel movieFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TvShowFragmentViewModel.class)
    protected abstract ViewModel tvShowListViewModel(TvShowFragmentViewModel tvShowFragmentViewModel);
}