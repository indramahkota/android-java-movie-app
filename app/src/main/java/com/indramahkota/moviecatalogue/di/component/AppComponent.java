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

public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        AppComponent build();
    }

    void inject(MovieCatalogueApp movieCatalogueApp);
}
