package com.indramahkota.moviecatalogue.di.component;

import android.app.Application;

import com.indramahkota.moviecatalogue.MovieCatalogueApp;
import com.indramahkota.moviecatalogue.di.module.ActivityModule;
import com.indramahkota.moviecatalogue.di.module.ApiModule;
import com.indramahkota.moviecatalogue.di.module.AppModule;
import com.indramahkota.moviecatalogue.di.module.DbModule;
import com.indramahkota.moviecatalogue.di.module.ExecutorModule;
import com.indramahkota.moviecatalogue.di.module.FragmentModule;
import com.indramahkota.moviecatalogue.di.module.RepositoryModule;
import com.indramahkota.moviecatalogue.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ApiModule.class,
                DbModule.class,
                RepositoryModule.class,
                ViewModelModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ExecutorModule.class
        }
)

public interface AppComponent extends AndroidInjector<MovieCatalogueApp> {
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        AppComponent create(@BindsInstance Application application);
    }
}
