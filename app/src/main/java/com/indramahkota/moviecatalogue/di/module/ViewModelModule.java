package com.indramahkota.moviecatalogue.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.indramahkota.moviecatalogue.di.scope.ViewModelKey;
import com.indramahkota.moviecatalogue.factory.ViewModelFactory;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.LanguageViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.MovieDetailsViewModel;
import com.indramahkota.moviecatalogue.ui.detail.viewmodel.TvShowDetailsViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteMovieViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.FavoriteTvShowViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.MovieFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.main.fragment.viewmodel.TvShowFragmentViewModel;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchMovieViewModel;
import com.indramahkota.moviecatalogue.ui.search.viewmodel.SearchTvShowViewModel;

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

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    protected abstract ViewModel movieDetailViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailsViewModel.class)
    protected abstract ViewModel tvShowDetailViewModel(TvShowDetailsViewModel tvShowDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LanguageViewModel.class)
    protected abstract ViewModel languageViewModel(LanguageViewModel languageViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchMovieViewModel.class)
    protected abstract ViewModel searchMovieViewModel(SearchMovieViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchTvShowViewModel.class)
    protected abstract ViewModel searchTvShowViewModel(SearchTvShowViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel.class)
    protected abstract ViewModel favoriteMovieViewModel(FavoriteMovieViewModel favoriteMovieViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteTvShowViewModel.class)
    protected abstract ViewModel favoriteTvShowViewModel(FavoriteTvShowViewModel favoriteTvShowViewModel);
}